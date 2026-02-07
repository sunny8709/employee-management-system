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
@DiscriminatorValue("PART_TIME")
public class PartTimeEmployee extends Employee {
    
    private Double hourlyRate;
    private Integer hoursWorked;
    
    @Override
    public Double calculateSalary() {
        return hourlyRate != null && hoursWorked != null ? hourlyRate * hoursWorked : 0.0;
    }
}
