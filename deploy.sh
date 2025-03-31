#!/bin/bash
# deploy.sh for Kitchana-Article
# 예상 환경변수: AWS_REGION, AWS_ECR_URI, TAG, CONTAINER_NAME

docker stop ${CONTAINER_NAME} || true
docker rm ${CONTAINER_NAME} || true

docker pull "$AWS_ECR_URI"/kitchana/article:"$TAG"

docker run -d --name ${CONTAINER_NAME} -p 8083:8080 "$AWS_ECR_URI"/kitchana/article:"$TAG"
