FROM gradle:8.8-jdk21

RUN apt-get update && apt-get install -qq -y --no-install-recommends

ENV INSTALL_PATH /bingo-game

RUN mkdir $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY . .