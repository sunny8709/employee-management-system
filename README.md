# 🏢 Employee Management & Payroll System

A simple **Employee Management and Payroll System** built with **Java 17**, **Spring Boot 3.x**, and **MySQL**, demonstrating core **Object-Oriented Programming (OOP)** principles with a layered architecture.

---
## 🎯 Overview

This is a **console-based Employee Management System** that provides basic functionality for managing employees, tracking attendance, and processing payroll. It demonstrates:

- ✅ **Layered Architecture** (Controller → Service → Repository → Model)
- ✅ **OOP Principles** (Encapsulation, Inheritance, Polymorphism, Abstraction)
- ✅ **Spring Boot 3.x** with JPA/Hibernate
- ✅ **MySQL Database** integration
- ✅ **Simple Authentication** (username/password)

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
- 📊 View attendance reports
- ⏰ Track check-in/check-out times

### 3. **Payroll Processing**
- 💰 Generate monthly payroll
- 📈 Calculate salaries based on employee type
- 📜 View payroll history

### 4. **Employee Types**
- 👔 **Full-Time Employees**: Salary + 15% bonus
- ⏱️ **Part-Time Employees**: Hourly rate × hours worked
- 📝 **Contract Employees**: Fixed contract amount

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
    
    // Getters and Setters (via Lombok @Data)
}
```

### 2. **Inheritance** 🌳
Employee hierarchy demonstrates class inheritance:

```
Employee (Base Class)
├── FullTimeEmployee
├── PartTimeEmployee
└── ContractEmployee
```

### 3. **Polymorphism** 🔄
Method overriding for employee type-specific salary calculations:

```java
// FullTimeEmployee: Base salary + 15% bonus
public Double calculateSalary() {
    return getSalary() + (getSalary() * 0.15);
}

// PartTimeEmployee: Hourly rate × hours worked
public Double calculateSalary() {
    return hourlyRate * hoursWorked;
}

// ContractEmployee: Fixed contract amount
public Double calculateSalary() {
    return contractAmount;
}
```

### 4. **Abstraction** 🎭
Interface-based design for service contracts:

```java
public interface PayrollOperations {
    Double calculateSalary(Long employeeId);
    Payroll generatePayrollReport(Long employeeId, String month, Integer year);
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
| **Build Tool** | Maven | 3.x |
| **Testing** | JUnit 5 | 5.x |

---

## 📁 Project Structure

```
employeeManagement/
│
├── src/
│   ├── main/
│   │   ├── java/com/employee/
│   │   │   ├── App.java                    # Main application entry point
│   │   │   ├── controller/
│   │   │   │   └── MainMenu.java           # Console-based controller
│   │   │   ├── interfaces/
│   │   │   │   └── PayrollOperations.java  # Service interface (Abstraction)
│   │   │   ├── model/                      # Entity classes
│   │   │   │   ├── Employee.java           # Base class
│   │   │   │   ├── FullTimeEmployee.java   # Inheritance + Polymorphism
│   │   │   │   ├── PartTimeEmployee.java   # Inheritance + Polymorphism
│   │   │   │   ├── ContractEmployee.java   # Inheritance + Polymorphism
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
│   │   │       ├── AuthService.java        # Credential validation
│   │   │       └── LoginService.java
│   │   └── resources/
│   │       └── application.yml             # Configuration file
│   │
│   └── test/
│       └── java/com/employee/model/
│           └── EmployeeModelTest.java      # OOP tests
│
├── pom.xml                                 # Maven dependencies
└── README.md                               # This file
```

---



### **Prerequisites**

1. ✅ **Java 17** or higher
2. ✅ **Maven 3.6+**
3. ✅ **MySQL 8.0+**
4. ✅ **IDE** (IntelliJ IDEA, Eclipse, or VS Code)


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
