package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_reference", length = 64)
    private String transactionReference;

    @Column(name = "operation_name", length = 120)
    private String operationName;

    @Column(name = "propagation_type", length = 40)
    private String propagationType;

    @Column(name = "event_status", nullable = false, length = 30)
    private String eventStatus;

    @Column(name = "reason", length = 1000)
    private String reason;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected AuditLog() { }

    public AuditLog(String transactionReference, String operationName,
                    String propagationType, String eventStatus,
                    String reason, String description) {
        this.transactionReference = transactionReference;
        this.operationName = operationName;
        this.propagationType = propagationType;
        this.eventStatus = eventStatus;
        this.reason = reason;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTransactionReference() { return transactionReference; }
    public String getOperationName() { return operationName; }
    public String getPropagationType() { return propagationType; }
    public String getEventStatus() { return eventStatus; }
    public String getReason() { return reason; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Retained for compatibility with the original transfer demo.
    public String getMessage() { return description; }
}
