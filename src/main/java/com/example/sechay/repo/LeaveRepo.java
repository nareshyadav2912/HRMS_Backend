package com.example.sechay.repo;

import com.example.sechay.model.Employee;
import com.example.sechay.model.LeaveRequest;
import com.example.sechay.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepo extends JpaRepository<LeaveRequest,Integer> {
    List<LeaveRequest> findLeaveRequestByEmployee(Employee employee);

    List<LeaveRequest> findByLeaveStatus(LeaveStatus leaveStatus);
}
