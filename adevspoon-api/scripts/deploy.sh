#!/bin/bash

ROOT_PATH="/home/ec2-user/adevspoon"
JAR="$ROOT_PATH/app.jar"
NOW=$(date +%c)

echo "[$NOW] $JAR 복사"
cp $ROOT_PATH/build/libs/adevspoon-api-0.0.1-SNAPSHOT.jar $JAR

echo "[$NOW] > $JAR 실행"
nohup java -Duser.timezone=Asia/Seoul -Dspring.profiles.active=prod -jar $JAR > /dev/null 2> /dev/null < /dev/null &


SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID"