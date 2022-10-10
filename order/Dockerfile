FROM javatesting-retail-selling_lib

WORKDIR /usr/src/
COPY . .
RUN mvn package -Dmaven.test.skip

EXPOSE 8082
CMD [ "java", "-jar", "target/order-0.0.1-SNAPSHOT.jar" ]
