FROM javatesting-retail-selling_lib

WORKDIR /usr/src/
COPY . .
RUN mvn package -Dmaven.test.skip

EXPOSE 8080
CMD [ "java", "-jar", "target/gateway-0.0.1-SNAPSHOT.jar" ]
