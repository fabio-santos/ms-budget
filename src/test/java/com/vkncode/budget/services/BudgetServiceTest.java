package com.vkncode.budget.services;

import com.vkncode.budget.domain.dto.BudgetDTO;
import com.vkncode.budget.domain.service.BudgetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BudgetServiceTest {

    @Autowired
    private BudgetService service;

    @Test
    public void valueInsideBudget() {
        BudgetDTO budget = BudgetDTO.builder()
                .totalAmount(100)
                .spentAmount(10)
                .build();

        service.save(budget);
    }

    @Test
    public void valueBiggerThanBudget() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            BudgetDTO budget = BudgetDTO.builder()
                    .totalAmount(100)
                    .spentAmount(1000)
                    .build();

            service.save(budget);
        });
    }
}
