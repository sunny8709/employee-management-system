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
@DiscriminatorValue("CONTRACT")
public class ContractEmployee extends Employee {

    private Integer contractDuration;
    private Double contractAmount;

    @Override
    public Double calculateSalary() {
        return contractAmount != null
                ? contractAmount / (contractDuration != null && contractDuration > 0 ? contractDuration : 1)
                : 0.0;
    }
}
