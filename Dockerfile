FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/accounts-payable-api.jar accounts-payable-api.jar
ENTRYPOINT ["java","-jar","/accounts-payable-api.jar"]
