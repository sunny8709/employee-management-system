package com.employee.interfaces;

import com.employee.model.Payroll;

public interface PayrollOperations {

    Double calculateSalary(Long employeeId);

    Payroll generatePayrollReport(Long employeeId, String month, Integer year);

    Double handleDeductions(Double salary, Double deductions);

    Double handleAllowances(Double salary, Double allowances);
}
