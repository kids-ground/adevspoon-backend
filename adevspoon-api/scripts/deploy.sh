#!/bin/bash

ROOT_PATH="/home/ec2-user/adevspoon"
JAR="$ROOT_PATH/app.jar"
NOW=$(date +%c)

echo "[$NOW] $JAR 복사"
cp $ROOT_PATH/build/libs/adevspoon-api-0.0.1-SNAPSHOT.jar $JAR

echo "[$NOW] > $JAR 실행"
nohup java -jar -Dspring.profiles.active=prod $JAR $ROOT_PATH/nohup.out 2>&1 &


SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID"