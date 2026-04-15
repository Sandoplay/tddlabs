package edu.levytskyi.request;

/*
 @author Sandoplay
 @project lab1
 @class CompanyCreateRequest
 @version 1.0.0
 @since 15.04.2026 - 19.20
*/

public class CompanyCreateRequest {
  private String name;
  private String code;
  private long employeesCount;

  // Геттери та сеттери
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }
  public long getEmployeesCount() { return employeesCount; }
  public void setEmployeesCount(long employeesCount) { this.employeesCount = employeesCount; }
}