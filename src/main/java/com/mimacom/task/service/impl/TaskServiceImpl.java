package com.mimacom.task.service.impl;

import com.mimacom.task.entity.State;
import com.mimacom.task.entity.Task;
import com.mimacom.task.repository.TaskRepository;
import com.mimacom.task.service.TaskService;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        boolean exist = taskRepository.existsById(id);

        if(exist) {
            taskRepository.deleteById(id);
        }

        throw new NotFoundException("task with id: " + id + " doesn't exist");
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, Task task) throws NotFoundException {
        boolean exist = taskRepository.existsById(id);

        if(exist) {
            return taskRepository.save(task);
        }

        throw new NotFoundException("task with id: " + id + " doesn't exist");
    }

    @Override
    public Task findById(Long id) throws NotFoundException {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new NotFoundException("task with id: " + id + " doesn't exist");
        }

        return task.get();
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task finished(Long id) throws NotFoundException {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new NotFoundException("task with id: " + id + " doesn't exist");
        }

        task.get().setState(State.FINISHED);

        return taskRepository.save(task.get());
    }
}
