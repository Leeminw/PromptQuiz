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

# 대상 디렉토리 내의 dist 폴더가 존재하는지 확인 후 삭제
if [ -d "$TARGET_DIR/dist" ]; then
    echo ">>> 대상 폴더 내 dist 폴더가 이미 존재합니다. 삭제 후 복사를 진행합니다."
    rm -rf "$TARGET_DIR/dist"
fi

# dist 디렉토리를 $TARGET_DIR 디렉토리로 복사
# -R: 재귀적으로 복사, -f: 강제로 덮어쓰기
cp -Rf dist/ $TARGET_DIR/
echo ">>> 빌드 정적파일 복사 완료: $TARGET_DIR"
echo ""


echo -e "\n<<<<<<<<<< Frontend Deploy Complete Successfully >>>>>>>>>>\n"
