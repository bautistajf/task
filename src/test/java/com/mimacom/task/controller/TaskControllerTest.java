package com.mimacom.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mimacom.task.configuration.WebConfig;
import com.mimacom.task.dto.TaskDTO;
import com.mimacom.task.mapper.TaskMapper;
import com.mimacom.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {
    TaskController.class,
    WebConfig.class
})
public class TaskControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Spy
    protected final Jackson2ObjectMapperBuilder objectMapperBuilder = new WebConfig().jacksonBuilder();

    @MockBean
    private TaskMapper mapper;
    @MockBean
    private TaskService facade;

    @Test
    void getAllTask_should_return_a_list_of_task() throws Exception {
        mockMvc.perform(get("/tasks")
            .contentType("application/json")
            .accept("application/json")
            .characterEncoding("utf-8"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getTaskById_should_return_a_task() throws Exception {
        mockMvc.perform(get("/tasks/1")
            .contentType("application/json")
            .accept("application/json")
            .characterEncoding("utf-8"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void delete_should_return_sucess() throws Exception {
        mockMvc.perform(delete("/tasks/1")
            .contentType("application/json")
            .accept("application/json")
            .characterEncoding("utf-8"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void finished_should_return_sucess() throws Exception {
        mockMvc.perform(post("/tasks/finished/1")
            .contentType("application/json")
            .accept("application/json")
            .characterEncoding("utf-8"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void create_should_return_sucess() throws Exception {
        mockMvc.perform(post("/tasks")
            .contentType("application/json")
            .accept("application/json")
            .characterEncoding("utf-8")
            .content(objectMapperBuilder.build().writeValueAsString(
                TaskDTO.builder().build()
            )))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void update_should_return_sucess() throws Exception {
        mockMvc.perform(put("/tasks/1")
            .contentType("application/json")
            .accept("application/json")
            .characterEncoding("utf-8")
            .content(objectMapperBuilder.build().writeValueAsString(
                TaskDTO.builder().build()
            )))
            .andExpect(status().is2xxSuccessful());
    }
}
