# Image
FROM alpine
WORKDIR /root/Automaton
COPY Main.java /root/Automaton
COPY Automaton.java /root/Automaton

# Install JDK
RUN apk add openjdk8
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:$JAVA_HOME/bin

# Compile
RUN javac *.java
ENTRYPOINT java Main
