package com.example.sechay.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "payslips")
public class PaySlips {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pId;

    private String fileName;

    private String contentType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate uploadDate;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empId",nullable = false)
    private Employee employee;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "PaySlips{" +
                "pId=" + pId +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", uploadDate=" + uploadDate +
                ", data=" + Arrays.toString(data) +
                ", employee=" + employee +
                '}';
    }
}
