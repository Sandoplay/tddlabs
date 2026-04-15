package edu.levytskyi.service;

import edu.levytskyi.model.Company;
import edu.levytskyi.request.CompanyPageRequest;
import edu.levytskyi.response.ApiResponse;
import edu.levytskyi.response.BaseMetaData;
import edu.levytskyi.response.PaginationMetaData;
import java.util.List;
import edu.levytskyi.request.CompanyCreateRequest;
import org.springframework.stereotype.Service;

@Service
public interface ICompanyService {
    Company getTopLevelParent(Company child);
    long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies);

    List<Company> getAll();
    ApiResponse<BaseMetaData, Company> getByIdAsApiResponse(Long id);

    void create(CompanyCreateRequest request);
    void updateName(Long id, String newName);

    void deleteById(Long id);
    Company getByCode(String code);

    ApiResponse<PaginationMetaData, Company> getCompaniesPage(CompanyPageRequest request);
}
