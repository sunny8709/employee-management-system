# 🏢 Employee Management & Payroll System

A comprehensive **Employee Management and Payroll System** built with **Java 17**, **Spring Boot 3.x**, and **MySQL**, demonstrating core **Object-Oriented Programming (OOP)** principles with a layered architecture.

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Setup & Installation](#-setup--installation)
- [Running the Application](#-running-the-application)
- [Application Flow](#-application-flow)
- [API Endpoints](#-api-endpoints)
- [Testing](#-testing)
- [Default Credentials](#-default-credentials)
- [Contributing](#-contributing)

---

## 🎯 Overview

This project is a **console-based Employee Management System** that provides comprehensive functionality for managing employees, tracking attendance, and processing payroll. It showcases professional software development practices including:

- ✅ **Layered Architecture** (Controller → Service → Repository)
- ✅ **OOP Principles** (Encapsulation, Inheritance, Polymorphism, Abstraction)
- ✅ **Spring Boot 3.x** with JPA/Hibernate
- ✅ **MySQL Database** with automatic schema generation
- ✅ **Spring Security** for authentication
- ✅ **Comprehensive Unit Testing** with JUnit 5

---

## ✨ Key Features

### 1. **Employee Management**
- ➕ Add new employees
- 👁️ View employee details
- ✏️ Update employee information
- 🗑️ Delete employees
- 📋 List all employees

### 2. **Attendance Tracking**
- 📅 Mark daily attendance (PRESENT/ABSENT)
- 📊 Generate attendance reports
- ⏰ Track check-in/check-out times
- 🕐 Calculate hours worked

### 3. **Payroll Processing**
- 💰 Generate monthly payroll
- 📈 Calculate salaries with role-based bonuses
- 📜 View payroll history
- 💵 Track allowances and deductions

### 4. **Employee Types**
- 👔 **Full-Time Employees**: Benefits + Annual Leave
- ⏱️ **Part-Time Employees**: Hourly rate calculation
- 📝 **Contract Employees**: Contract-based payments
- 💻 **Developers**: Project completion bonuses
- 🧪 **Testers**: Bug-finding incentives
- 👥 **HR**: Employee management bonuses

---

## 🎓 OOP Concepts Demonstrated

### 1. **Encapsulation** 🔒
All model classes use **private fields** with **public getters/setters** to control access:

```java
public class Employee {
    private Long employeeId;
    private String name;
    private String department;
    private Double salary;
    
    // Getters and Setters
}
```

### 2. **Inheritance** 🌳
Employee hierarchy demonstrates class inheritance:

```
Employee (Base Class)
├── FullTimeEmployee
├── PartTimeEmployee
├── ContractEmployee
├── Developer
├── Tester
└── HR
```

### 3. **Polymorphism** 🔄
Method overriding for role-specific salary calculations:

```java
// Developer: Base salary + (Projects × $1,000)
public Double calculateSalary() {
    return getSalary() + (projectsCompleted * 1000);
}

// Tester: Base salary + (Bugs × $50)
public Double calculateSalary() {
    return getSalary() + (bugsFound * 50);
}

// HR: Base salary + (Employees Managed × $200)
public Double calculateSalary() {
    return getSalary() + (employeesManaged * 200);
}
```

### 4. **Abstraction** 🎭
Interface-based design for service contracts:

```java
public interface PayrollOperations {
    void generatePayrollReport(Long employeeId, String month, Integer year);
    List<Payroll> getEmployeePayrollHistory(Long employeeId);
}
```

---

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 17 |
| **Framework** | Spring Boot | 3.2.0 |
| **ORM** | Hibernate (JPA) | 6.x |
| **Database** | MySQL | 8.x |
| **Security** | Spring Security | 6.x |
| **Build Tool** | Maven | 3.x |
| **Testing** | JUnit 5 | 5.x |
| **Validation** | Spring Validation | 3.x |
| **Monitoring** | Spring Actuator | 3.x |

---

## 📁 Project Structure

```
employeeManagement/
│
├── src/
│   ├── main/
│   │   ├── java/com/employee/
│   │   │   ├── App.java                    # Main application entry point
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java     # Security configuration
│   │   │   ├── controller/
│   │   │   │   └── MainMenu.java           # Console-based controller
│   │   │   ├── interfaces/
│   │   │   │   └── PayrollOperations.java  # Service interface (Abstraction)
│   │   │   ├── model/                      # Entity classes
│   │   │   │   ├── Employee.java           # Base class
│   │   │   │   ├── FullTimeEmployee.java   # Inheritance
│   │   │   │   ├── PartTimeEmployee.java   # Inheritance
│   │   │   │   ├── ContractEmployee.java   # Inheritance
│   │   │   │   ├── Developer.java          # Polymorphism
│   │   │   │   ├── Tester.java             # Polymorphism
│   │   │   │   ├── HR.java                 # Polymorphism
│   │   │   │   ├── Attendance.java
│   │   │   │   ├── Payroll.java
│   │   │   │   └── User.java
│   │   │   ├── repository/                 # Data access layer
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   ├── AttendanceRepository.java
│   │   │   │   ├── PayrollRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/                    # Business logic layer
│   │   │       ├── EmployeeService.java
│   │   │       ├── AttendanceService.java
│   │   │       ├── PayrollService.java
│   │   │       ├── AuthService.java
│   │   │       └── LoginService.java
│   │   └── resources/
│   │       └── application.yml             # Configuration file
│   │
│   └── test/
│       └── java/com/employee/model/
│           └── EmployeeModelTest.java      # Comprehensive OOP tests
│
├── pom.xml                                 # Maven dependencies
└── README.md                               # This file
```

---

## 🗄️ Database Schema

### **Entities**

1. **Employee** - Base employee information
2. **FullTimeEmployee** - Full-time specific data
3. **PartTimeEmployee** - Part-time specific data
4. **ContractEmployee** - Contract specific data
5. **Developer** - Developer specific data
6. **Tester** - Tester specific data
7. **HR** - HR specific data
8. **Attendance** - Daily attendance records
9. **Payroll** - Monthly payroll records
10. **User** - Authentication credentials

### **Relationships**

- **One-to-Many**: Employee → Attendance
- **One-to-Many**: Employee → Payroll
- **Inheritance**: Single Table Strategy for employee types

---

## 🚀 Setup & Installation

### **Prerequisites**

1. ✅ **Java 17** or higher
2. ✅ **Maven 3.6+**
3. ✅ **MySQL 8.0+**
4. ✅ **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### **Step 1: Clone the Repository**

```bash
git clone <repository-url>
cd employeeManagement
```

### **Step 2: Configure MySQL Database**

1. **Create Database**:
```sql
CREATE DATABASE employee_management_db;
```

2. **Update Configuration**:
Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employee_management_db
    username: root          # Your MySQL username
    password: your_password # Your MySQL password
```

### **Step 3: Build the Project**

```bash
mvn clean install
```

### **Step 4: Run Tests** (Optional)

```bash
mvn test
```

---

## ▶️ Running the Application

### **Option 1: Using Maven**

```bash
mvn spring-boot:run
```

### **Option 2: Using JAR**

```bash
mvn clean package
java -jar target/employee-management-system-1.0.0.jar
```

### **Option 3: Using IDE**

Run the `App.java` main class directly from your IDE.

---

## 🔄 Application Flow

### **1. Application Startup**

```
┌─────────────────────────────────────┐
│   Spring Boot Application Starts   │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  Database Connection Established    │
│  (Hibernate creates/updates schema) │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Default Admin User Created        │
│   (if not exists)                   │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│      Login Screen Displayed         │
└─────────────────────────────────────┘
```

### **2. Authentication Flow**

```
User enters credentials
         │
         ▼
LoginService validates
         │
    ┌────┴────┐
    │         │
   YES       NO
    │         │
    ▼         ▼
Main Menu   Exit
```

### **3. Main Menu Flow**

```
┌─────────────────────────────────────┐
│       Main Menu                     │
│  1. Employee Management             │
│  2. Attendance Tracking             │
│  3. Payroll Processing              │
│  4. Exit                            │
└──────────────┬──────────────────────┘
               │
       ┌───────┼───────┬───────┐
       │       │       │       │
       ▼       ▼       ▼       ▼
   Employee Attendance Payroll Exit
   Operations Operations Operations
```

### **4. Employee Operations**

```
┌─────────────────────────────────────┐
│   Employee Management Menu          │
│  1. Add Employee                    │
│  2. View Employee                   │
│  3. Update Employee                 │
│  4. Delete Employee                 │
│  5. View All Employees              │
└─────────────────────────────────────┘
         │
         ▼
   User Input → Service Layer → Repository → Database
         │
         ▼
   Response Displayed
```

### **5. Data Flow Architecture**

```
┌──────────────┐
│   Console    │  ← User Interaction
└──────┬───────┘
       │
       ▼
┌──────────────┐
│  Controller  │  ← MainMenu.java
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Service    │  ← Business Logic
└──────┬───────┘
       │
       ▼
┌──────────────┐
│  Repository  │  ← Data Access
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   Database   │  ← MySQL
└──────────────┘
```

---

## 🌐 API Endpoints

While this is primarily a console application, **Spring Actuator** provides monitoring endpoints:

| Endpoint | Description |
|----------|-------------|
| `http://localhost:8080/actuator/health` | Application health status |
| `http://localhost:8080/actuator/info` | Application information |
| `http://localhost:8080/actuator/metrics` | Application metrics |

---

## 🧪 Testing

### **Run All Tests**

```bash
mvn test
```

### **Test Coverage**

The `EmployeeModelTest.java` includes comprehensive tests for:

- ✅ **Encapsulation**: Getter/Setter validation
- ✅ **Inheritance**: Subclass property inheritance
- ✅ **Polymorphism**: Method overriding verification
- ✅ **Abstraction**: Interface implementation
- ✅ **Model Integrity**: Attendance, Payroll, User models

### **Sample Test Output**

```
✅ Encapsulation Test PASSED - All getters/setters working
✅ Inheritance Test PASSED - FullTimeEmployee inherits from Employee
✅ Polymorphism Test PASSED - Developer overrides calculateSalary()
✅ Attendance Model Test PASSED
✅ Payroll Model Test PASSED
========================================
ALL OOP PRINCIPLES VERIFIED ✅
========================================
```

---

## 🔑 Default Credentials

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | ADMIN |

> **Note**: The default admin user is automatically created on first run.

---

## 📝 Usage Examples

### **Example 1: Adding an Employee**

```
Choose an option: 1
Enter name: John Doe
Enter department: IT
Enter salary: 75000
Employee added successfully with ID: 1
```

### **Example 2: Marking Attendance**

```
Choose an option: 2
Enter employee ID: 1
Enter status (PRESENT/ABSENT): PRESENT
Attendance marked successfully
```

### **Example 3: Generating Payroll**

```
Choose an option: 3
Enter employee ID: 1
Enter month: January
Enter year: 2026
Payroll generated successfully
```

---

## 🎨 Design Patterns Used

1. **Singleton Pattern**: Spring Bean management
2. **Repository Pattern**: Data access abstraction
3. **Service Layer Pattern**: Business logic separation
4. **Dependency Injection**: Constructor-based injection with Lombok
5. **Template Method Pattern**: JPA repository inheritance

---

## 🔧 Configuration

### **application.yml**

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/employee_management_db
    username: root
    password: your_password
  
  jpa:
    hibernate:
      ddl-auto: update      # Auto-create/update schema
    show-sql: true          # Log SQL queries
  
server:
  port: 8080                # Application port
```

---

## 🐛 Troubleshooting

### **Issue: Database Connection Failed**
- ✅ Verify MySQL is running
- ✅ Check database credentials in `application.yml`
- ✅ Ensure database `employee_management_db` exists

### **Issue: Port 8080 Already in Use**
- ✅ Change port in `application.yml`: `server.port: 8081`
- ✅ Or stop the process using port 8080

### **Issue: Tests Failing**
- ✅ Ensure database is accessible
- ✅ Run `mvn clean install` to rebuild
- ✅ Check test logs for specific errors

---

## 📚 Learning Outcomes

This project demonstrates:

1. ✅ **OOP Mastery**: All four pillars implemented
2. ✅ **Spring Boot Proficiency**: Modern Java framework usage
3. ✅ **Database Integration**: JPA/Hibernate with MySQL
4. ✅ **Layered Architecture**: Separation of concerns
5. ✅ **Testing Best Practices**: Comprehensive unit tests
6. ✅ **Security Implementation**: Spring Security basics
7. ✅ **Professional Code Structure**: Clean, maintainable code

---


<div align="center">

**⭐ If you found this project helpful, please give it a star! ⭐**

Made with ❤️ using Java & Spring Boot

</div>
