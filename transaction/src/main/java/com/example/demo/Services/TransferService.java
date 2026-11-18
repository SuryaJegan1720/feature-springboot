package com.example.demo.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Entity.Account;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final AuditService auditService;

    @Transactional
    public void transfer(Long fromId, Long toId, Double amount) {
        String transactionReference = UUID.randomUUID().toString();
        AtomicReference<String> rollbackReason = new AtomicReference<>(
                "Transaction ended without a captured application exception");

        auditService.writeAudit(transactionReference, "BANK_TRANSFER", "REQUIRED",
                "STARTED", null,
                "Transfer started. Source account=" + fromId + ", destination account=" +
                toId + ", amount=" + amount + ". The transfer debits the source and credits the destination.");

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                boolean committed = status == STATUS_COMMITTED;
                auditService.writeAudit(transactionReference, "BANK_TRANSFER", "REQUIRED",
                        committed ? "COMMITTED" : "ROLLED_BACK",
                        committed ? "All transfer operations completed without an exception" : rollbackReason.get(),
                        committed
                                ? "The account debit and credit were committed together. The transfer is complete."
                                : "The bank transfer transaction rolled back. Account balance changes made " +
                                  "inside this transaction were undone; the independent audit entry was retained.");
            }
        });

        try {
            Account sender = accountRepository.findById(fromId).orElseThrow(
                    () -> new IllegalArgumentException("Source account not found: " + fromId));
            Account receiver = accountRepository.findById(toId).orElseThrow(
                    () -> new IllegalArgumentException("Destination account not found: " + toId));

            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            accountRepository.save(sender);
            accountRepository.save(receiver);

            if (amount > 10000) {
                throw new RuntimeException("Transfer limit exceeded: amount " + amount + " is above 10000");
            }
            throw new RuntimeException("Runtime exception : Amount lower than 10000");
        } catch (RuntimeException ex) {
            rollbackReason.set(ex.getClass().getSimpleName() + ": " +
                    (ex.getMessage() == null ? "No exception message supplied" : ex.getMessage()));
            throw ex;
        }
    }
}
