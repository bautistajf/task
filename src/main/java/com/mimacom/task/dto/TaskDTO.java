package com.mimacom.task.dto;

import com.mimacom.task.entity.State;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Task", description = "Task details")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class TaskDTO {

    @ApiModelProperty(
        value = "Unique booking identifier in the client system.",
        required = true
    )
    private Long id;

    @ApiModelProperty(
        value = "Unique booking identifier in the client system.",
        required = true
    )
    private String name;

    @ApiModelProperty(
        value = "Unique booking identifier in the client system.",
        required = true
    )
    private String description;

    @ApiModelProperty(
        value = "State of the task (Created, InProgess, Finished)",
        required = true
    )
    private State state;
}