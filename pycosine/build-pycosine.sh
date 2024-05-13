#!/bin/bash

IMAGE_NAME="server/pycosine"
IMAGE_ID=$(sudo docker images -q $IMAGE_NAME)

CONTAINER_NAME="server-pycosine"
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")


echo -e "\n<<<<<<<<<< PyCosine Build Start >>>>>>>>>>\n"


echo ">>> CURRENT DOCKER INFORMATION:"
echo ">>> DOCKER IMAGE NAME: $IMAGE_NAME"
echo ">>> DOCKER IMAGE ID: $IMAGE_ID"
echo ""


# Stop & Remove Existing Container
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 존재 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 존재 확인."
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 중지 시작..."
    sudo docker stop $CONTAINER_ID || {
        echo ">>> DOCKER CONTAINER $CONTAINER_NAME 중지 실패."
        exit 1
    }
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 중지 완료."


    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 삭제 시작..."
    sudo docker rm -f $CONTAINER_ID || {
        echo ">>> DOCKER CONTAINER $CONTAINER_NAME 삭제 실패."
        exit 1
    }
    echo ">>> DOCKER CONTAINER $CONTAINER_NAME 삭제 완료."
fi
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 존재 여부 검사 완료."
echo ""


# Remove Existing Docker Image
echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 확인."
    echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 시작..."
    sudo docker rmi -f $IMAGE_ID || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 실패."
        exit 1
    }
    echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 완료."
fi
echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 여부 검사 완료."
echo ""


## Build Docker Image
echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 시작..."
sudo docker build -t $IMAGE_NAME . || {
    echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 실패."
    exit 1
}
echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 완료."
echo ""


echo -e "\n<<<<<<<<<< PyCosine Build Complete Successfully >>>>>>>>>>\n"
