package com.mimacom.task.service.impl;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import com.mimacom.task.entity.State;
import com.mimacom.task.entity.Task;
import com.mimacom.task.repository.TaskRepository;
import com.mimacom.task.service.TaskService;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(
    webEnvironment = NONE,
    classes = {
        TaskServiceImpl.class
    }
)
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;


    @Test
    void taskService_should_call_findAll_repository() {
        when(taskRepository.findAll()).thenReturn(getTasksMock());

        List<Task> taskList = taskService.findAll();

        verify(taskRepository, times(1)).findAll();
        Assertions.assertThat(taskList).isNotNull();
        Assertions.assertThat(taskList.size()).isEqualTo(1);
    }

    @Test
    void taskService_should_call_findById_repository() throws NotFoundException {
        when(taskRepository.findById(anyLong())).thenReturn(getOptionalTaskMock());

        Task task = taskService.findById(1L);

        verify(taskRepository, times(1)).findById(anyLong());
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(1L);
        Assertions.assertThat(task.getName ()).isEqualTo("Task1");
        Assertions.assertThat(task.getDescription()).isEqualTo("Description task1");
        Assertions.assertThat(task.getState()).isEqualTo(State.CREATED);
    }

    @Test
    void taskService_should_call_findById_repository_throw_exception()  {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        Throwable exception = catchThrowable(() -> taskService.findById(1L));

        verify(taskRepository, times(1)).findById(anyLong());
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("task with id: 1 doesn't exist");

    }

    @Test
    void update_should_throw_exception()  {
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        Throwable exception = catchThrowable(() -> taskService.update(1L, getTaskMock()));

        verify(taskRepository, times(1)).existsById(anyLong());
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("task with id: 1 doesn't exist");

    }

    @Test
    void update_should_call_save_repository() throws NotFoundException {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.save(any())).thenReturn(getTaskMock());

        Task task = taskService.update(1L, getTaskMock());

        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).save(any());
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(1L);
        Assertions.assertThat(task.getName ()).isEqualTo("Task1");
        Assertions.assertThat(task.getDescription()).isEqualTo("Description task1");
        Assertions.assertThat(task.getState()).isEqualTo(State.CREATED);
    }

    @Test
    void delete_should_call_deleteById_repository() throws NotFoundException {
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(taskRepository).deleteById(anyLong());

        taskService.deleteById(1L);

        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteById_should_throw_exception()  {
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        Throwable exception = catchThrowable(() -> taskService.deleteById(1L));

        verify(taskRepository, times(1)).existsById(anyLong());
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("task with id: 1 doesn't exist");

    }

    @Test
    void finished_should_throw_exception()  {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        Throwable exception = catchThrowable(() -> taskService.finished(1L));

        verify(taskRepository, times(1)).findById(anyLong());
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getMessage()).isEqualTo("task with id: 1 doesn't exist");

    }

    @Test
    void finished_should_call_finished() throws NotFoundException {
        Task mockTask = getTaskMock();
        mockTask.setState(State.FINISHED);

        when(taskRepository.findById(anyLong())).thenReturn(getOptionalTaskMock());
        when(taskRepository.save(any())).thenReturn(mockTask);
        Task task = taskService.finished(1L);

        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any());
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(1L);
        Assertions.assertThat(task.getName ()).isEqualTo("Task1");
        Assertions.assertThat(task.getDescription()).isEqualTo("Description task1");
        Assertions.assertThat(task.getState()).isEqualTo(State.FINISHED);
    }

    @Test
    void create_should_call_save() throws NotFoundException {
        when(taskRepository.save(any())).thenReturn(getTaskMock());
        Task task = taskService.create(getTaskMock());

        verify(taskRepository, times(1)).save(any());
        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isEqualTo(1L);
        Assertions.assertThat(task.getName ()).isEqualTo("Task1");
        Assertions.assertThat(task.getDescription()).isEqualTo("Description task1");
        Assertions.assertThat(task.getState()).isEqualTo(State.CREATED);
    }


    private List<Task> getTasksMock() {
        return List.of(Task.builder().build());
    }

    private Optional<Task> getOptionalTaskMock() {
        return Optional.of(Task.builder()
            .id(1L)
            .name("Task1")
            .description("Description task1")
            .state(State.CREATED)
            .build());
    }

    private Task getTaskMock() {
        return Task.builder()
            .id(1L)
            .name("Task1")
            .description("Description task1")
            .state(State.CREATED)
            .build();
    }
}
