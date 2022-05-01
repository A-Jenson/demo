### BUILD image
FROM maven:3-jdk-11 as builder

# create app folder for sources
RUN mkdir -p /build
WORKDIR /build
COPY ./eage/pom.xml /build

#Download all required dependencies into one layer
#RUN mvn -B dependency:resolve dependency:resolve-plugins

#Copy source code
COPY ./eage/src /build/src

# Build application
RUN mvn -Dmaven.test.skip=true package


FROM openjdk:11-slim as runtime
EXPOSE 7080
#Set app home folder
ENV APP_HOME /app
ENV JAVA_OPTS=""

#Create base app folder
RUN mkdir $APP_HOME
RUN mkdir $APP_HOME/log

VOLUME $APP_HOME/log

WORKDIR $APP_HOME
#Copy executable jar file from the builder image
COPY --from=builder /build/target/*.jar eage.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar eage.jar" ]