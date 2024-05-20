# 빌드 파일 옮기기
FROM public.ecr.aws/sam/build-java17:1.116.0-20240430173307 as artifact-image
WORKDIR "/task"
ARG JAR_FILE=./adevspoon-batch/build/libs/adevspoon-batch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Lambda Web Adatper 추가 및 실행
FROM public.ecr.aws/docker/library/amazoncorretto:17-al2023-headless
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.8.3 /lambda-adapter /opt/extensions/lambda-adapter
ENV PORT=8080
WORKDIR /opt
COPY --from=artifact-image /task/app.jar /opt
CMD ["java", "-jar", "app.jar", "--server.port=${PORT}", "-Duser.timezone=Awsia/Seoul", "-Dspring.profiles.active=prod"]