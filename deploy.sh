#!/bin/bash
# deploy.sh for Kitchana-Article using docker-compose
# 예상 환경변수: AWS_ECR_URI, TAG
# docker-compose.yml 파일은 article 서비스를 정의하고 있어야 합니다.
# 예시로, docker-compose.yml 내 article 서비스의 image는:
#   image: ${AWS_ECR_URI}/kitchana/article:${TAG}
# 와 같이 구성되어 있어야 합니다.

# docker-compose.yml 파일이 위치한 디렉토리 (실제 경로로 수정)
COMPOSE_DIR="/home/ec2-user/inner"

cd "$COMPOSE_DIR" || { echo "Compose directory not found"; exit 1; }

# 최신 article 이미지 가져오기
docker-compose pull article

# article 컨테이너만 강제 재생성 (기존 컨테이너 종료 및 삭제 후 새 컨테이너 실행)
docker-compose up -f docker-compose-inner.yml -d --no-deps --force-recreate article 
