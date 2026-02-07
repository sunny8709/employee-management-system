package com.employee.service;

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
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.calculateSalary();
    }

    @Override
    public Payroll generatePayrollReport(Long employeeId, String month, Integer year) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

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
        return salary - deductions;
    }

    @Override
    public Double handleAllowances(Double salary, Double allowances) {
        return salary + allowances;
    }

    public List<Payroll> getEmployeePayrollHistory(Long employeeId) {
        return payrollRepository.findByEmployeeEmployeeId(employeeId);
    }

    public List<Payroll> getPayrollByMonth(String month, Integer year) {
        return payrollRepository.findByMonthAndYear(month, year);
    }
}
