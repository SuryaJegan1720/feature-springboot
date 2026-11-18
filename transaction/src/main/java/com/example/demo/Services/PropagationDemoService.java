package com.example.demo.Services;

import com.example.demo.Entity.PropagationRecord;
import com.example.demo.Repository.PropagationRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PropagationDemoService {

    private final PropagationRecordRepository recordRepository;
    private final PropagationWorkerService worker;
    private final AuditService auditService;

    @PersistenceContext
    private EntityManager entityManager;

    public PropagationDemoService(PropagationRecordRepository recordRepository,
                                  PropagationWorkerService worker,
                                  AuditService auditService) {
        this.recordRepository = recordRepository;
        this.worker = worker;
        this.auditService = auditService;
    }

    private void record(String label) {
        boolean active = TransactionSynchronizationManager.isActualTransactionActive();
        recordRepository.saveAndFlush(new PropagationRecord(label, active));
        System.out.println(label + " | service transaction active = " + active);
    }

    @Transactional
    public String runInside(String type, boolean failOuter) {
        String propagation = type.toUpperCase();
        String transactionReference = UUID.randomUUID().toString();
        AtomicReference<String> rollbackReason = new AtomicReference<>(
                failOuter ? "Configured failOuter=true: simulated outer failure" :
                        "No application exception was reported; checking transaction completion status");

        auditService.writeAudit(transactionReference, "PROPAGATION_DEMO", propagation,
                "STARTED", null,
                "Started propagation demonstration inside an existing outer transaction. " +
                "The worker operation is " + propagation + ".");

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                boolean committed = status == STATUS_COMMITTED;
                String eventStatus = committed ? "COMMITTED" : "ROLLED_BACK";
                String reason = committed ? "Transaction completed successfully" : rollbackReason.get();
                String description = committed
                        ? "The outer transaction and all participating REQUIRED work committed. " +
                          "Independent REQUIRES_NEW work, if any, was committed separately."
                        : "The outer transaction rolled back. Work participating in this transaction " +
                          "was undone. Independent REQUIRES_NEW or non-transactional work may remain committed.";
                auditService.writeAudit(transactionReference, "PROPAGATION_DEMO", propagation,
                        eventStatus, reason, description);
            }
        });

        try {
            record("OUTER before " + propagation);
            String result = invoke(propagation, transactionReference);

            if (failOuter) {
                throw new IllegalStateException("Simulated outer transaction failure because failOuter=true");
            }

            record("OUTER after " + propagation);
            return result;
        } catch (RuntimeException ex) {
            rollbackReason.set(ex.getClass().getSimpleName() + ": " + safeMessage(ex));
            throw ex;
        }
    }

    // Intentionally not transactional: demonstrates calls without a caller transaction.
    public String runOutside(String type) {
        String propagation = type.toUpperCase();
        String transactionReference = UUID.randomUUID().toString();
        auditService.writeAudit(transactionReference, "PROPAGATION_DEMO", propagation,
                "STARTED", null,
                "Started propagation demonstration without an outer service transaction.");
        try {
            record("CALLER outside transaction");
            String result = invoke(propagation, transactionReference);
            auditService.writeAudit(transactionReference, "PROPAGATION_DEMO", propagation,
                    "COMPLETED", "No exception was thrown",
                    "The worker call returned successfully. Any repository write transaction " +
                    "was committed by Spring Data JPA.");
            return result;
        } catch (IllegalTransactionStateException ex) {
            auditService.writeAudit(transactionReference, "PROPAGATION_DEMO", propagation,
                    "REJECTED", ex.getClass().getSimpleName() + ": " + safeMessage(ex),
                    "Spring rejected the call because the selected propagation rule requires " +
                    "an existing transaction, but none was available.");
            throw ex;
        } catch (RuntimeException ex) {
            auditService.writeAudit(transactionReference, "PROPAGATION_DEMO", propagation,
                    "FAILED", ex.getClass().getSimpleName() + ": " + safeMessage(ex),
                    "The operation failed before it could complete successfully.");
            throw ex;
        }
    }

    private String invoke(String type, String transactionReference) {
        switch (type) {
            case "REQUIRED" -> worker.required();
            case "REQUIRES_NEW" -> worker.requiresNew();
            case "SUPPORTS" -> worker.supports();
            case "MANDATORY" -> worker.mandatory();
            case "NOT_SUPPORTED" -> worker.notSupported();
            case "NEVER" -> {
                try {
                    worker.never();
                } catch (IllegalTransactionStateException ex) {
                    auditService.writeAudit(transactionReference, "PROPAGATION_WORKER", type,
                            "REJECTED", ex.getClass().getSimpleName() + ": " + safeMessage(ex),
                            "NEVER does not permit execution when an existing transaction is active. " +
                            "The outer demonstration catches this rejection, so the outer transaction can continue.");
                    return "NEVER correctly rejected an existing transaction";
                }
            }
            case "NESTED" -> {
                try {
                    worker.nestedThenFail();
                } catch (IllegalStateException ex) {
                    entityManager.clear();
                    auditService.writeAudit(transactionReference, "PROPAGATION_WORKER", type,
                            "ROLLED_BACK", ex.getClass().getSimpleName() + ": " + safeMessage(ex),
                            "The nested worker deliberately threw an exception. Its JDBC savepoint was rolled back; " +
                            "the outer transaction catches the exception and can still commit.");
                    record("OUTER caught nested failure and continued");
                }
            }
            default -> throw new IllegalArgumentException(
                    "Unknown propagation type: " + type + ". Use REQUIRED, REQUIRES_NEW, SUPPORTS, " +
                    "MANDATORY, NOT_SUPPORTED, NEVER or NESTED.");
        }
        return "Worker executed: " + type;
    }

    private String safeMessage(Exception ex) {
        return ex.getMessage() == null ? "No exception message supplied" : ex.getMessage();
    }
}
