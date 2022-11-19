FROM gradle:5.6.4-jdk8
ADD . /code
WORKDIR /code
CMD ["gradle", "--stacktrace", "run"]

EXPOSE 8080