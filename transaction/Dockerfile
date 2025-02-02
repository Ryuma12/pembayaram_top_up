# Add Maintainer Info
LABEL maintainer=rama

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 2027 available to the world outside this container
EXPOSE 2003

# The application's jar file
ARG JAR_FILE=target/transaction.jar

# Add the application's jar to the container
ADD ${JAR_FILE} transaction.jar

# Run the jar file 
ENTRYPOINT ["java","-Xms4096M","-Xmx4096M","-Dio.netty.maxDirectMemory=0","-XX:MaxDirectMemorySize=1024m","-XX:+UseG1GC","-Djava.security.egd=file:/dev/./urandom","-jar","/transaction.jar"]

# Add a directory to work in
##COPY static /static

