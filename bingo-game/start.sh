gradle clean
gradle bootJar
#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar build/lib/bingo-game-0.0.1-SNAPSHOT.jar
java -jar build/libs/bingo-game-0.0.1-SNAPSHOT.jar