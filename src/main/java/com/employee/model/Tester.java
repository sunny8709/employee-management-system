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
@DiscriminatorValue("TESTER")
public class Tester extends Employee {

    private Integer bugsFound;
    private String testingTools;

    @Override
    public Double calculateSalary() {
        Double baseSalary = getSalary();
        Double bonus = bugsFound != null ? bugsFound * 50.0 : 0.0;
        return baseSalary + bonus;
    }
}
