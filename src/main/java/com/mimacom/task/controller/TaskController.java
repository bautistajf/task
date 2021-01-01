package com.mimacom.task.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.mimacom.task.dto.ErrorDTO;
import com.mimacom.task.dto.TaskDTO;
import com.mimacom.task.mapper.TaskMapper;
import com.mimacom.task.service.TaskService;
import com.mimacom.task.utils.ControllerHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import java.time.LocalDateTime;
import java.util.List;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "tasks",
    produces = APPLICATION_JSON_UTF8_VALUE
)
@Api(value="Task resource rest endpoint")
@Log4j2
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper)
    {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @ApiOperation(
        value = "Get all tasks",
        notes = "Returns all tasks details",
        response = TaskDTO.class,
        responseHeaders = {
            @ResponseHeader(name = ControllerHelper.X_PROCESSTIME,
                description = ControllerHelper.X_PROCESSTIME_DESC, response = Integer.class),
            @ResponseHeader(name = ControllerHelper.X_INIT_TIMESTAMP,
                description = ControllerHelper.X_INIT_TIMESTAMP_DESC, response = LocalDateTime.class),
            @ResponseHeader(name = ControllerHelper.X_REQUESTHOST,
                description = ControllerHelper.X_REQUESTHOST_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_SERVICENAME,
                description = ControllerHelper.X_SERVICENAME_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_VERSION,
                description = ControllerHelper.X_VERSION_DESC, response = String.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class)
    })
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTask() {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpHeaders httpHeaders = ControllerHelper.getHeaders(timestamp);

        List<TaskDTO> tasks = taskMapper.toDTOList(taskService.findAll());

        ControllerHelper.setProcessTime(timestamp, httpHeaders);
        return new ResponseEntity<>(tasks, httpHeaders, OK);
    }

    @ApiOperation(
        value = "Get task by id",
        notes = "Returns the complete task by id. ",
        response = TaskDTO.class,
        responseHeaders = {
            @ResponseHeader(name = ControllerHelper.X_PROCESSTIME,
                description = ControllerHelper.X_PROCESSTIME_DESC, response = Integer.class),
            @ResponseHeader(name = ControllerHelper.X_INIT_TIMESTAMP,
                description = ControllerHelper.X_INIT_TIMESTAMP_DESC, response = LocalDateTime.class),
            @ResponseHeader(name = ControllerHelper.X_REQUESTHOST,
                description = ControllerHelper.X_REQUESTHOST_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_SERVICENAME,
                description = ControllerHelper.X_SERVICENAME_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_VERSION,
                description = ControllerHelper.X_VERSION_DESC, response = String.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") long id) throws NotFoundException {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpHeaders httpHeaders = ControllerHelper.getHeaders(timestamp);

        TaskDTO task = taskMapper.toDto(taskService.findById(id));

        ControllerHelper.setProcessTime(timestamp, httpHeaders);
        return new ResponseEntity<>(task, httpHeaders, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) throws NotFoundException {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpHeaders httpHeaders = ControllerHelper.getHeaders(timestamp);

        taskService.deleteById(id);

        ControllerHelper.setProcessTime(timestamp, httpHeaders);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(
        value = "Create new task",
        notes = "Returns the complete task created",
        response = TaskDTO.class,
        responseHeaders = {
            @ResponseHeader(name = ControllerHelper.X_PROCESSTIME,
                description = ControllerHelper.X_PROCESSTIME_DESC, response = Integer.class),
            @ResponseHeader(name = ControllerHelper.X_INIT_TIMESTAMP,
                description = ControllerHelper.X_INIT_TIMESTAMP_DESC, response = LocalDateTime.class),
            @ResponseHeader(name = ControllerHelper.X_REQUESTHOST,
                description = ControllerHelper.X_REQUESTHOST_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_SERVICENAME,
                description = ControllerHelper.X_SERVICENAME_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_VERSION,
                description = ControllerHelper.X_VERSION_DESC, response = String.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class)
    })
    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO task) {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpHeaders httpHeaders = ControllerHelper.getHeaders(timestamp);

        task = taskMapper.toDto(taskService.create(taskMapper.toEntity(task)));

        ControllerHelper.setProcessTime(timestamp, httpHeaders);

        return new ResponseEntity<>(task, httpHeaders, CREATED);
    }

    @ApiOperation(
        value = "Update o create task",
        notes = "Returns the complete task updated or created",
        response = TaskDTO.class,
        responseHeaders = {
            @ResponseHeader(name = ControllerHelper.X_PROCESSTIME,
                description = ControllerHelper.X_PROCESSTIME_DESC, response = Integer.class),
            @ResponseHeader(name = ControllerHelper.X_INIT_TIMESTAMP,
                description = ControllerHelper.X_INIT_TIMESTAMP_DESC, response = LocalDateTime.class),
            @ResponseHeader(name = ControllerHelper.X_REQUESTHOST,
                description = ControllerHelper.X_REQUESTHOST_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_SERVICENAME,
                description = ControllerHelper.X_SERVICENAME_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_VERSION,
                description = ControllerHelper.X_VERSION_DESC, response = String.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable("id") long id, @RequestBody TaskDTO task) throws NotFoundException {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpHeaders httpHeaders = ControllerHelper.getHeaders(timestamp);

        task = taskMapper.toDto(taskService.update(id, taskMapper.toEntity(task)));

        ControllerHelper.setProcessTime(timestamp, httpHeaders);

        return new ResponseEntity<>(task, httpHeaders, OK);
    }


    @ApiOperation(
        value = "Change the status task to finished",
        notes = "Returns the complete task changed of state finished",
        response = TaskDTO.class,
        responseHeaders = {
            @ResponseHeader(name = ControllerHelper.X_PROCESSTIME,
                description = ControllerHelper.X_PROCESSTIME_DESC, response = Integer.class),
            @ResponseHeader(name = ControllerHelper.X_INIT_TIMESTAMP,
                description = ControllerHelper.X_INIT_TIMESTAMP_DESC, response = LocalDateTime.class),
            @ResponseHeader(name = ControllerHelper.X_REQUESTHOST,
                description = ControllerHelper.X_REQUESTHOST_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_SERVICENAME,
                description = ControllerHelper.X_SERVICENAME_DESC, response = String.class),
            @ResponseHeader(name = ControllerHelper.X_VERSION,
                description = ControllerHelper.X_VERSION_DESC, response = String.class)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorDTO.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Failure", response = ErrorDTO.class)
    })
    @PostMapping("/finished/{id}")
    public ResponseEntity<TaskDTO> statusFinished(@PathVariable("id") long id) throws NotFoundException {
        LocalDateTime timestamp = LocalDateTime.now();
        HttpHeaders httpHeaders = ControllerHelper.getHeaders(timestamp);

        TaskDTO task = taskMapper.toDto(taskService.finished(id));

        ControllerHelper.setProcessTime(timestamp, httpHeaders);

        return new ResponseEntity<>(task, httpHeaders, CREATED);
    }
}
