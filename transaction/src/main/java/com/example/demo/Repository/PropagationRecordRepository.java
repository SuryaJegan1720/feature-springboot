package com.example.demo.Repository;

import com.example.demo.Entity.PropagationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropagationRecordRepository
        extends JpaRepository<PropagationRecord, Long> {

    List<PropagationRecord> findAllByOrderByIdAsc();
}
