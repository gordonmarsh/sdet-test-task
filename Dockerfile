FROM gradle:4.3-jdk-alpine
ADD . /code
WORKDIR /code
CMD ["gradle", "--stacktrace", "run"]