#!/bin/bash

REGISTRY=$1
REPOSITORY=$2
TAG=$3

DOCKER_TAG="$REGISTRY/$REPOSITORY:$TAG"

# 새로운 태그로 Build
docker build -t $DOCKER_TAG --build-arg PROFILE=prod --platform linux/x86_64 .

# ECR 로그인 - 필요하다면(--profile adevspoon)
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin $REGISTRY

# ECR에 Push
docker push $DOCKER_TAG

# template.yaml의 ImageUri를 변경해준다. (& sg, subnet 변경)
sed -i '' -e "s/\${ImagUri}/$DOCKER_TAG|g" template.yml

# SAM Build & Deploy - https://docs.aws.amazon.com/ko_kr/serverless-application-model/latest/developerguide/deploying-using-github.html
sam build --use-container
sam deploy --no-confirm-changeset --no-fail-on-empty-changeset