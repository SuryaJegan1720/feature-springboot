package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "propagation_records")
public class PropagationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(name = "transaction_active", nullable = false)
    private boolean transactionActive;

    protected PropagationRecord() {}

    public PropagationRecord(String label, boolean transactionActive) {
        this.label = label;
        this.transactionActive = transactionActive;
    }

    public Long getId() { return id; }
    public String getLabel() { return label; }
    public boolean isTransactionActive() { return transactionActive; }
}
