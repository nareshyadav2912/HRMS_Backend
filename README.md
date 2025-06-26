# HRMS Backend

A Spring Boot-based Human Resource Management System (HRMS) backend providing secure JWT authentication and role-based access control for HR and Employees. Includes integrated Swagger UI for testing.

## 🔐 Features

- **Role-based JWT Authentication**
  - HR and Employee login via `/authenticate`
- **Employee Functionalities**
  - Apply for leave
  - View/update profile
  - Download payslips
- **HR Functionalities**
  - Manage employees (add/delete)
  - Approve/reject leave requests
  - Upload payslips
  - Assign tasks
- **Swagger UI Integration**
  - Test all secured APIs with JWT

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Security + JWT
- Swagger / OpenAPI (`springdoc-openapi`)
- Maven
- H2 / MySQL (configurable)

---

## 🚀 Run the Project

### 1. Build

```bash
mvn clean install
