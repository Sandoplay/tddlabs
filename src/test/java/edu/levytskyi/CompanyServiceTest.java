package edu.levytskyi;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyServiceTest {

    private final ICompanyService service = new CompanyServiceImpl();

    @Test
    public void shouldReturnSameCompanyWhenNoParent() {
        Company top = new Company(null, 10);
        assertEquals(top, service.getTopLevelParent(top));
    }

    @Test
    public void shouldReturnTopLevelParentForOneLevelUp() {
        Company top = new Company(null, 10);
        Company child = new Company(top, 5);
        assertEquals(top, service.getTopLevelParent(child));
    }

    @Test
    public void shouldReturnTopLevelParentForDeepHierarchy() {
        Company top = new Company(null, 10);
        Company middle = new Company(top, 5);
        Company child = new Company(middle, 2);
        assertEquals(top, service.getTopLevelParent(child));
    }

    @Test
    public void shouldReturnNullWhenInputIsNull() {
        assertNull(service.getTopLevelParent(null));
    }

    @Test
    public void shouldReturnOwnEmployeesWhenNoChildren() {
        Company top = new Company(null, 10);
        List<Company> allCompanies = Arrays.asList(top);
        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(top, allCompanies));
    }

    @Test
    public void shouldReturnZeroWhenInputsAreNull() {
        assertEquals(0, service.getEmployeeCountForCompanyAndChildren(null, null));
        assertEquals(0, service.getEmployeeCountForCompanyAndChildren(new Company(null, 0), null));
        assertEquals(0, service.getEmployeeCountForCompanyAndChildren(null, Arrays.asList(new Company(null, 0))));
    }

    @Test
    public void shouldReturnOwnEmployeesWhenListIsEmpty() {
        Company top = new Company(null, 10);
        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(top, Arrays.asList()));
    }

    @Test
    public void shouldCalculateTotalEmployeesInComplexHierarchy() {
        Company root = new Company(null, 100);
        Company branch1 = new Company(root, 50);
        Company branch2 = new Company(root, 30);
        Company subBranch1 = new Company(branch1, 20);
        Company subBranch2 = new Company(branch1, 10);
        Company leaf = new Company(subBranch2, 5);
        
        List<Company> all = Arrays.asList(root, branch1, branch2, subBranch1, subBranch2, leaf);
        
        // Root: 100 + 50 + 30 + 20 + 10 + 5 = 215
        assertEquals(215, service.getEmployeeCountForCompanyAndChildren(root, all));
        // Branch1: 50 + 20 + 10 + 5 = 85
        assertEquals(85, service.getEmployeeCountForCompanyAndChildren(branch1, all));
    }

    @Test
    public void shouldCountOnlyEmployeesInList() {
        Company top = new Company(null, 10);
        Company child = new Company(top, 5);
        // child is not in the list, so only top's employees should be counted
        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(top, Arrays.asList(top)));
    }

    @Test
    public void shouldCalculateEmployeesForNestedChildren() {
        Company top = new Company(null, 10);
        Company child1 = new Company(top, 5);
        Company child2 = new Company(top, 3);
        Company grandChild = new Company(child1, 2);
        
        List<Company> allCompanies = Arrays.asList(top, child1, child2, grandChild);
        
        // top (10) + child1 (5) + child2 (3) + grandChild (2) = 20
        assertEquals(20, service.getEmployeeCountForCompanyAndChildren(top, allCompanies));
        
        // child1 (5) + grandChild (2) = 7
        assertEquals(7, service.getEmployeeCountForCompanyAndChildren(child1, allCompanies));
    }
}
