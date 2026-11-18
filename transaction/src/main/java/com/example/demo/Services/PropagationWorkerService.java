package com.example.demo.Services;

import com.example.demo.Entity.PropagationRecord;
import com.example.demo.Repository.PropagationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class PropagationWorkerService {

    private final PropagationRecordRepository recordRepository;

    public PropagationWorkerService(PropagationRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    private void record(String label) {
        boolean active = TransactionSynchronizationManager.isActualTransactionActive();

        // saveAndFlush forces the INSERT to reach the database now.
        // This is important for observing savepoint rollback in the NESTED example.
        recordRepository.saveAndFlush(new PropagationRecord(label, active));

        System.out.println(label + " | service transaction active = " + active);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        record("REQUIRED worker");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {
        record("REQUIRES_NEW worker");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {
        record("SUPPORTS worker");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {
        record("MANDATORY worker");
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {
        record("NOT_SUPPORTED worker");
    }

    @Transactional(propagation = Propagation.NEVER)
    public void never() {
        record("NEVER worker");
    }

    /*
     * NESTED uses a JDBC savepoint underneath JpaTransactionManager.
     * saveAndFlush() sends the INSERT to the database before the exception,
     * allowing the savepoint rollback to undo that INSERT.
     */
    @Transactional(propagation = Propagation.NESTED)
    public void nestedThenFail() {
        record("NESTED worker - this row should roll back");
        throw new IllegalStateException("Simulated nested failure");
    }
}
