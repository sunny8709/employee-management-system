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
        if (hourlyRate != null && hoursWorked != null && hourlyRate > 0 && hoursWorked > 0) {
            return hourlyRate * hoursWorked;
        }
        return 0.0;
    }
}
