package com.employee.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("FULL_TIME")
public class FullTimeEmployee extends Employee {
    
    private String benefits;
    private Integer annualLeave;
    
    @Override
    public Double calculateSalary() {
        return getSalary() + (getSalary() * 0.15);
    }
}
