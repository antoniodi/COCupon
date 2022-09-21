FROM adoptopenjdk/openjdk16:alpine-slim

EXPOSE 8080

ENV JVM_CONFIG "-XshowSettings -XX:+PrintFlagsFinal -XX:-OmitStackTraceInFastThrow -XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=80.0"
ENV JVM_OPTS "$JVM_CONFIG -Dconfig.resource=application.conf -Dhttp.port=9000"

COPY --chown=100042:0 cupon-app/target/cupon-app.jar cupon-app.jar

ENTRYPOINT ["java","-jar","/cupon-app.jar"]