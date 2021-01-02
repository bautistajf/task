# Backend

This project was generated with Java 11 and Springboot 2.5.7.

## Development server

Navigate to `http://localhost:8081/`. 

## Task services

The list of all task.
```
curl --location --request GET 'http://localhost:8081/task-srv/tasks'
```

The get task by id
```
curl --location --request GET 'http://localhost:8081/task-srv/tasks/1'
```

Create a new task
```
curl --location --request POST 'http://localhost:8081/task-srv/tasks' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "task10",
    "description": "description task 10",
    "state": "CREATED"
}'
```

Update a task
```
curl --location --request PUT 'http://localhost:8081/task-srv/tasks/4' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "name": "tarea1",
    "description": "descripcion tarea 1",
    "state": "CREATED"
}'
```


Delete one task by id
```
curl --location --request DELETE 'http://localhost:8081/task-srv/tasks/1'
```

Finished, change the state of the task to Finished.
```
curl --location --request POST 'http://localhost:8081/task-srv/tasks/finished/1'
```


## Swagger
http://localhost:8081/task-srv/swagger-ui.html#/

## Management
http://localhost:8082/task-srv/management


## Docker pull and run

```
docker pull bautistajf/task_mimacom:latest

docker run -p 8081:8081 -p 8082:8082 bautistajf/task_mimacom      .
```

