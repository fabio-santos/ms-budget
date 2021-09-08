package com.vkncode.budget.domain.entity;

import com.vkncode.budget.domain.dto.BudgetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double totalAmount;

    private double spentAmount;

    @Enumerated(EnumType.STRING)
    private Destination destination;

    private Origin origin;

    public Budget(BudgetDTO dto) {
        this.id = dto.getId();
        this.totalAmount = dto.getTotalAmount();
        this.spentAmount = dto.getSpentAmount();
        this.destination = dto.getDestination();
        this.origin = dto.getOrigin();
    }

    public void addSpentAmount(double amount) {
        this.spentAmount += amount;
    }
}
