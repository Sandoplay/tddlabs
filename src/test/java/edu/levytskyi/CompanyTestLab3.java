package edu.levytskyi;

import static org.junit.jupiter.api.Assertions.*;
import edu.levytskyi.model.Company;
import edu.levytskyi.repository.DatabaseManager;
import edu.levytskyi.request.CompanyCreateRequest;
import edu.levytskyi.request.CompanyPageRequest;
import edu.levytskyi.response.ApiResponse;
import edu.levytskyi.response.BaseMetaData;
import edu.levytskyi.response.PaginationMetaData;
import edu.levytskyi.service.CompanyServiceImpl;
import edu.levytskyi.service.ICompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompanyTestLab3 {

  private ICompanyService underTest;

  @BeforeEach
  void setUp() {
    // Гарантує 30 записів перед КОЖНИМ тестом (ізоляція)
    DatabaseManager.initDatabase();
    underTest = new CompanyServiceImpl();
  }

  @Test
  @DisplayName("1. Перевірка загальної кількості (30 записів)")
  void whenGetAllCompaniesListThenSizeIs30() {
    assertEquals(30, underTest.getAll().size());
  }

  @Test
  @DisplayName("2. Перевірка полів аудиту (AuditMetadata)")
  void whenCompanyExistsThenAuditFieldsAreNotNull() {
    Company company = underTest.getAll().get(0);
    assertNotNull(company.getCreatedDate());
    assertNotNull(company.getCreatedBy());
  }

  @Test
  @DisplayName("3. Пагінація: Отримання сторінки №0 розміром 5")
  void whenGetFirstPageOfSizeFiveThenReturnFiveItems() {
    CompanyPageRequest request = new CompanyPageRequest(0, 5);
    ApiResponse<PaginationMetaData, Company> response = underTest.getCompaniesPage(request);
    assertEquals(5, response.getData().size());
    assertEquals(0, response.getMeta().getNumber());
  }

  @Test
  @DisplayName("4. Пагінація: Валідація загальної кількості елементів")
  void whenGetPageThenTotalElementsInMetaIs30() {
    CompanyPageRequest request = new CompanyPageRequest(0, 5);
    ApiResponse<PaginationMetaData, Company> response = underTest.getCompaniesPage(request);
    assertEquals(30, response.getMeta().getTotalElements());
    assertTrue(response.getMeta().isFirst());
  }

  @Test
  @DisplayName("5. Пагінація: Перевірка прапорця останньої сторінки")
  void whenGetLastPageThenIsLastIsTrue() {
    // 30 елементів по 5 = 6 сторінок (індекси 0-5). Сторінка 5 - остання.
    CompanyPageRequest request = new CompanyPageRequest(5, 5);
    ApiResponse<PaginationMetaData, Company> response = underTest.getCompaniesPage(request);
    assertTrue(response.getMeta().isLast());
  }

  @Test
  @DisplayName("6. Пошук: Успішне отримання ApiResponse (200 OK)")
  void whenGetByIdThenReturnSuccessApiResponse() {
    ApiResponse<BaseMetaData, Company> response = underTest.getByIdAsApiResponse(1L);
    assertTrue(response.getMeta().isSuccess());
    assertEquals(200, response.getMeta().getCode());
  }

  @Test
  @DisplayName("7. Пошук: Отримання помилки 404 (Not Found)")
  void whenGetByNonExistingIdThenReturn404() {
    ApiResponse<BaseMetaData, Company> response = underTest.getByIdAsApiResponse(999L);
    assertFalse(response.getMeta().isSuccess());
    assertEquals(404, response.getMeta().getCode());
  }

  @Test
  @DisplayName("8. Створення: Додавання нової компанії (TDD Create)")
  void whenCreateCompanyThenSizeIncreases() {
    CompanyCreateRequest request = new CompanyCreateRequest();
    request.setName("New TDD Corp");
    request.setCode("TDD-777");
    request.setEmployeesCount(100);

    underTest.create(request);
    assertEquals(31, underTest.getAll().size());
  }

  @Test
  @DisplayName("9. Оновлення: Зміна імені існуючої компанії")
  void whenUpdateNameThenNameIsPersisted() {
    underTest.updateName(1L, "Updated Name");
    Company company = underTest.getAll().stream()
        .filter(c -> c.getId().equals(1L)).findFirst().get();
    assertEquals("Updated Name", company.getName());
  }

  @Test
  @DisplayName("10. Видалення: Видалення запису за ID")
  void whenDeleteByIdThenSizeDecreases() {
    underTest.deleteById(1L);
    assertEquals(29, underTest.getAll().size());
  }
}