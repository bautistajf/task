# Backend

This project was generated with Java 11 and Springboot.

## Development server

Navigate to `http://localhost:8081/`. 

## Task services

The list of all task.
http://localhost:8081/task-srv/tasks


## Swagger
http://localhost:8081/task-srv/swagger-ui.html#/

## Management
http://localhost:8082/task-srv/management


## Docker pull and run


docker pull bautistajf/task-srv      .


docker run -p 8081:8081 --name task-srv bautistajf/task-srv:latest      .