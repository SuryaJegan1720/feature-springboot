package com.hackerrank.api.controller;

import com.hackerrank.api.model.Scan;
import com.hackerrank.api.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/scan")
public class ScanController {

	@Autowired
	private ScanService scanService;

  @Autowired
  public ScanController(ScanService scanService) {
    this.scanService = scanService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Scan> getAllScan() {
    return scanService.getAllScan();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Scan createScan(@RequestBody Scan scan) {
    return scanService.createNewScan(scan);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Scan> getScanDataById(@PathVariable Long id) {
	  try {
		 Scan scan = scanService.getScanById(id);
		 if(scan == null) {
			 throw new Exception();
		 }
	  return ResponseEntity.ok(scan);
	  }catch (Exception e) {
			return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
	  }
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteScanDataById(@PathVariable Long id) {
	  try {
		  scanService.deleteById(id);
		  return ResponseEntity.ok().build();	
	} catch (Exception e) {
		return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
	}
  }
  
  @GetMapping("/search/{domainName}")
  public ResponseEntity<List<Scan>> getScansFilteredAndSorted(@PathVariable String domainName, @RequestParam("orderBy") String columnName){
	  List<Scan> scans;
	  
	  try {
		  scans = scanService.getScansFilteredAndSorted(domainName, columnName);
		  return ResponseEntity.ok(scans);
	  } catch (Exception e) {
		  return ResponseEntity.status(HttpStatusCode.valueOf(400)).build();  
	  }
  }
  
  

  
  
}
