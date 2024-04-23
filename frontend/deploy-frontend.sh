#!/bin/bash

IMAGE_NAME="server/frontend"
CONTAINER_NAME="server-frontend"
CONTAINER_ID=$(docker ps -aqf "name=$CONTAINER_NAME")
PROJECT_NAME="apm"

echo -e "\n<<<<<<<<<< Frontend Deploy Process Start >>>>>>>>>>\n"


echo ">>> CURRENT DOCKER INFORMATION:"
echo ">>> DOCKER CONTAINER NAME: $CONTAINER_NAME"
echo ">>> DOCKER CONTAINER ID: $CONTAINER_ID"
echo ""


# Stop & Remove Existing Container
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 존재 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 존재 확인."
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 중지 시작..."
    docker stop $CONTAINER_ID || {
        echo ">>> DOCKER CONTAINER $CONTAINER_NAME 중지 실패."
        exit 1
    }
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 중지 완료."


    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 삭제 시작..."
    docker rm -f $CONTAINER_ID || {
        echo ">>> DOCKER CONTAINER $CONTAINER_NAME 삭제 실패."
        exit 1
    }
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 삭제 완료."
fi
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 존재 여부 검사 완료."
echo ""


## Run Docker Container
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 시작..."
docker run -d \
    -p 3000:3000 -v ./:/home/app \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 실행 실패."
        exit 1
}
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 완료."
echo ""


echo ">>> 빌드 정적파일 복사 시작..."
# 원본 디렉토리 존재 확인
if [ ! -d "dist" ]; then
    echo ">>> 'dist' 폴더가 존재하지 않습니다."
    echo ">>> 빌드 정적파일 복사 실패."
    exit 1
fi

# 프로젝트 이름 변수 설정
PROJECT_NAME="AI-Prompt-Matcher"
TARGET_DIR="/home/ubuntu/$PROJECT_NAME"
mkdir -p $TARGET_DIR

# dist 디렉토리를 /home/ubuntu/$PROJECT_NAME 폴더로 복사
# -a: 아카이브 모드, 모든 파일 속성 보존 및 심볼릭 링크 등 처리
# -v: 'rsync'가 수행하는 동작에 대해 보다 상세한 정보를 출력
# --delete: 대상 폴더에서 원본에 없는 파일은 삭제
rsync -av --delete dist/ /home/ubuntu/$PROJECT_NAME/
echo ">>> 빌드 정적파일 복사 완료: $TARGET_DIR"
echo ""


echo -e "\n<<<<<<<<<< Frontend Deploy Complete Successfully >>>>>>>>>>\n"
