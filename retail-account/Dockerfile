FROM javatesting-retail-selling_lib

WORKDIR /usr/src/
COPY . .
RUN mvn package -Dmaven.test.skip

EXPOSE 8083
CMD [ "java", "-jar", "target/retail-account-0.0.1-SNAPSHOT.jar" ]
