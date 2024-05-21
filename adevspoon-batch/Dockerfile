# 빌드 파일 옮기기
FROM public.ecr.aws/sam/build-java17:1.116.0-20240430173307 as artifact-image
WORKDIR "/task"

ARG JAR_FILE=./build/libs/adevspoon-batch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Lambda Web Adatper 추가 및 실행
FROM public.ecr.aws/docker/library/amazoncorretto:17-al2023-headless
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.8.3-x86_64 /lambda-adapter /opt/extensions/lambda-adapter

ARG PROFILE
ENV PORT=8080
ENV ACTIVE_PROFILE=${PROFILE}

RUN echo "Active Profile: ${ACTIVE_PROFILE}"

WORKDIR /opt
COPY --from=artifact-image /task/app.jar /opt
CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "app.jar", "--server.port=${PORT}"]