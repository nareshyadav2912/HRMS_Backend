package com.example.sechay.controller;

import com.example.sechay.model.Employee;
import com.example.sechay.service.EmployeeService;
import com.example.sechay.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public Employee register(@RequestBody Employee employee){
        return employeeService.saveUser(employee);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(header==null || !header.startsWith("Basic ")){
            return ResponseEntity.badRequest().body("Missing Credentials");
        }
        String base64Credentials=header.substring("Basic ".length());
        byte[] decode= Base64.getDecoder().decode(base64Credentials);
        String[] credentials=new String(decode).split(":",2);
        String empEmail=credentials[0];
        String empPassword=credentials[1];
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(empEmail,empPassword));
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(Map.of("jwt",jwtService.generateToken(empEmail)));
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials,Authentication Failed");
        }
    }

}
