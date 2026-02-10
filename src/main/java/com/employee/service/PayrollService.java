package com.employee.service;

import com.employee.exception.InvalidInputException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.interfaces.PayrollOperations;
import com.employee.model.Employee;
import com.employee.model.Payroll;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollService implements PayrollOperations {

    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;

    @Override
    public Double calculateSalary(Long employeeId) {
        if (employeeId == null) {
            throw new InvalidInputException("Employee ID cannot be null");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", employeeId));
        return employee.calculateSalary();
    }

    @Override
    public Payroll generatePayrollReport(Long employeeId, String month, Integer year) {
        if (employeeId == null) {
            throw new InvalidInputException("Employee ID cannot be null");
        }
        if (month == null || month.trim().isEmpty()) {
            throw new InvalidInputException("Month cannot be null or empty");
        }
        if (year == null) {
            throw new InvalidInputException("Year cannot be null");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", employeeId));

        Double basicSalary = employee.calculateSalary();
        Double allowances = 2000.0;
        Double deductions = 500.0;
        Double netSalary = basicSalary + allowances - deductions;

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setMonth(month);
        payroll.setYear(year);
        payroll.setBasicSalary(basicSalary);
        payroll.setAllowances(allowances);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);
        payroll.setPaymentDate(LocalDate.now());
        payroll.setStatus("PROCESSED");

        return payrollRepository.save(payroll);
    }

    @Override
    public Double handleDeductions(Double salary, Double deductions) {
        if (salary == null) {
            throw new InvalidInputException("Salary cannot be null");
        }
        if (deductions == null) {
            throw new InvalidInputException("Deductions cannot be null");
        }
        return salary - deductions;
    }

    @Override
    public Double handleAllowances(Double salary, Double allowances) {
        if (salary == null) {
            throw new InvalidInputException("Salary cannot be null");
        }
        if (allowances == null) {
            throw new InvalidInputException("Allowances cannot be null");
        }
        return salary + allowances;
    }

    public List<Payroll> getEmployeePayrollHistory(Long employeeId) {
        if (employeeId == null) {
            throw new InvalidInputException("Employee ID cannot be null");
        }

        return payrollRepository.findByEmployeeEmployeeId(employeeId);
    }

    public List<Payroll> getPayrollByMonth(String month, Integer year) {
        return payrollRepository.findByMonthAndYear(month, year);
    }
}
