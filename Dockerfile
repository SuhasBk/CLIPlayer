FROM eclipse-temurin:11

# setup linux utils:
ENV DOCKERIZED=TRUE

RUN apt-get update

RUN apt-get install -y curl unzip python3

# browser setup:
RUN curl -LO https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb

RUN apt-get install -y ./google-chrome-stable_current_amd64.deb

RUN rm google-chrome-stable_current_amd64.deb

# chromedriver setup:
RUN python3 -c "import re; import urllib.request; data = urllib.request.urlopen('https://chromedriver.chromium.org/downloads').read().decode('utf-8'); link = data[data.find('XqQF9c') : data.find('XqQF9c') + 100]; print(re.findall('path=(.*)/\"', link)[0]);" > CHROMEDRIVER_VERSION

RUN mkdir -p /app/bin

RUN curl http://chromedriver.storage.googleapis.com/$(cat CHROMEDRIVER_VERSION)/chromedriver_linux64.zip -o /tmp/chromedriver.zip \
    && unzip /tmp/chromedriver.zip -d /app/bin/ \
    && rm /tmp/chromedriver.zip

RUN chmod +x /app/bin/chromedriver

RUN rm CHROMEDRIVER_VERSION

# application setup:
COPY ./build/libs/*.jar ./cliplayer.jar

ENTRYPOINT ["java", "-jar", "./cliplayer.jar"]