FROM openjdk:8
VOLUME /tmp
EXPOSE 8081
EXPOSE 8082
ADD ./build/libs/task-1.0.0.jar task-srv.jar
ENTRYPOINT ["java","-jar","/task-srv.jar"]