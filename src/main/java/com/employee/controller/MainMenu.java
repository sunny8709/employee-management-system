package com.employee.controller;

import com.employee.model.Employee;
import com.employee.service.AttendanceService;
import com.employee.service.EmployeeService;
import com.employee.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MainMenu {

    private static final Logger logger = LoggerFactory.getLogger(MainMenu.class);

    private final EmployeeService employeeService;
    private final PayrollService payrollService;
    private final AttendanceService attendanceService;
    private Scanner scanner;

    public void displayMenu() {
        // Initialize scanner for this session
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        try {
            while (true) {
                System.out.println("\n=== Employee Management System ===");
                System.out.println("1. Employee Management");
                System.out.println("2. Attendance Tracking");
                System.out.println("3. Payroll Processing");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> employeeOperations();
                    case 2 -> attendanceOperations();
                    case 3 -> payrollOperations();
                    case 4 -> {
                        System.out.println("Exiting system...");
                        return;
                    }
                    default -> logger.warn("Invalid option. Please try again.");
                }
            }
        } finally {
            // Close scanner when exiting
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private void employeeOperations() {
        System.out.println("\n=== Employee Operations ===");
        System.out.println("1. Add Employee");
        System.out.println("2. View Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. View All Employees");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addEmployee();
            case 2 -> viewEmployee();
            case 3 -> updateEmployee();
            case 4 -> deleteEmployee();
            case 5 -> viewAllEmployees();
            default -> logger.warn("Invalid option");
        }
    }

    private void addEmployee() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        System.out.print("Enter salary: ");
        Double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee employee = new Employee();
        employee.setName(name);
        employee.setDepartment(department);
        employee.setSalary(salary);

        Employee saved = employeeService.addEmployee(employee);
        logger.info("Employee added successfully with ID: {}", saved.getEmployeeId());
    }

    private void viewEmployee() {
        System.out.print("Enter employee ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            Employee employee = employeeService.viewEmployeeDetails(id);
            logger.info(employee.getEmployeeDetails());
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    private void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new department: ");
        String department = scanner.nextLine();
        System.out.print("Enter new salary: ");
        Double salary = scanner.nextDouble();
        scanner.nextLine();

        Employee updated = new Employee();
        updated.setName(name);
        updated.setDepartment(department);
        updated.setSalary(salary);

        try {
            employeeService.updateEmployee(id, updated);
            logger.info("Employee updated successfully");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    private void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            employeeService.deleteEmployee(id);
            logger.info("Employee deleted successfully");
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
    }

    private void viewAllEmployees() {
        employeeService.viewAllEmployees().forEach(emp -> logger.info(emp.getEmployeeDetails()));
    }

    private void attendanceOperations() {
        System.out.println("\n=== Attendance Operations ===");
        System.out.println("1. Mark Attendance");
        System.out.println("2. View Attendance Report");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter employee ID: ");
            Long empId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter status (PRESENT/ABSENT): ");
            String status = scanner.nextLine();

            attendanceService.trackAttendance(empId, LocalDate.now(), status);
            logger.info("Attendance marked successfully");
        } else if (choice == 2) {
            System.out.print("Enter employee ID: ");
            Long empId = scanner.nextLong();
            scanner.nextLine();

            attendanceService.getEmployeeAttendanceLogs(empId).forEach(
                    att -> logger.info("Date: {}, Status: {}", att.getAttendanceDate(), att.getStatus()));
        }
    }

    private void payrollOperations() {
        System.out.println("\n=== Payroll Operations ===");
        System.out.println("1. Generate Payroll");
        System.out.println("2. View Payroll History");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter employee ID: ");
            Long empId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Enter month: ");
            String month = scanner.nextLine();
            System.out.print("Enter year: ");
            Integer year = scanner.nextInt();
            scanner.nextLine();

            payrollService.generatePayrollReport(empId, month, year);
            logger.info("Payroll generated successfully");
        } else if (choice == 2) {
            System.out.print("Enter employee ID: ");
            Long empId = scanner.nextLong();
            scanner.nextLine();

            payrollService.getEmployeePayrollHistory(empId).forEach(
                    pay -> logger.info("Month: {}, Net Salary: {}", pay.getMonth(), pay.getNetSalary()));
        }
    }
}
