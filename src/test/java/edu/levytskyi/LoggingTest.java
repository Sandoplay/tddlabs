package edu.levytskyi;

/*
 @author Sandoplay
 @project lab1
 @class LoggingTest
 @version 1.0.0
 @since 15.04.2026 - 21.45
*/

import edu.levytskyi.request.CompanyCreateRequest;
import edu.levytskyi.service.ICompanyService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class LoggingTest {

  @Autowired
  private ICompanyService companyService;

  @Test
  @Order(1)
  void test01_getAllEntryLogging(CapturedOutput output) {
    companyService.getAll();
    assertThat(output.getOut()).contains("Entering method: getAll");
  }

  @Test
  @Order(2)
  void test02_getByIdEntryLogging(CapturedOutput output) {
    companyService.getByIdAsApiResponse(1L);
    assertThat(output.getOut()).contains("Entering method: getByIdAsApiResponse");
    assertThat(output.getOut()).contains("[1]"); // Перевірка логування аргументу ID
  }

  @Test
  @Order(3)
  void test03_createEntryLogging(CapturedOutput output) {
    CompanyCreateRequest req = new CompanyCreateRequest();
    req.setName("LogCompany");
    companyService.create(req);
    assertThat(output.getOut()).contains("Entering method: create");
  }

  @Test
  @Order(4)
  void test04_deleteEntryLogging(CapturedOutput output) {
    companyService.deleteById(1L);
    assertThat(output.getOut()).contains("Entering method: deleteById");
  }

  @Test
  @Order(5)
  void test05_updateNameEntryLogging(CapturedOutput output) {
    companyService.updateName(1L, "Updated Name");
    assertThat(output.getOut()).contains("Entering method: updateName");
    assertThat(output.getOut()).contains("Updated Name"); // Перевірка логування нового імені
  }

  @Test
  @Order(6)
  void test06_getAllSuccessExitLogging(CapturedOutput output) {
    companyService.getAll();
    assertThat(output.getOut()).contains("executed successfully"); // Перевірка @AfterReturning
  }

  @Test
  @Order(7)
  void test07_getByIdSuccessExitLogging(CapturedOutput output) {
    companyService.getByIdAsApiResponse(1L);
    assertThat(output.getOut()).contains("Method getByIdAsApiResponse executed successfully");
  }

  @Test
  @Order(8)
  void test08_createSuccessExitLogging(CapturedOutput output) {
    CompanyCreateRequest req = new CompanyCreateRequest();
    req.setName("SuccessCompany");
    companyService.create(req);
    assertThat(output.getOut()).contains("Method create executed successfully");
  }

  @Test
  @Order(9)
  void test09_deleteSuccessExitLogging(CapturedOutput output) {
    companyService.deleteById(2L);
    assertThat(output.getOut()).contains("Method deleteById executed successfully");
  }

  @Test
  @Order(10)
  void test10_updateNameSuccessExitLogging(CapturedOutput output) {
    companyService.updateName(2L, "Final Name");
    assertThat(output.getOut()).contains("Method updateName executed successfully");
  }
}