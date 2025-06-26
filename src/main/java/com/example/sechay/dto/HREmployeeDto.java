package com.example.sechay.dto;

public class HREmployeeDto {
    private Integer empId;
    private String empEmail;
    private String empPassword;
    private String empRole;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    @Override
    public String toString() {
        return "HREmployeeDto{" +
                "empId='" + empId + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", empPassword='" + empPassword + '\'' +
                ", empRole='" + empRole + '\'' +
                '}';
    }
}
