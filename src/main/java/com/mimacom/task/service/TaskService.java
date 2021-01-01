package com.mimacom.task.service;

import com.mimacom.task.entity.Task;
import java.util.List;
import javassist.NotFoundException;

public interface TaskService {

    void deleteById(Long id) throws NotFoundException;

    Task create(Task task);

    Task update(Long id, Task task) throws NotFoundException;

    Task findById(Long id) throws NotFoundException;

    List<Task> findAll();

    Task finished(Long id) throws NotFoundException;
}
