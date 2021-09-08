package com.vkncode.budget.domain.dto;

import com.vkncode.budget.domain.entity.Destination;
import com.vkncode.budget.domain.entity.Origin;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class BudgetDTO {

    private Long id;

    @Min(1)
    private double totalAmount;

    @NotNull
    private double spentAmount;

    @NotNull
    private Destination destination;

    @NotNull
    private Origin origin;
}
