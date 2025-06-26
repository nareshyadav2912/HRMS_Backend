package com.example.sechay.service;


import com.example.sechay.model.Employee;
import com.example.sechay.model.LeaveRequest;
import com.example.sechay.model.LeaveStatus;
import com.example.sechay.repo.LeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepo leaveRepo;


    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest){
        leaveRequest.setLeaveStatus(LeaveStatus.PENDING);
        return leaveRepo.save(leaveRequest);
    }
    public List<LeaveRequest> getLeaveRequests(Employee employee){
        return leaveRepo.findLeaveRequestByEmployee(employee);
    }
    public boolean cancelLeave(int leaveId,String empEmail){
        LeaveRequest leaveRequest1=leaveRepo.findById(leaveId).orElse(null);
        if(leaveRequest1!=null && leaveRequest1.getEmployee().getEmpEmail().equals(empEmail)){
            leaveRepo.delete(leaveRequest1);
            return true;
        }
        return false;
    }

    public List<LeaveRequest> getAllLeaves(){
        return leaveRepo.findAll();
    }
    public List<LeaveRequest> getByStatus(LeaveStatus leaveStatus){
        return leaveRepo.findByLeaveStatus(leaveStatus);
    }
    public boolean updateLeaveStatus(int leaveId,LeaveStatus newStatus){
        LeaveRequest leaveRequest=leaveRepo.findById(leaveId).orElse(null);
        if(leaveRequest==null || !leaveRequest.getLeaveStatus().equals(LeaveStatus.PENDING)){
            return false;
        }
        leaveRequest.setLeaveStatus(newStatus);
        leaveRepo.save(leaveRequest);
        return true;
    }
}
