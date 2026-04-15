package edu.levytskyi.service;

import edu.levytskyi.model.Company;
import edu.levytskyi.response.ApiResponse;
import edu.levytskyi.response.BaseMetaData;
import java.util.List;
import edu.levytskyi.request.CompanyCreateRequest;

public interface ICompanyService {
    Company getTopLevelParent(Company child);
    long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies);

    List<Company> getAll();
    ApiResponse<BaseMetaData, Company> getByIdAsApiResponse(Long id);

    void create(CompanyCreateRequest request);
    void updateName(Long id, String newName);

    void deleteById(Long id);
    Company getByCode(String code);
}
