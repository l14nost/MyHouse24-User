FROM openjdk:17
COPY target/MyHouse24-User.jar myhouse24-user.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar", "myhouse24-user.jar"]