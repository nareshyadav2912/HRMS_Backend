package com.example.sechay.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name="employee")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;

    @Column(unique = true)
    private String empEmail;
    private String empRole;
    private String empPassword;

    private String empFirstName;
    private String empLastName;
    private String empGender;
    private String empContact;
    private String empAddress;
    private String empNewPassword;
    private String empConfirmPassword;
    private boolean profileComplete;

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

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpNewPassword() {
        return empNewPassword;
    }

    public void setEmpNewPassword(String empNewPassword) {
        this.empNewPassword = empNewPassword;
    }

    public String getEmpConfirmPassword() {
        return empConfirmPassword;
    }

    public void setEmpConfirmPassword(String empConfirmPassword) {
        this.empConfirmPassword = empConfirmPassword;
    }

    public boolean isProfileComplete() {
        return profileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        this.profileComplete = profileComplete;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empEmail='" + empEmail + '\'' +
                ", empRole='" + empRole + '\'' +
                ", empPassword='" + empPassword + '\'' +
                ", empFirstName='" + empFirstName + '\'' +
                ", empLastName='" + empLastName + '\'' +
                ", empGender='" + empGender + '\'' +
                ", empContact='" + empContact + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empNewPassword='" + empNewPassword + '\'' +
                ", empConfirmPassword='" + empConfirmPassword + '\'' +
                ", profileComplete='"+ profileComplete+'\''+
                '}';
    }
}
