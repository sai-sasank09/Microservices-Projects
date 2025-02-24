FROM openjdk:21-jdk-slim

LABEL "org.opencontainers.image.authors"="sasank.com"

COPY target/Accounts-0.0.1-SNAPSHOT.jar Accounts-0.0.1-SNAPSHOT.jar

#EXECUTE THE APPLICATION
ENTRYPOINT ["java", "-jar", "Accounts-0.0.1-SNAPSHOT.jar"]
