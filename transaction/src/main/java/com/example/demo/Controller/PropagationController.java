package com.example.demo.Controller;

import com.example.demo.Entity.AuditLog;
import com.example.demo.Entity.PropagationRecord;
import com.example.demo.Repository.AuditRepository;
import com.example.demo.Repository.PropagationRecordRepository;
import com.example.demo.Services.PropagationDemoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/propagation")
public class PropagationController {

    private final PropagationDemoService demoService;
    private final PropagationRecordRepository recordRepository;
    private final AuditRepository auditRepository;

    public PropagationController(PropagationDemoService demoService,
                                 PropagationRecordRepository recordRepository,
                                 AuditRepository auditRepository) {
        this.demoService = demoService;
        this.recordRepository = recordRepository;
        this.auditRepository = auditRepository;
    }

    @PostMapping("/{type}/inside")
    public String runInside(@PathVariable String type,
                            @RequestParam(defaultValue = "false") boolean failOuter) {
        return demoService.runInside(type, failOuter);
    }

    @PostMapping("/{type}/outside")
    public String runOutside(@PathVariable String type) {
        return demoService.runOutside(type);
    }

    @GetMapping("/records")
    public List<PropagationRecord> records() {
        return recordRepository.findAllByOrderByIdAsc();
    }

    @DeleteMapping("/records")
    public String clearRecords() {
        recordRepository.deleteAllInBatch();
        return "Propagation records cleared";
    }

    @GetMapping("/audit-logs")
    public List<AuditLog> auditLogs() {
        return auditRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/audit-logs/{transactionReference}")
    public List<AuditLog> auditLogsForTransaction(@PathVariable String transactionReference) {
        return auditRepository.findByTransactionReferenceOrderByCreatedAtAsc(transactionReference);
    }

    @DeleteMapping("/audit-logs")
    public String clearAuditLogs() {
        auditRepository.deleteAllInBatch();
        return "Audit logs cleared";
    }
}
