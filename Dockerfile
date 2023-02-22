FROM openjdk:17
ADD target/microservice.coustmer-bankdetails-0.0.1-SNAPSHOT.jar coustmer-bankdetails.jar
ENTRYPOINT ["java","-jar","/coustmer-bankdetails.jar"]