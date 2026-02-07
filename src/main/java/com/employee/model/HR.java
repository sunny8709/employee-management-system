package com.employee.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("HR")
public class HR extends Employee {

    private Integer employeesManaged;
    private String hrSpecialization;

    @Override
    public Double calculateSalary() {
        Double baseSalary = getSalary();
        Double bonus = employeesManaged != null ? employeesManaged * 200.0 : 0.0;
        return baseSalary + bonus;
    }
}
