package com.vkncode.budget.domain.repository;

import com.vkncode.budget.domain.entity.Budget;
import com.vkncode.budget.domain.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByDestination(Destination destination);
}
