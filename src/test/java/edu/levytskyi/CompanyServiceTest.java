package edu.levytskyi;

import edu.levytskyi.model.Company;
import edu.levytskyi.repository.DatabaseManager;
import edu.levytskyi.request.CompanyCreateRequest;
import edu.levytskyi.response.ApiResponse;
import edu.levytskyi.response.BaseMetaData;
import edu.levytskyi.service.CompanyServiceImpl;
import edu.levytskyi.service.ICompanyService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyServiceTest {

    private ICompanyService underTest;

    @BeforeEach
    void setUp() {
        DatabaseManager.initDatabase();
        underTest = new CompanyServiceImpl();
    }

    @Test
    @DisplayName("1. Перевірка загальної кількості компаній (30)")
    void whenGetAllCompaniesListThenSizeIs30() {
        assertEquals(30, underTest.getAll().size());
    }

    @Test
    @DisplayName("2. Отримання існуючої компанії (200 OK)")
    void whenCompanyIsPresentThenReturnAsOkApiResponse() {
        ApiResponse<BaseMetaData, Company> response = underTest.getByIdAsApiResponse(1L);
        assertTrue(response.getMeta().isSuccess());
        assertEquals(200, response.getMeta().getCode());
    }

    @Test
    @DisplayName("3. Отримання неіснуючої компанії (404 Not Found)")
    void whenCompanyIsNotPresentThenReturn404() {
        ApiResponse<BaseMetaData, Company> response = underTest.getByIdAsApiResponse(999L);
        assertEquals(404, response.getMeta().getCode());
    }

    @Test
    @DisplayName("4. Створення нової компанії")
    void whenCreateCompanyThenSizeIncreases() {
        CompanyCreateRequest request = new CompanyCreateRequest();
        request.setName("TDD StartUp");
        request.setCode("TDD-777");
        request.setEmployeesCount(5);

        underTest.create(request);
        assertEquals(31, underTest.getAll().size());
    }

    @Test
    @DisplayName("5. Оновлення імені компанії")
    void whenUpdateCompanyNameThenItChanges() {
        underTest.updateName(2L, "Updated Name");
        assertEquals("Updated Name", underTest.getAll().get(1).getName());
    }

    @Test
    @DisplayName("6. Видалення компанії")
    void whenDeleteCompanyThenSizeDecreases() {
        underTest.deleteById(3L);
        assertEquals(29, underTest.getAll().size());
    }

    @Test
    @DisplayName("7. Пошук за унікальним кодом")
    void whenGetByCodeThenReturnCorrectCompany() {
        String code = "COMP-005"; // Згенеровано в DatabaseManager
        Company company = underTest.getByCode(code);
        assertNotNull(company);
        assertEquals("Компанія №5", company.getName());
    }

    @Test
    @DisplayName("8. Перевірка аудит-метаданих (CreatedBy)")
    void whenCompanyFetchedThenAuditIsPresent() {
        Company company = underTest.getAll().get(0);
        assertNotNull(company.getCreatedBy());
        assertEquals("admin_user", company.getCreatedBy());
    }

    @Test
    @DisplayName("9. Пошук головної компанії (Hierarchy Test)")
    void whenGetTopLevelParentThenReturnRoot() {
        // given
        Company root = new Company(1L, "Root", "R", 100);
        Company child = new Company(2L, "Child", "C", 50);
        child.setParent(root);

        // when
        Company result = underTest.getTopLevelParent(child);

        // then
        assertEquals("Root", result.getName());
    }

    @Test
    @DisplayName("10. Підрахунок співробітників з дочірніми (Hierarchy Test)")
    void whenGetTotalEmployeesThenSumIsCorrect() {
        // given
        Company parent = new Company(1L, "Parent", "P", 100);
        Company child1 = new Company(2L, "Child1", "C1", 50);
        Company child2 = new Company(3L, "Child2", "C2", 30);

        child1.setParent(parent);
        child2.setParent(parent);

        List<Company> all = List.of(parent, child1, child2);

        // when
        long total = underTest.getEmployeeCountForCompanyAndChildren(parent, all);

        // then
        assertEquals(180, total); // 100 + 50 + 30
    }
}