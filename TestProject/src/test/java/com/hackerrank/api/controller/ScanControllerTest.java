package com.hackerrank.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.api.model.Scan;
import com.hackerrank.api.repository.ScanRepository;
import com.hackerrank.api.service.ScanService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
class ScanControllerTest {
  ObjectMapper om = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ScanRepository scanRepository;
  
  @Autowired
  private ScanService scanService;

  @Test
  public void testCreation() throws Exception {
    Scan expectedRecord = Scan.builder().domainName("java.com").build();
    System.out.println(expectedRecord.toString());
    Scan actualRecord = om.readValue(mockMvc.perform(post("/scan")
            .contentType("application/json")
            .content(om.writeValueAsString(expectedRecord)))
            .andDo(print())
            .andExpect(jsonPath("$.id", greaterThan(0)))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), Scan.class);
    System.out.println(actualRecord.toString());
      Assertions.assertEquals(expectedRecord.getDomainName(), actualRecord.getDomainName());
  }
  
  @Test
  public void testGetScanById() {
      Long id = 1L;
      Scan scan = scanService.getScanById(id);
      Optional<Scan> result =Optional.of(scan);
      Assertions.assertTrue(result.isPresent());
      Assertions.assertEquals(scan, result.get());
  }
  
  @Test
  public void testDeleteById() {
      Long id = 1L;
      scanService.deleteById(id);
      Optional<Scan> scan = scanRepository.findById(id);
      Assertions.assertTrue(scan.get().isDeleted());
  }
  
  @Test
  public void testGetScansFilteredAndSorted() {
      String domainName = "domain1.com";
      List<Scan> scans = scanService.getScansFilteredAndSorted(domainName, "numPages");
      int numberOfPages = scans.stream()
      .min(Comparator.comparingInt(Scan::getNumPages)).get().getNumPages();
      Assertions.assertEquals(scans.get(0).getNumPages(),numberOfPages);
  }
  
  @Test
  public void testGetScansFilteredAndSortedCheckEmpty() {
      String domainName = "domain1.com";
      Assertions.assertFalse(scanService.getScansFilteredAndSorted(domainName, "numPages").isEmpty());
  }
  
  @Test
  public void testGetScansFilteredAndSortedCheckNotEmpty() {
      String domainName = "domain1.com";
      Assertions.assertTrue(!scanService.getScansFilteredAndSorted(domainName, "numPages").isEmpty());
  }
  
  
  
  
  
  
  
}
