package com.employee.repository;

import com.employee.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeEmployeeId(Long employeeId);

    Optional<Payroll> findByEmployeeEmployeeIdAndMonthAndYear(Long employeeId, String month, Integer year);

    List<Payroll> findByMonthAndYear(String month, Integer year);
}
