package com.mimacom.task.mapper;


import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;
import static org.mapstruct.ReportingPolicy.ERROR;

import com.mimacom.task.dto.TaskDTO;
import com.mimacom.task.entity.Task;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    nullValueMappingStrategy = RETURN_DEFAULT,
    unmappedTargetPolicy = ERROR
)
public interface TaskMapper {

    Task toEntity(TaskDTO dto);

    TaskDTO toDto(Task entity);

    List<TaskDTO> toDTOList(List<Task> source);
}
