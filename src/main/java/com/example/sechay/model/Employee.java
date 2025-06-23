package com.example.sechay.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {
    @Id
    private Integer empId;
    @Column(unique = true)
    private String empEmail;
    private String empRole;
    private String empPassword;

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empEmail='" + empEmail + '\'' +
                ", empRole='" + empRole + '\'' +
                ", empPassword='" + empPassword + '\'' +
                '}';
    }

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

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }
}
