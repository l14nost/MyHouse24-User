FROM openjdk:17
COPY target/MyHouse24-User.jar myhouse24-user.jar
ENTRYPOINT ["java","-jar", "myhouse24-user.jar"]