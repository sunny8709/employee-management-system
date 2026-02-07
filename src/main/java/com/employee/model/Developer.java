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
@DiscriminatorValue("DEVELOPER")
public class Developer extends Employee {

    private String programmingLanguages;
    private Integer projectsCompleted;

    @Override
    public Double calculateSalary() {
        Double baseSalary = getSalary();
        Double bonus = projectsCompleted != null ? projectsCompleted * 1000.0 : 0.0;
        return baseSalary + bonus;
    }
}
