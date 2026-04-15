package edu.levytskyi.model;

public class Company extends AuditMetadata {
    private Long id;
    private String name;
    private String code;
    private long employeesCount;
    private Company parent;

    public Company() {}

    public Company(Long id, String name, String code, long employeesCount) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.employeesCount = employeesCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public long getEmployeesCount() { return employeesCount; }
    public void setEmployeesCount(long employeesCount) { this.employeesCount = employeesCount; }

    public Company getParent() { return parent; }
    public void setParent(Company parent) { this.parent = parent; }
}