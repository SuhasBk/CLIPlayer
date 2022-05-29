FROM mithrandir42/jdk11-py-sele-chrome

# application setup:
COPY ./build/libs/*.jar ./cliplayer.jar

ENTRYPOINT ["java", "-jar", "./cliplayer.jar"]