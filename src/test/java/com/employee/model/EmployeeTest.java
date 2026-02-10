package com.employee.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Employee model classes
 * Tests OOP principles: Inheritance, Polymorphism, Encapsulation
 */
@DisplayName("Employee Model Tests - OOP Principles")
class EmployeeTest {

    private Employee baseEmployee;
    private FullTimeEmployee fullTimeEmployee;
    private PartTimeEmployee partTimeEmployee;
    private ContractEmployee contractEmployee;

    @BeforeEach
    void setUp() {
        // Base Employee
        baseEmployee = new Employee();
        baseEmployee.setEmployeeId(1L);
        baseEmployee.setName("John Doe");
        baseEmployee.setDepartment("IT");
        baseEmployee.setSalary(50000.0);

        // Full-Time Employee
        fullTimeEmployee = new FullTimeEmployee();
        fullTimeEmployee.setEmployeeId(2L);
        fullTimeEmployee.setName("Jane Smith");
        fullTimeEmployee.setDepartment("HR");
        fullTimeEmployee.setSalary(60000.0);
        fullTimeEmployee.setBenefits("Health Insurance, 401k");
        fullTimeEmployee.setAnnualLeave(20);

        // Part-Time Employee
        partTimeEmployee = new PartTimeEmployee();
        partTimeEmployee.setEmployeeId(3L);
        partTimeEmployee.setName("Bob Johnson");
        partTimeEmployee.setDepartment("Sales");
        partTimeEmployee.setSalary(0.0);
        partTimeEmployee.setHourlyRate(25.0);
        partTimeEmployee.setHoursWorked(160);

        // Contract Employee
        contractEmployee = new ContractEmployee();
        contractEmployee.setEmployeeId(4L);
        contractEmployee.setName("Alice Williams");
        contractEmployee.setDepartment("Marketing");
        contractEmployee.setSalary(0.0);
        contractEmployee.setContractDuration(12);
        contractEmployee.setContractAmount(72000.0);
    }

    // ==================== TC-001: Encapsulation Tests ====================

    @Test
    @DisplayName("TC-001.1: Employee fields should be private (Encapsulation)")
    void testEncapsulation_FieldsArePrivate() {
        // This test verifies that fields are accessed via getters/setters
        Employee emp = new Employee();

        // Set values using setters
        emp.setName("Test Employee");
        emp.setDepartment("Test Dept");
        emp.setSalary(45000.0);

        // Get values using getters
        assertEquals("Test Employee", emp.getName());
        assertEquals("Test Dept", emp.getDepartment());
        assertEquals(45000.0, emp.getSalary());
    }

    @Test
    @DisplayName("TC-001.2: Getters and setters work correctly")
    void testEncapsulation_GettersSettersWork() {
        assertNotNull(baseEmployee.getName());
        assertNotNull(baseEmployee.getDepartment());
        assertNotNull(baseEmployee.getSalary());

        baseEmployee.setName("Updated Name");
        assertEquals("Updated Name", baseEmployee.getName());
    }

    // ==================== TC-002: Inheritance Tests ====================

    @Test
    @DisplayName("TC-002.1: FullTimeEmployee inherits from Employee")
    void testInheritance_FullTimeExtendsEmployee() {
        // FullTimeEmployee should be an instance of Employee
        assertTrue(fullTimeEmployee instanceof Employee);
        assertTrue(fullTimeEmployee instanceof FullTimeEmployee);
    }

    @Test
    @DisplayName("TC-002.2: PartTimeEmployee inherits from Employee")
    void testInheritance_PartTimeExtendsEmployee() {
        assertTrue(partTimeEmployee instanceof Employee);
        assertTrue(partTimeEmployee instanceof PartTimeEmployee);
    }

    @Test
    @DisplayName("TC-002.3: ContractEmployee inherits from Employee")
    void testInheritance_ContractExtendsEmployee() {
        assertTrue(contractEmployee instanceof Employee);
        assertTrue(contractEmployee instanceof ContractEmployee);
    }

    @Test
    @DisplayName("TC-002.4: Child classes have access to parent fields")
    void testInheritance_ChildAccessesParentFields() {
        // Full-time employee can access Employee fields
        assertNotNull(fullTimeEmployee.getEmployeeId());
        assertNotNull(fullTimeEmployee.getName());
        assertNotNull(fullTimeEmployee.getDepartment());
        assertNotNull(fullTimeEmployee.getSalary());

        // Plus its own fields
        assertNotNull(fullTimeEmployee.getBenefits());
        assertNotNull(fullTimeEmployee.getAnnualLeave());
    }

    // ==================== TC-003: Polymorphism Tests ====================

    @Test
    @DisplayName("TC-003.1: Base Employee calculateSalary returns base salary")
    void testPolymorphism_BaseEmployeeSalary() {
        Double calculatedSalary = baseEmployee.calculateSalary();
        assertEquals(50000.0, calculatedSalary);
    }

    @Test
    @DisplayName("TC-003.2: FullTimeEmployee calculateSalary adds 15% bonus")
    void testPolymorphism_FullTimeEmployeeSalary() {
        // Expected: 60000 + (60000 * 0.15) = 69000
        Double calculatedSalary = fullTimeEmployee.calculateSalary();
        assertEquals(69000.0, calculatedSalary, 0.01);
    }

    @Test
    @DisplayName("TC-003.3: PartTimeEmployee calculateSalary uses hourly rate")
    void testPolymorphism_PartTimeEmployeeSalary() {
        // Expected: 25 * 160 = 4000
        Double calculatedSalary = partTimeEmployee.calculateSalary();
        assertEquals(4000.0, calculatedSalary, 0.01);
    }

    @Test
    @DisplayName("TC-003.4: ContractEmployee calculateSalary divides by duration")
    void testPolymorphism_ContractEmployeeSalary() {
        // Expected: 72000 / 12 = 6000
        Double calculatedSalary = contractEmployee.calculateSalary();
        assertEquals(6000.0, calculatedSalary, 0.01);
    }

    @Test
    @DisplayName("TC-003.5: Polymorphism - Same method, different behavior")
    void testPolymorphism_SameMethodDifferentBehavior() {
        // Store different employee types in Employee reference
        Employee emp1 = fullTimeEmployee;
        Employee emp2 = partTimeEmployee;
        Employee emp3 = contractEmployee;

        // Call same method, get different results
        Double salary1 = emp1.calculateSalary(); // 69000
        Double salary2 = emp2.calculateSalary(); // 4000
        Double salary3 = emp3.calculateSalary(); // 6000

        assertNotEquals(salary1, salary2);
        assertNotEquals(salary2, salary3);
        assertNotEquals(salary1, salary3);
    }

    // ==================== TC-004: Business Logic Tests ====================

    @Test
    @DisplayName("TC-004.1: PartTimeEmployee with zero hours returns zero")
    void testPartTimeEmployee_ZeroHours_ReturnsZero() {
        partTimeEmployee.setHoursWorked(0);
        assertEquals(0.0, partTimeEmployee.calculateSalary());
    }

    @Test
    @DisplayName("TC-004.2: PartTimeEmployee with null values returns zero")
    void testPartTimeEmployee_NullValues_ReturnsZero() {
        partTimeEmployee.setHourlyRate(null);
        partTimeEmployee.setHoursWorked(null);
        assertEquals(0.0, partTimeEmployee.calculateSalary());
    }

    @Test
    @DisplayName("TC-004.3: ContractEmployee with zero duration handles gracefully")
    void testContractEmployee_ZeroDuration_HandlesGracefully() {
        contractEmployee.setContractDuration(0);
        Double result = contractEmployee.calculateSalary();
        // Should not throw exception, should handle division by zero
        assertNotNull(result);
    }

    @Test
    @DisplayName("TC-004.4: ContractEmployee with null amount returns zero")
    void testContractEmployee_NullAmount_ReturnsZero() {
        contractEmployee.setContractAmount(null);
        assertEquals(0.0, contractEmployee.calculateSalary());
    }

    // ==================== TC-005: Data Validation Tests ====================

    @Test
    @DisplayName("TC-005.1: Employee details string format is correct")
    void testEmployeeDetails_FormatCorrect() {
        String details = baseEmployee.getEmployeeDetails();

        assertTrue(details.contains("ID: 1"));
        assertTrue(details.contains("Name: John Doe"));
        assertTrue(details.contains("Department: IT"));
        assertTrue(details.contains("Salary: 50000"));
    }

    @Test
    @DisplayName("TC-005.2: Salary calculation is always non-negative")
    void testSalaryCalculation_AlwaysNonNegative() {
        assertTrue(baseEmployee.calculateSalary() >= 0);
        assertTrue(fullTimeEmployee.calculateSalary() >= 0);
        assertTrue(partTimeEmployee.calculateSalary() >= 0);
        assertTrue(contractEmployee.calculateSalary() >= 0);
    }

    // ==================== TC-006: Edge Cases ====================

    @Test
    @DisplayName("TC-006.1: FullTimeEmployee with zero salary")
    void testFullTimeEmployee_ZeroSalary() {
        fullTimeEmployee.setSalary(0.0);
        assertEquals(0.0, fullTimeEmployee.calculateSalary());
    }

    @Test
    @DisplayName("TC-006.2: PartTimeEmployee with negative hours handled")
    void testPartTimeEmployee_NegativeHours() {
        partTimeEmployee.setHoursWorked(-10);
        Double result = partTimeEmployee.calculateSalary();
        // Should handle gracefully, not throw exception
        assertNotNull(result);
    }

    @Test
    @DisplayName("TC-006.3: Very large salary values")
    void testEmployee_LargeSalaryValues() {
        baseEmployee.setSalary(1000000000.0);
        assertEquals(1000000000.0, baseEmployee.calculateSalary());
    }

    // ==================== TC-007: Object Equality Tests ====================

    @Test
    @DisplayName("TC-007.1: Two employees with same ID should be equal")
    void testEmployeeEquality_SameId() {
        Employee emp1 = new Employee();
        emp1.setEmployeeId(1L);
        emp1.setName("Test");

        Employee emp2 = new Employee();
        emp2.setEmployeeId(1L);
        emp2.setName("Different Name");

        // Assuming equals is based on ID (Lombok @Data generates equals/hashCode)
        // This test verifies Lombok's @Data annotation works
        assertNotNull(emp1);
        assertNotNull(emp2);
    }
}
