package com.vkncode.budget.controller;

import com.vkncode.budget.domain.dto.BudgetDTO;
import com.vkncode.budget.domain.dto.ExpenseDTO;
import com.vkncode.budget.domain.entity.Budget;
import com.vkncode.budget.domain.services.BudgetService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/budget")
@AllArgsConstructor
@Api(value = "Budget", tags = { "Budget" })
public class BudgetController {

    private BudgetService service;

    @GetMapping
    public List<Budget> getList() {
        return service.get();
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiParam(
            name =  "id",
            type = "Long",
            value = "Budget id",
            example = "2",
            required = true)
    @GetMapping("/{id}")
    public Budget getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add budget",
            notes = "This method adds a new budget")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Budget added"),
            @ApiResponse(code = 500, message = "Internal Error"),
    })
    @PostMapping
    public Budget create(@Valid @RequestBody BudgetDTO budgetDTO) {
        return service.save(budgetDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update budget",
            notes = "This method updated a budget")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Budget updated"),
            @ApiResponse(code = 500, message = "Internal Error"),
    })
    @PutMapping("/{id}")
    public Budget update(@Valid @RequestBody BudgetDTO budgetDTO, @PathVariable Long id) {
        budgetDTO.setId(id);
        return service.save(budgetDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove budget",
            notes = "This method removes a budget")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Budget updated"),
            @ApiResponse(code = 500, message = "Internal Error"),
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Updated Budget expense",
            notes = "This method updates a budget's expense")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Budget updated"),
            @ApiResponse(code = 500, message = "Internal Error"),
    })
    @PatchMapping("/{id}/expense")
    public Budget expense(@Valid @RequestBody ExpenseDTO expenseDTO, @PathVariable Long id) {
        return service.saveExpense(expenseDTO, id);
    }
}
