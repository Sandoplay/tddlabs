package edu.levytskyi;

import java.util.List;

public class CompanyServiceImpl implements ICompanyService {
    @Override
    public Company getTopLevelParent(Company child) {
        if (child == null) {
            return null;
        }
        Company current = child;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (company == null || companies == null) {
            return 0;
        }
        long totalCount = company.getEmployeesCount();
        for (Company potentialChild : companies) {
            if (potentialChild.getParent() == company) {
                totalCount += getEmployeeCountForCompanyAndChildren(potentialChild, companies);
            }
        }
        return totalCount;
    }
}
