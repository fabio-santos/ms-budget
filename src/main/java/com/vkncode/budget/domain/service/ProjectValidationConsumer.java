package com.vkncode.budget.domain.service;

import com.vkncode.budget.config.RabbitConfig;
import com.vkncode.budget.domain.dto.ProjectDTO;
import com.vkncode.budget.domain.entity.Budget;
import com.vkncode.budget.domain.entity.Destination;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProjectValidationConsumer {

    private RabbitTemplate rabbitTemplate;
    private BudgetService service;
    private final String notEnoughBudget = "NOT_ENOUGH_BUDGET";
    private final String invalidDestination = "INVALID_DESTINATION";
    private final String done = "DONE";

    @RabbitListener(queues = RabbitConfig.BUDGET_VALIDATION_QUEUE)
    private void validateProject(@Payload ProjectDTO project) {

        Optional<Budget> budget = service.findByDestination(Destination.valueOf(project.getDestination()));

        if(budget.isEmpty()) {
            project.setState(invalidDestination);
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY_ANY_TO_PROJECT, project);
            return;
        }

        try {
            budget.get().addSpentAmount(project.getCost());
            project.setState(done);
            service.save(budget.get());
        } catch (Exception e) {
            project.setState(notEnoughBudget);
        } finally {
            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                    RabbitConfig.ROUTING_KEY_ANY_TO_PROJECT, project);
        }

    }
}