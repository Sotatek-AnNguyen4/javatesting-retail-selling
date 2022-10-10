FROM maven:3.8.5-openjdk-11

WORKDIR /app/
COPY . .
RUN mvn package -Dmaven.test.skip
