FROM adoptopenjdk:14-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/ratings-api-1.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "ratings-api-1.0-SNAPSHOT.jar"]
