package com.example.sechay.service;

import com.example.sechay.model.Employee;
import com.example.sechay.model.EmployePrincipal;
import com.example.sechay.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetail implements UserDetailsService {

    @Autowired
    private EmployeeRepo repo;
    @Override
    public UserDetails loadUserByUsername(String empEmail) throws UsernameNotFoundException {
        Employee employee=repo.findByEmpEmail(empEmail);
        if(employee==null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return new EmployePrincipal(employee);
    }
}
