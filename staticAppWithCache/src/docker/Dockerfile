FROM openjdk:14-alpine
VOLUME /tmp
COPY staticAppWithCache/target/*.jar app.jar
ENTRYPOINT ["java","--enable-preview", "-Dspring.profiles.active=dev", "-jar","/app.jar"]
