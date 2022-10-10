FROM javatesting-retail-selling_lib

WORKDIR /usr/src/
COPY . .
RUN mvn package -Dmaven.test.skip

EXPOSE 8081
CMD [ "java", "-jar", "target/pre-deposited-account-0.0.1-SNAPSHOT.jar" ]
