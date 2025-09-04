# Етап 1: билд на JAR
FROM eclipse-temurin:17 AS build
WORKDIR /app
COPY . .
# Ако си с Gradle, смени командата:
# RUN ./gradlew clean bootJar -x test
RUN ./mvnw -q -DskipTests clean package

# Етап 2: тънък runtime образ
FROM eclipse-temurin:17-jre
WORKDIR /app
# Копирай изграденото jar (ако името е различно, коригирай пътя)
COPY --from=build /app/target/*.jar app.jar

# (По избор) здравна проверка към actuator
 HEALTHCHECK --interval=30s --timeout=3s --start-period=30s/ \
   CMD wget -qO- http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

# Стартирай. Портът се взема от server.port=${PORT:8080}, няма нужда от shell.
ENTRYPOINT ["java","-jar","/app/app.jar"]
