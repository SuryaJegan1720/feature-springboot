package com.hackerrank.api.service.impl;

import com.hackerrank.api.exception.BadRequestException;
import com.hackerrank.api.model.Scan;
import com.hackerrank.api.repository.ScanRepository;
import com.hackerrank.api.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultScanService implements ScanService {
	@Autowired
	private ScanRepository scanRepository;

  @Autowired
  DefaultScanService(ScanRepository scanRepository) {
    this.scanRepository = scanRepository;
  }

  @Override
  public List<Scan> getAllScan() {
    return scanRepository.findAll().stream()
            .filter(scan -> !scan.isDeleted())
            .collect(Collectors.toList());
  }


  @Override
  public Scan createNewScan(Scan scan) {
    if (scan.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Scan");
    }

    return scanRepository.save(scan);
  }

  @Override
  public Scan getScanById(Long id) {
    Optional<Scan> optionalScan = scanRepository.findById(id);
    return optionalScan.orElse(null);
  }

  @Override
  public void deleteById(Long id) {
	  Optional<Scan> optionalScan = scanRepository.findById(id);
	  optionalScan.ifPresent(entity-> {
		  entity.setDeleted(true);
		  scanRepository.save(entity);
	  });

  }


@SuppressWarnings("unchecked")
@Override
public List<Scan> getScansFilteredAndSorted(String domainName, String columnName) {
	List<Scan> scans = scanRepository.findByDomainName(domainName);
	String getterMethodName = "get" + columnName.substring(0,1).toUpperCase()+columnName.substring(1);
	System.out.println(getterMethodName);
	Comparator<Scan> comparator = Comparator.comparing(scan -> {
        try {
            Method method = scan.getClass().getMethod(getterMethodName);
            return (Comparable) method.invoke(scan);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null; 
        }
    });

    List<Scan> sortedList = scans.stream().sorted(comparator).collect(Collectors.toList());

	
	return sortedList;
}
}
