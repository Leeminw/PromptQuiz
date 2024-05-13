#!/bin/bash

# 모델 파일 경로 설정
MODEL_DIR="models"
MODEL_FILE="cc.ko.300.bin"
MODEL_PATH="${MODEL_DIR}/${MODEL_FILE}"

MODEL_GZ="${MODEL_FILE}.gz"
MODEL_GZ_PATH="${MODEL_DIR}/${MODEL_GZ}"
MODEL_URL="https://dl.fbaipublicfiles.com/fasttext/vectors-crawl/cc.ko.300.bin.gz"

# models 디렉토리 생성 (없을 경우)
sudo mkdir -p $MODEL_DIR

# 모델 파일 다운로드 및 압축 해제
if [ ! -f $MODEL_PATH ]; then
    if [ ! -f $MODEL_GZ_PATH ]; then
        echo "모델 압축 파일을 다운로드 중..."
        sudo wget -O $MODEL_GZ_PATH $MODEL_URL
    else
        echo "압축 파일이 이미 다운로드되어 있습니다."
    fi

    echo "압축을 해제 중..."
    gunzip -f $MODEL_GZ_PATH
    echo "압축 해제 완료."
else
    echo "모델 파일이 이미 존재합니다."
fi
