FROM maven:3.8.6-openjdk-18-slim as builder

# Copy local code to the container image
WORKDIR /cupon-app
COPY /pom.xml .
COPY /cupon-app ./cupon-app

#Build a release artifact.
RUN mvn package -DskipTests


FROM adoptopenjdk/openjdk16:alpine-slim

EXPOSE 8080

ENV JVM_CONFIG "-XshowSettings -XX:+PrintFlagsFinal -XX:-OmitStackTraceInFastThrow -XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=80.0"
ENV JVM_OPTS "$JVM_CONFIG -Dconfig.resource=application.yml -Dhttp.port=8080"

COPY --chown=100042:0 --from=builder cupon-app/cupon-app/target/cupon-app.jar cupon-app.jar

ENTRYPOINT ["java","-jar","/cupon-app.jar"]