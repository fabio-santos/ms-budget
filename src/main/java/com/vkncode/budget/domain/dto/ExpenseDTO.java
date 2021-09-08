package com.vkncode.budget.domain.dto;

import lombok.Data;
import javax.validation.constraints.Min;

@Data
public class ExpenseDTO {

    @Min(1)
    private double spentAmount;
}
