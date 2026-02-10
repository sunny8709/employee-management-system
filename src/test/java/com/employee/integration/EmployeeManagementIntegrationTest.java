package com.employee.integration;

import com.employee.model.*;
import com.employee.repository.*;
import com.employee.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the entire Employee Management System
 * Tests end-to-end workflows and module interactions
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Integration Tests - End-to-End Workflows")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeManagementIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Long testEmployeeId;

    @BeforeEach
    void setUp() {
        // Create test user
        User testUser = new User();
        testUser.setUsername("testadmin");
        testUser.setPassword("test123");
        testUser.setRole("ADMIN");
        userRepository.save(testUser);
    }

    // ==================== TC-INT-001: Authentication Flow ====================

    @Test
    @Order(1)
    @DisplayName("INT-001: Complete authentication flow")
    void testAuthenticationFlow() {
        // Test 1: Valid login
        boolean loginSuccess = loginService.login("testadmin", "test123");
        assertTrue(loginSuccess, "Login should succeed with valid credentials");

        // Test 2: Invalid login
        boolean loginFail = loginService.login("testadmin", "wrongpassword");
        assertFalse(loginFail, "Login should fail with invalid credentials");

        // Test 3: Authorization check
        boolean isAuthorized = authService.verifyUserAuthorization("testadmin", "ADMIN");
        assertTrue(isAuthorized, "User should be authorized for ADMIN role");
    }

    // ==================== TC-INT-002: Employee CRUD Flow ====================

    @Test
    @Order(2)
    @DisplayName("INT-002: Complete employee CRUD workflow")
    void testEmployeeCRUDFlow() {
        // 1. CREATE - Add Full-Time Employee
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName("Integration Test Employee");
        employee.setDepartment("QA");
        employee.setSalary(55000.0);
        employee.setBenefits("Health Insurance");
        employee.setAnnualLeave(25);

        Employee savedEmployee = employeeService.addEmployee(employee);
        assertNotNull(savedEmployee.getEmployeeId(), "Employee should have ID after save");
        testEmployeeId = savedEmployee.getEmployeeId();

        // 2. READ - View Employee
        Employee retrievedEmployee = employeeService.viewEmployeeDetails(testEmployeeId);
        assertNotNull(retrievedEmployee, "Should retrieve saved employee");
        assertEquals("Integration Test Employee", retrievedEmployee.getName());
        assertEquals("QA", retrievedEmployee.getDepartment());

        // 3. UPDATE - Modify Employee
        retrievedEmployee.setSalary(60000.0);
        Employee updatedEmployee = employeeService.updateEmployee(testEmployeeId, retrievedEmployee);
        assertEquals(60000.0, updatedEmployee.getSalary());

        // 4. DELETE - Remove Employee
        employeeService.deleteEmployee(testEmployeeId);
        assertThrows(RuntimeException.class, () -> {
            employeeService.viewEmployeeDetails(testEmployeeId);
        }, "Should throw exception for deleted employee");
    }

    // ==================== TC-INT-003: Attendance Workflow ====================

    @Test
    @Order(3)
    @DisplayName("INT-003: Complete attendance tracking workflow")
    void testAttendanceWorkflow() {
        // Setup: Create employee
        Employee employee = new Employee();
        employee.setName("Attendance Test Employee");
        employee.setDepartment("HR");
        employee.setSalary(45000.0);
        Employee savedEmployee = employeeService.addEmployee(employee);
        Long empId = savedEmployee.getEmployeeId();

        // 1. Mark attendance as PRESENT
        Attendance attendance1 = attendanceService.trackAttendance(
                empId,
                LocalDate.now(),
                "PRESENT");
        assertNotNull(attendance1, "Attendance should be recorded");
        assertEquals("PRESENT", attendance1.getStatus());

        // 2. Mark attendance as ABSENT (different date)
        Attendance attendance2 = attendanceService.trackAttendance(
                empId,
                LocalDate.now().minusDays(1),
                "ABSENT");
        assertEquals("ABSENT", attendance2.getStatus());

        // 3. View attendance report
        var attendanceList = attendanceService.getEmployeeAttendanceLogs(empId);
        assertNotNull(attendanceList, "Should retrieve attendance records");
        assertTrue(attendanceList.size() >= 2, "Should have at least 2 attendance records");

        // Cleanup
        employeeService.deleteEmployee(empId);
    }

    // ==================== TC-INT-004: Payroll Generation Workflow
    // ====================

    @Test
    @Order(4)
    @DisplayName("INT-004: Complete payroll generation workflow")
    void testPayrollWorkflow() {
        // Setup: Create Full-Time Employee
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName("Payroll Test Employee");
        employee.setDepartment("Finance");
        employee.setSalary(50000.0);
        employee.setBenefits("401k");
        employee.setAnnualLeave(20);

        Employee savedEmployee = employeeService.addEmployee(employee);
        Long empId = savedEmployee.getEmployeeId();

        // 1. Generate payroll
        Payroll payroll = payrollService.generatePayrollReport(empId, "February", 2026);

        assertNotNull(payroll, "Payroll should be generated");
        assertNotNull(payroll.getPayrollId(), "Payroll should have ID");

        // 2. Verify salary calculation (polymorphism)
        // Full-time: 50000 + (50000 * 0.15) = 57500
        assertEquals(57500.0, payroll.getBasicSalary(), 0.01);

        // 3. Verify net salary calculation
        // Net = Basic + Allowances - Deductions
        // Net = 57500 + 2000 - 500 = 59000
        assertEquals(59000.0, payroll.getNetSalary(), 0.01);

        // 4. View payroll history
        var payrollHistory = payrollService.getEmployeePayrollHistory(empId);
        assertNotNull(payrollHistory, "Should retrieve payroll history");
        assertTrue(payrollHistory.size() >= 1, "Should have at least 1 payroll record");

        // Cleanup
        employeeService.deleteEmployee(empId);
    }

    // ==================== TC-INT-005: Polymorphism Across All Employee Types
    // ====================

    @Test
    @Order(5)
    @DisplayName("INT-005: Polymorphism - Different employee types, different salaries")
    void testPolymorphismAcrossEmployeeTypes() {
        // Create Full-Time Employee
        FullTimeEmployee fullTime = new FullTimeEmployee();
        fullTime.setName("Full Time Test");
        fullTime.setDepartment("IT");
        fullTime.setSalary(50000.0);
        Employee ft = employeeService.addEmployee(fullTime);

        // Create Part-Time Employee
        PartTimeEmployee partTime = new PartTimeEmployee();
        partTime.setName("Part Time Test");
        partTime.setDepartment("Sales");
        partTime.setHourlyRate(25.0);
        partTime.setHoursWorked(160);
        Employee pt = employeeService.addEmployee(partTime);

        // Create Contract Employee
        ContractEmployee contract = new ContractEmployee();
        contract.setName("Contract Test");
        contract.setDepartment("Marketing");
        contract.setContractDuration(12);
        contract.setContractAmount(72000.0);
        Employee ct = employeeService.addEmployee(contract);

        // Calculate salaries using polymorphism
        Double ftSalary = payrollService.calculateSalary(ft.getEmployeeId());
        Double ptSalary = payrollService.calculateSalary(pt.getEmployeeId());
        Double ctSalary = payrollService.calculateSalary(ct.getEmployeeId());

        // Verify different calculations
        assertEquals(57500.0, ftSalary, 0.01, "Full-time: 50000 + 15%");
        assertEquals(4000.0, ptSalary, 0.01, "Part-time: 25 * 160");
        assertEquals(6000.0, ctSalary, 0.01, "Contract: 72000 / 12");

        // All should be different
        assertNotEquals(ftSalary, ptSalary);
        assertNotEquals(ptSalary, ctSalary);
        assertNotEquals(ftSalary, ctSalary);

        // Cleanup
        employeeService.deleteEmployee(ft.getEmployeeId());
        employeeService.deleteEmployee(pt.getEmployeeId());
        employeeService.deleteEmployee(ct.getEmployeeId());
    }

    // ==================== TC-INT-006: Complete User Journey ====================

    @Test
    @Order(6)
    @DisplayName("INT-006: Complete user journey from login to payroll")
    void testCompleteUserJourney() {
        // Step 1: Login
        boolean loggedIn = loginService.login("testadmin", "test123");
        assertTrue(loggedIn, "User should login successfully");

        // Step 2: Add Employee
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName("Journey Test Employee");
        employee.setDepartment("Operations");
        employee.setSalary(55000.0);
        employee.setBenefits("Full Benefits");
        employee.setAnnualLeave(30);

        Employee savedEmp = employeeService.addEmployee(employee);
        assertNotNull(savedEmp.getEmployeeId());

        // Step 3: Mark Attendance
        Attendance attendance = attendanceService.trackAttendance(
                savedEmp.getEmployeeId(),
                LocalDate.now(),
                "PRESENT");
        assertNotNull(attendance);

        // Step 4: Generate Payroll
        Payroll payroll = payrollService.generatePayrollReport(
                savedEmp.getEmployeeId(),
                "February",
                2026);
        assertNotNull(payroll);

        // Verify complete workflow
        assertEquals(63250.0, payroll.getBasicSalary(), 0.01); // 55000 + 15%
        assertEquals(64750.0, payroll.getNetSalary(), 0.01); // 63250 + 2000 - 500

        // Step 5: Cleanup
        employeeService.deleteEmployee(savedEmp.getEmployeeId());
    }
}
