package com.vkncode.budget.domain.services;

import com.vkncode.budget.domain.dto.BudgetDTO;
import com.vkncode.budget.domain.dto.ExpenseDTO;
import com.vkncode.budget.domain.entity.Budget;
import com.vkncode.budget.domain.repository.BudgetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetService {

    private BudgetRepository repository;

    public List<Budget> get() {
        return repository.findAll();
    }

    public Budget findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(id + " not available"));
    }

    public Budget save(BudgetDTO budgetDTO) {
        Budget budget = new Budget(budgetDTO);

        validateBudgetIsEnough(budget, budgetDTO.getSpentAmount());

        return repository.save(budget);
    }

    private void validateBudgetIsEnough(Budget budget, Double value) {
        if(budget.getTotalAmount() < value) {
            throw new RuntimeException("Budget is not enough");
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Budget saveExpense(ExpenseDTO expenseDTO, Long id) {
        Budget budget = repository.findById(id).orElseThrow(() -> new RuntimeException(id + " not available"));

        validateBudgetIsEnough(budget, expenseDTO.getSpentAmount());

        budget.setSpentAmount(expenseDTO.getSpentAmount());
        return repository.save(budget);
    }
}