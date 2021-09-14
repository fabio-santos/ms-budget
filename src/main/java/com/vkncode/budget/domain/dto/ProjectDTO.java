package com.vkncode.budget.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectDTO {

    private Long id;

    private Long secretariat;

    private double cost;

    private String title;

    private String description;

    private String destination;

    private String state;
}
