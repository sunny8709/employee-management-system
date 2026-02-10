package com.employee.service;

import com.employee.model.*;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.PayrollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PayrollService
 * Tests salary calculation polymorphism and payroll generation
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PayrollService Tests")
class PayrollServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PayrollRepository payrollRepository;

    @InjectMocks
    private PayrollService payrollService;

    private FullTimeEmployee fullTimeEmployee;
    private PartTimeEmployee partTimeEmployee;
    private ContractEmployee contractEmployee;

    @BeforeEach
    void setUp() {
        // Full-Time Employee
        fullTimeEmployee = new FullTimeEmployee();
        fullTimeEmployee.setEmployeeId(1L);
        fullTimeEmployee.setName("John Doe");
        fullTimeEmployee.setDepartment("IT");
        fullTimeEmployee.setSalary(50000.0);
        fullTimeEmployee.setBenefits("Health");
        fullTimeEmployee.setAnnualLeave(20);

        // Part-Time Employee
        partTimeEmployee = new PartTimeEmployee();
        partTimeEmployee.setEmployeeId(2L);
        partTimeEmployee.setName("Jane Smith");
        partTimeEmployee.setDepartment("Sales");
        partTimeEmployee.setHourlyRate(25.0);
        partTimeEmployee.setHoursWorked(160);

        // Contract Employee
        contractEmployee = new ContractEmployee();
        contractEmployee.setEmployeeId(3L);
        contractEmployee.setName("Bob Johnson");
        contractEmployee.setDepartment("Marketing");
        contractEmployee.setContractDuration(12);
        contractEmployee.setContractAmount(72000.0);
    }

    // ==================== TC-001: Salary Calculation Tests ====================

    @Test
    @DisplayName("TC-001.1: Calculate salary for Full-Time Employee")
    void testCalculateSalary_FullTimeEmployee() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(fullTimeEmployee));

        // Act
        Double salary = payrollService.calculateSalary(1L);

        // Assert
        // Expected: 50000 + (50000 * 0.15) = 57500
        assertEquals(57500.0, salary, 0.01);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TC-001.2: Calculate salary for Part-Time Employee")
    void testCalculateSalary_PartTimeEmployee() {
        // Arrange
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(partTimeEmployee));

        // Act
        Double salary = payrollService.calculateSalary(2L);

        // Assert
        // Expected: 25 * 160 = 4000
        assertEquals(4000.0, salary, 0.01);
    }

    @Test
    @DisplayName("TC-001.3: Calculate salary for Contract Employee")
    void testCalculateSalary_ContractEmployee() {
        // Arrange
        when(employeeRepository.findById(3L)).thenReturn(Optional.of(contractEmployee));

        // Act
        Double salary = payrollService.calculateSalary(3L);

        // Assert
        // Expected: 72000 / 12 = 6000
        assertEquals(6000.0, salary, 0.01);
    }

    @Test
    @DisplayName("TC-001.4: Calculate salary for non-existent employee throws exception")
    void testCalculateSalary_EmployeeNotFound_ThrowsException() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            payrollService.calculateSalary(999L);
        });
    }

    // ==================== TC-002: Payroll Generation Tests ====================

    @Test
    @DisplayName("TC-002.1: Generate payroll for Full-Time Employee")
    void testGeneratePayrollReport_FullTimeEmployee() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(fullTimeEmployee));
        
        Payroll expectedPayroll = new Payroll();
        expectedPayroll.setPayrollId(1L);
        expectedPayroll.setEmployee(fullTimeEmployee);
        expectedPayroll.setBasicSalary(57500.0);
        expectedPayroll.setAllowances(2000.0);
        expectedPayroll.setDeductions(500.0);
        expectedPayroll.setNetSalary(59000.0);
        
        when(payrollRepository.save(any(Payroll.class))).thenReturn(expectedPayroll);

        // Act
        Payroll payroll = payrollService.generatePayrollReport(1L, "February", 2026);

        // Assert
        assertNotNull(payroll);
        assertEquals(57500.0, payroll.getBasicSalary(), 0.01);
        assertEquals(2000.0, payroll.getAllowances(), 0.01);
        assertEquals(500.0, payroll.getDeductions(), 0.01);
        assertEquals(59000.0, payroll.getNetSalary(), 0.01);
        
        verify(payrollRepository, times(1)).save(any(Payroll.class));
    }

    @Test
    @DisplayName("TC-002.2: Generate payroll for Part-Time Employee")
    void testGeneratePayrollReport_PartTimeEmployee() {
        // Arrange
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(partTimeEmployee));
        
        Payroll expectedPayroll = new Payroll();
        expectedPayroll.setBasicSalary(4000.0);
        expectedPayroll.setNetSalary(5500.0); // 4000 + 2000 - 500
        
        when(payrollRepository.save(any(Payroll.class))).thenReturn(expectedPayroll);

        // Act
        Payroll payroll = payrollService.generatePayrollReport(2L, "February", 2026);

        // Assert
        assertNotNull(payroll);
        assertEquals(4000.0, payroll.getBasicSalary(), 0.01);
        assertEquals(5500.0, payroll.getNetSalary(), 0.01);
    }

    @Test
    @DisplayName("TC-002.3: Generate payroll for Contract Employee")
    void testGeneratePayrollReport_ContractEmployee() {
        // Arrange
        when(employeeRepository.findById(3L)).thenReturn(Optional.of(contractEmployee));
        
        Payroll expectedPayroll = new Payroll();
        expectedPayroll.setBasicSalary(6000.0);
        expectedPayroll.setNetSalary(7500.0); // 6000 + 2000 - 500
        
        when(payrollRepository.save(any(Payroll.class))).thenReturn(expectedPayroll);

        // Act
        Payroll payroll = payrollService.generatePayrollReport(3L, "February", 2026);

        // Assert
        assertNotNull(payroll);
        assertEquals(6000.0, payroll.getBasicSalary(), 0.01);
        assertEquals(7500.0, payroll.getNetSalary(), 0.01);
    }

    // ==================== TC-003: Polymorphism Verification ====================

    @Test
    @DisplayName("TC-003.1: Polymorphism - Different employees, different calculations")
    void testPolymorphism_DifferentEmployeeTypes() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(fullTimeEmployee));
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(partTimeEmployee));
        when(employeeRepository.findById(3L)).thenReturn(Optional.of(contractEmployee));

        // Act
        Double salary1 = payrollService.calculateSalary(1L); // Full-time
        Double salary2 = payrollService.calculateSalary(2L); // Part-time
        Double salary3 = payrollService.calculateSalary(3L); // Contract

        // Assert - All different calculations
        assertNotEquals(salary1, salary2);
        assertNotEquals(salary2, salary3);
        assertNotEquals(salary1, salary3);
        
        // Verify correct values
        assertEquals(57500.0, salary1, 0.01); // Full-time with 15% bonus
        assertEquals(4000.0, salary2, 0.01);  // Part-time hourly
        assertEquals(6000.0, salary3, 0.01);  // Contract monthly
    }

    // ==================== TC-004: Edge Cases ====================

    @Test
    @DisplayName("TC-004.1: Null employee ID throws exception")
    void testCalculateSalary_NullEmployeeId_ThrowsException() {
        assertThrows(Exception.class, () -> {
            payrollService.calculateSalary(null);
        });
    }

    @Test
    @DisplayName("TC-004.2: Negative employee ID throws exception")
    void testCalculateSalary_NegativeEmployeeId_ThrowsException() {
        when(employeeRepository.findById(-1L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            payrollService.calculateSalary(-1L);
        });
    }
}
