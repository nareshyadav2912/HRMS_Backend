package com.example.sechay.config;

import com.example.sechay.service.JwtService;
import com.example.sechay.service.JwtService;
import com.example.sechay.service.EmployeeDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Jwtfilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization");
        String token=null;
        String empEmail=null;
        System.out.println("Header: " + header);
        if(header!=null && header.startsWith("Bearer ")){
            token=header.substring(7);
            empEmail=jwtService.extractUserName(token);
            System.out.println("Extracted Token: " + token);
            System.out.println("Extracted Email: " + empEmail);
        }
        if(empEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=context.getBean(EmployeeDetail.class).loadUserByUsername(empEmail);
            try {
                Claims claims = Jwts.parserBuilder().setSigningKey(jwtService.getKey()).build().parseClaimsJws(token).getBody();
                empEmail = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);
                List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                System.out.println("JWT Roles: " + roles);
                System.out.println("Authorities: " + authorities);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return;
            }
        }
        System.out.println("Auth in context: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request,response);

    }
}