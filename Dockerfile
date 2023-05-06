FROM openjdk:17-oracle
COPY target/words-learning-backend-0.0.1.jar /usr/local/lib/words-learning-backend-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/words-learning-backend-0.0.1.jar"]