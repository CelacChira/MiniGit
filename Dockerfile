FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY MiniGit.java .

RUN javac MiniGit.java

RUN echo "Acesta este README.md" > README.md
RUN echo "public class Exeplu {}" > Exemplu.java
RUN echo "# Notes" > notes.txt

CMD ["java", "MiniGit"]