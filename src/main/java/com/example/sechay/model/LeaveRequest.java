package com.example.sechay.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "leaves")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leaveId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empId",nullable = false)
    private Employee employee;

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    @Override
    public String toString() {
        return "LeaveRequest{" +
                "leaveId=" + leaveId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", reason='" + reason + '\'' +
                ", leaveType=" + leaveType +
                ", leaveStatus=" + leaveStatus +
                ", employee=" + employee +
                '}';
    }
}
