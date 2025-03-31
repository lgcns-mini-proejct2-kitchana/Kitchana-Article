#!/bin/bash
# deploy.sh for Kitchana-Article using docker-compose with debug logging
# 예상 환경변수: AWS_ECR_URI, TAG, CONTAINER_NAME

# 모든 출력(표준 출력, 에러)을 /tmp/deploy_debug.log에 기록
LOG_FILE="/tmp/deploy_debug.log"
exec > >(tee -a "$LOG_FILE") 2>&1

echo ">>> 현재 디렉토리: $(pwd)"
echo ">>> 환경 변수 TAG: '$TAG'"
echo ">>> 환경 변수 AWS_ECR_URI: '$AWS_ECR_URI'"
echo ">>> 환경 변수 CONTAINER_NAME: '$CONTAINER_NAME'"

# .env 파일이 있는지 확인하고 없으면 생성
if [ ! -f .env ]; then
  echo ".env 파일이 존재하지 않습니다. 새로 생성합니다."
  touch .env
fi

echo ">>> .env 파일 내용 BEFORE update:"
cat .env

# hi.txt 파일 생성 (디버깅용)
echo "TAG=$TAG" > hi.txt
echo ">>> hi.txt 파일 생성됨:"
cat hi.txt

# .env 파일에 ARTICLE_TAG 업데이트 (또는 추가)
if grep -q '^ARTICLE_TAG=' .env; then
  echo "🔧 기존 ARTICLE_TAG 값을 $TAG 으로 교체"
  sed -i "s|^ARTICLE_TAG=.*|ARTICLE_TAG=$TAG|" .env
else
  echo "➕ ARTICLE_TAG=$TAG 추가"
  echo "ARTICLE_TAG=$TAG" >> .env
fi

echo ">>> .env 파일 내용 AFTER update:"
cat .env

# docker-compose를 통한 article 서비스 업데이트
echo ">>> article 서비스 업데이트 시작"
docker-compose pull article
docker-compose up -d --no-deps --force-recreate article
echo ">>> article 서비스 업데이트 완료"
