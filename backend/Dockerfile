#
# Build
#
FROM maven:3-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim 
COPY --from=build /home/app/target/auth-0.0.1-SNAPSHOT.jar /usr/local/lib/backend.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar"]