package com.example.demo.Repository;

import com.example.demo.Entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByOrderByCreatedAtDesc();
    List<AuditLog> findByTransactionReferenceOrderByCreatedAtAsc(String transactionReference);
}
