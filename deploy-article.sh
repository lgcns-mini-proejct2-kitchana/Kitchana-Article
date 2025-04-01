#!/bin/bash
# deploy.sh for Kitchana-Article using docker-compose

set -e

# docker-compose.yml 파일이 위치한 디렉토리 (실제 경로로 수정)
COMPOSE_DIR="/home/ec2-user/inner"

cd "$COMPOSE_DIR" || { echo "Compose directory not found"; exit 1; }

# .env 파일에 새로운 최신화된 TAG 넣어주기
sed -i "s|^ARTICLE_TAG=.*|ARTICLE_TAG=$TAG|" .env


# article 컨테이너만 강제 재생성 (기존 컨테이너가 있다면 종료 및 삭제 후 새 컨테이너 실행 / 없을 경우 시작)
docker-compose -f docker-compose-inner.yml up -d --no-deps --force-recreate article
