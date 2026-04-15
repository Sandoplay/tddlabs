package edu.levytskyi.service;

import edu.levytskyi.model.Company;
import edu.levytskyi.repository.DatabaseManager;
import edu.levytskyi.request.CompanyCreateRequest;
import edu.levytskyi.request.CompanyPageRequest;
import edu.levytskyi.response.ApiResponse;
import edu.levytskyi.response.BaseMetaData;

import edu.levytskyi.response.PaginationMetaData;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CompanyServiceImpl implements ICompanyService {

    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM companies";
        try (Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Company c = new Company(rs.getLong("id"), rs.getString("name"),
                    rs.getString("code"), rs.getLong("employees_count"));

                // ДОДАЙТЕ ЦІ РЯДКИ:
                c.setCreatedBy(rs.getString("created_by"));
                if (rs.getTimestamp("created_date") != null) {
                    c.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                }

                companies.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return companies;
    }

    @Override
    public ApiResponse<BaseMetaData, Company> getByIdAsApiResponse(Long id) {
        List<Company> data = new ArrayList<>();
        String sql = "SELECT * FROM companies WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Company c = new Company(rs.getLong("id"), rs.getString("name"),
                    rs.getString("code"), rs.getLong("employees_count"));
                data.add(c);
                return new ApiResponse<>(new BaseMetaData(true, 200, null), data);
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return new ApiResponse<>(new BaseMetaData(false, 404, "Not found"), data);
    }


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

    @Override
    public void create(CompanyCreateRequest request) {
        String sql = "INSERT INTO companies (name, code, employees_count, created_date, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = edu.levytskyi.repository.DatabaseManager.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, request.getName());
            pstmt.setString(2, request.getCode());
            pstmt.setLong(3, request.getEmployeesCount());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            pstmt.setString(5, "admin");
            pstmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateName(Long id, String newName) {
        String sql = "UPDATE companies SET name = ? WHERE id = ?";
        try (Connection conn = edu.levytskyi.repository.DatabaseManager.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM companies WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Company getByCode(String code) {
        String sql = "SELECT * FROM companies WHERE code = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Company(rs.getLong("id"), rs.getString("name"),
                    rs.getString("code"), rs.getLong("employees_count"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public ApiResponse<PaginationMetaData, Company> getCompaniesPage(CompanyPageRequest request) {
        List<Company> all = getAll();
        int start = request.page() * request.size();
        int end = Math.min(start + request.size(), all.size());
        List<Company> pagedData = (start < all.size()) ? all.subList(start, end) : new java.util.ArrayList<>();
        PaginationMetaData meta = new PaginationMetaData(request.page(), request.size(), all.size());
        return new ApiResponse<>(meta, pagedData);
    }

}