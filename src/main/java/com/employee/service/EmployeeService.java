package com.employee.service;

import com.employee.exception.InvalidInputException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee viewEmployeeDetails(Long employeeId) {
        if (employeeId == null) {
            throw new InvalidInputException("Employee ID cannot be null");
        }

        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", employeeId));
    }

    public List<Employee> viewAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long employeeId, Employee updatedEmployee) {
        if (employeeId == null) {
            throw new InvalidInputException("Employee ID cannot be null");
        }
        if (updatedEmployee == null) {
            throw new InvalidInputException("Updated employee data cannot be null");
        }

        Employee existing = viewEmployeeDetails(employeeId);

        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setRoleType(updatedEmployee.getRoleType());

        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new InvalidInputException("Employee ID cannot be null");
        }

        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee", "ID", employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> findByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new InvalidInputException("Department cannot be null or empty");
        }

        return employeeRepository.findByDepartment(department);
    }
}
