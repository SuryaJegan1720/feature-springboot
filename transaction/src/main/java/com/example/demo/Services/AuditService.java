package com.example.demo.Services;

import com.example.demo.Entity.AuditLog;
import com.example.demo.Repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    /**
     * Every audit entry is committed in its own transaction. Therefore,
     * the audit row is not removed when the business transaction rolls back.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeAudit(String transactionReference,
                           String operationName,
                           String propagationType,
                           String eventStatus,
                           String reason,
                           String description) {
        auditRepository.saveAndFlush(new AuditLog(
                transactionReference,
                operationName,
                propagationType,
                eventStatus,
                reason,
                description));
    }

    // Compatibility method for any existing caller using the old API.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAudit(String message) {
        writeAuditInternal(message);
    }

    private void writeAuditInternal(String message) {
        auditRepository.saveAndFlush(new AuditLog(
                null, "LEGACY_AUDIT", null, "INFO",
                "Informational audit message", message));
    }
}
