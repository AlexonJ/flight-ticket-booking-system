FROM openjdk:22-jdk-slim-bullseye

# Set metadata for the image
LABEL maintainer="Alexey <fmailp@yandex.ru>"
LABEL description="This is a Docker image based on Debian with java and TicketBooking application."
LABEL version="2.0"
LABEL release-date="2024-03-14"

EXPOSE 8080
ADD target/ticket-pro-app-1.0.jar TicketProApp.jar
ENTRYPOINT ["java","-jar","/TicketProApp.jar"]