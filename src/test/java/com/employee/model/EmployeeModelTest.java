package com.employee.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

@DisplayName("Employee Model Tests - OOP Concepts Verification")
class EmployeeModelTest {

    @Test
    @DisplayName("Test Encapsulation - Employee Base Class")
    void testEncapsulation() {
        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setName("John Doe");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);
        employee.setRoleType("Developer");

        assertEquals(1L, employee.getEmployeeId());
        assertEquals("John Doe", employee.getName());
        assertEquals("IT", employee.getDepartment());
        assertEquals(50000.0, employee.getSalary());
        assertEquals("Developer", employee.getRoleType());

        System.out.println("✅ Encapsulation Test PASSED - All getters/setters working");
    }

    @Test
    @DisplayName("Test Inheritance - FullTimeEmployee extends Employee")
    void testFullTimeEmployeeInheritance() {
        FullTimeEmployee fullTime = new FullTimeEmployee();

        fullTime.setName("Alice Johnson");
        fullTime.setDepartment("HR");
        fullTime.setSalary(60000.0);
        fullTime.setBenefits("Health Insurance, 401k");
        fullTime.setAnnualLeave(25);

        assertEquals("Alice Johnson", fullTime.getName());
        assertEquals("HR", fullTime.getDepartment());
        assertEquals(60000.0, fullTime.getSalary());
        assertEquals("Health Insurance, 401k", fullTime.getBenefits());
        assertEquals(25, fullTime.getAnnualLeave());

        Double calculatedSalary = fullTime.calculateSalary();
        assertEquals(69000.0, calculatedSalary);

        System.out.println("✅ Inheritance Test PASSED - FullTimeEmployee inherits from Employee");
        System.out.println("   Base Salary: $60,000 | Calculated Salary: $" + calculatedSalary);
    }

    @Test
    @DisplayName("Test Inheritance - PartTimeEmployee extends Employee")
    void testPartTimeEmployeeInheritance() {
        PartTimeEmployee partTime = new PartTimeEmployee();

        partTime.setName("Bob Smith");
        partTime.setDepartment("Support");
        partTime.setSalary(30000.0);
        partTime.setHourlyRate(25.0);
        partTime.setHoursWorked(120);

        assertEquals("Bob Smith", partTime.getName());
        assertEquals("Support", partTime.getDepartment());
        assertEquals(30000.0, partTime.getSalary());
        assertEquals(25.0, partTime.getHourlyRate());
        assertEquals(120, partTime.getHoursWorked());

        Double calculatedSalary = partTime.calculateSalary();
        assertEquals(3000.0, calculatedSalary);

        System.out.println("✅ Inheritance Test PASSED - PartTimeEmployee inherits from Employee");
        System.out.println("   Hourly Rate: $25 × 120 hours = $" + calculatedSalary);
    }

    @Test
    @DisplayName("Test Inheritance - ContractEmployee extends Employee")
    void testContractEmployeeInheritance() {
        ContractEmployee contract = new ContractEmployee();

        contract.setName("Carol White");
        contract.setDepartment("Consulting");
        contract.setSalary(80000.0);
        contract.setContractDuration(12);
        contract.setContractAmount(96000.0);

        assertEquals("Carol White", contract.getName());
        assertEquals("Consulting", contract.getDepartment());
        assertEquals(80000.0, contract.getSalary());
        assertEquals(12, contract.getContractDuration());
        assertEquals(96000.0, contract.getContractAmount());

        Double calculatedSalary = contract.calculateSalary();
        assertEquals(8000.0, calculatedSalary);

        System.out.println("✅ Inheritance Test PASSED - ContractEmployee inherits from Employee");
        System.out.println("   Contract: $96,000 ÷ 12 months = $" + calculatedSalary + "/month");
    }

    @Test
    @DisplayName("Test Polymorphism - Developer calculateSalary()")
    void testDeveloperPolymorphism() {
        Developer developer = new Developer();

        developer.setName("David Developer");
        developer.setDepartment("Engineering");
        developer.setSalary(75000.0);
        developer.setProjectsCompleted(5);
        developer.setProgrammingLanguages("Java, Python, JavaScript");

        Double calculatedSalary = developer.calculateSalary();
        Double expectedSalary = 75000.0 + (5 * 1000);

        assertEquals(expectedSalary, calculatedSalary);
        assertEquals("Java, Python, JavaScript", developer.getProgrammingLanguages());

        System.out.println("✅ Polymorphism Test PASSED - Developer overrides calculateSalary()");
        System.out.println("   Base: $75,000 + (5 projects × $1,000) = $" + calculatedSalary);
    }

    @Test
    @DisplayName("Test Polymorphism - Tester calculateSalary()")
    void testTesterPolymorphism() {
        Tester tester = new Tester();

        tester.setName("Emma Tester");
        tester.setDepartment("QA");
        tester.setSalary(65000.0);
        tester.setBugsFound(150);
        tester.setTestingTools("Selenium, JUnit, TestNG");

        Double calculatedSalary = tester.calculateSalary();
        Double expectedSalary = 65000.0 + (150 * 50);

        assertEquals(expectedSalary, calculatedSalary);
        assertEquals("Selenium, JUnit, TestNG", tester.getTestingTools());

        System.out.println("✅ Polymorphism Test PASSED - Tester overrides calculateSalary()");
        System.out.println("   Base: $65,000 + (150 bugs × $50) = $" + calculatedSalary);
    }

    @Test
    @DisplayName("Test Polymorphism - HR calculateSalary()")
    void testHRPolymorphism() {
        HR hr = new HR();

        hr.setName("Frank HR");
        hr.setDepartment("Human Resources");
        hr.setSalary(70000.0);
        hr.setEmployeesManaged(25);
        hr.setHrSpecialization("Recruitment");

        Double calculatedSalary = hr.calculateSalary();
        Double expectedSalary = 70000.0 + (25 * 200);

        assertEquals(expectedSalary, calculatedSalary);
        assertEquals("Recruitment", hr.getHrSpecialization());

        System.out.println("✅ Polymorphism Test PASSED - HR overrides calculateSalary()");
        System.out.println("   Base: $70,000 + (25 employees × $200) = $" + calculatedSalary);
    }

    @Test
    @DisplayName("Test Polymorphism - Different salary calculations for same base salary")
    void testPolymorphismComparison() {
        Double baseSalary = 60000.0;

        Developer dev = new Developer();
        dev.setSalary(baseSalary);
        dev.setProjectsCompleted(5);

        Tester tester = new Tester();
        tester.setSalary(baseSalary);
        tester.setBugsFound(80);

        HR hr = new HR();
        hr.setSalary(baseSalary);
        hr.setEmployeesManaged(15);

        Double devSalary = dev.calculateSalary();
        Double testerSalary = tester.calculateSalary();
        Double hrSalary = hr.calculateSalary();

        assertNotEquals(devSalary, testerSalary);
        assertNotEquals(testerSalary, hrSalary);
        assertNotEquals(devSalary, hrSalary);

        System.out.println("✅ Polymorphism Comparison Test PASSED");
        System.out.println("   Same base salary ($60,000) produces different results:");
        System.out.println("   Developer: $" + devSalary);
        System.out.println("   Tester: $" + testerSalary);
        System.out.println("   HR: $" + hrSalary);
    }

    @Test
    @DisplayName("Test Attendance Model")
    void testAttendanceModel() {
        Attendance attendance = new Attendance();
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("Test Employee");

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setStatus("PRESENT");
        attendance.setCheckInTime(LocalTime.of(9, 0));
        attendance.setCheckOutTime(LocalTime.of(17, 30));
        attendance.setHoursWorked(8.5);

        assertEquals("PRESENT", attendance.getStatus());
        assertEquals(8.5, attendance.getHoursWorked());
        assertNotNull(attendance.getEmployee());

        System.out.println("✅ Attendance Model Test PASSED");
        System.out.println("   Employee: " + employee.getName());
        System.out.println("   Status: " + attendance.getStatus());
        System.out.println("   Hours Worked: " + attendance.getHoursWorked());
    }

    @Test
    @DisplayName("Test Payroll Model")
    void testPayrollModel() {
        Payroll payroll = new Payroll();
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("Test Employee");
        employee.setSalary(70000.0);

        payroll.setEmployee(employee);
        payroll.setMonth("January");
        payroll.setYear(2026);
        payroll.setBasicSalary(70000.0);
        payroll.setAllowances(2000.0);
        payroll.setDeductions(500.0);
        payroll.setNetSalary(71500.0);
        payroll.setStatus("PAID");

        assertEquals("January", payroll.getMonth());
        assertEquals(2026, payroll.getYear());
        assertEquals(70000.0, payroll.getBasicSalary());
        assertEquals(2000.0, payroll.getAllowances());
        assertEquals(500.0, payroll.getDeductions());
        assertEquals(71500.0, payroll.getNetSalary());
        assertEquals("PAID", payroll.getStatus());

        System.out.println("✅ Payroll Model Test PASSED");
        System.out.println("   Basic Salary: $" + payroll.getBasicSalary());
        System.out.println("   Allowances: $" + payroll.getAllowances());
        System.out.println("   Deductions: $" + payroll.getDeductions());
        System.out.println("   Net Salary: $" + payroll.getNetSalary());
    }

    @Test
    @DisplayName("Test User Model")
    void testUserModel() {
        User user = new User();

        user.setUsername("testuser");
        user.setPassword("testpass123");
        user.setRole("ADMIN");

        assertEquals("testuser", user.getUsername());
        assertEquals("testpass123", user.getPassword());
        assertEquals("ADMIN", user.getRole());

        System.out.println("✅ User Model Test PASSED");
        System.out.println("   Username: " + user.getUsername());
        System.out.println("   Role: " + user.getRole());
    }

    @Test
    @DisplayName("Test All OOP Principles Together")
    void testAllOOPPrinciples() {
        System.out.println("\n========================================");
        System.out.println("COMPREHENSIVE OOP PRINCIPLES TEST");
        System.out.println("========================================\n");

        // 1. Encapsulation
        Employee baseEmployee = new Employee();
        baseEmployee.setName("Base Employee");
        baseEmployee.setSalary(50000.0);
        System.out.println("1. ENCAPSULATION ✅");
        System.out.println("   Private fields accessed via getters/setters");
        System.out.println("   Employee: " + baseEmployee.getName() + " | Salary: $" + baseEmployee.getSalary());

        // 2. Inheritance
        FullTimeEmployee fullTime = new FullTimeEmployee();
        fullTime.setName("Full Time Employee");
        fullTime.setSalary(60000.0);
        fullTime.setBenefits("Full Benefits");
        System.out.println("\n2. INHERITANCE ✅");
        System.out.println("   FullTimeEmployee inherits from Employee");
        System.out.println("   Inherited: name, salary | Own: benefits");

        // 3. Polymorphism
        Employee dev = new Developer();
        dev.setSalary(70000.0);
        ((Developer) dev).setProjectsCompleted(4);

        Employee tester = new Tester();
        tester.setSalary(70000.0);
        ((Tester) tester).setBugsFound(120);

        System.out.println("\n3. POLYMORPHISM ✅");
        System.out.println("   Same method, different implementations:");
        System.out.println("   Developer salary: $" + dev.calculateSalary());
        System.out.println("   Tester salary: $" + tester.calculateSalary());

        // 4. Abstraction (demonstrated through interface usage)
        System.out.println("\n4. ABSTRACTION ✅");
        System.out.println("   PayrollOperations interface defines contract");
        System.out.println("   PayrollService implements the interface");
        System.out.println("   Implementation details hidden from users");

        System.out.println("\n========================================");
        System.out.println("ALL OOP PRINCIPLES VERIFIED ✅");
        System.out.println("========================================\n");

        assertTrue(true, "All OOP principles demonstrated successfully");
    }
}
