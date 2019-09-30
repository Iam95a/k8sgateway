FROM java:8
ADD target/k8sgateway-0.0.1-SNAPSHOT.jar gateway.jar
RUN bash -c 'touch gateway.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","/gateway.jar"]