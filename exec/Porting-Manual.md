# PQ(Prompt Quiz) 포팅 매뉴얼

## 목차

- [프로젝트 개요](#프로젝트-개요)
- [시스템 요구 사항](#시스템-요구-사항)
  - [원본 시스템(Linux)](#원본-시스템linux)
  - [타겟 시스템(Linux)](#타겟-시스템linux)
- [포팅 전 준비사항](#포팅-전-준비사항)
- [프론트엔드 포팅 절차 (React)](#프론트엔드-포팅-절차-react)
  - [단계 1: Dockerfile 작성](#단계-1-dockerfile-작성)
  - [단계 2: Shell Script 작성](#단계-2-shell-script-작성)
  - [단계 3: .env 작성](#단계-3-env-작성)
- [백엔드 포팅 절차](#백엔드-포팅-절차)
  - [Spring Boot](#spring-boot)
    - [단계 1: Dockerfile 작성](#단계-1-dockerfile-작성-1)
    - [단계 2: Shell Script 작성](#단계-2-shell-script-작성-1)
    - [단계 3: application.properties 작성](#단계-3-applicationproperties-작성)
  - [FastAPI](#fastapi)
    - [단계 1: Dockerfile 작성](#단계-1-dockerfile-작성-2)
    - [단계 2: Shell Script 작성](#단계-2-shell-script-작성-2)
    - [단계 3: 환경 설정 파일 작성](#단계-3-환경-설정-파일-작성)
- [데이터베이스 포팅 절차](#데이터베이스-포팅-절차)
  - [단계 1: MySQL 설정](#단계-1-mysql-설정)
  - [단계 2: Redis 설정](#단계-2-redis-설정)
  - [단계 3: InfluxDB 설정](#단계-3-influxdb-설정)
- [배포 및 인프라 설정 절차](#배포-및-인프라-설정-절차)
  - [단계 0: 서버 환경 설정](#단계-0-서버-환경-설정)
  - [단계 1: Docker 환경 구성](#단계-1-docker-환경-구성)
  - [단계 2: Jenkins 설정](#단계-2-jenkins-설정)
  - [단계 3: Nginx 설정](#단계-3-nginx-설정)
- [모니터링 포팅 절차](#모니터링-포팅-절차)
  - [단계 1: Node Exporter 설정](#단계-1-node-exporter-설정)
  - [단계 2: Grafana 설정](#단계-2-grafana-설정)
  - [단계 3: Prometheus 설정](#단계-3-prometheus-설정)
- [Stable Diffusion 포팅 절차](#stable-diffusion-포팅-절차)
  - [단계 1: Github Pull](#단계-1-github-pull)
  - [단계 2: Models 파일 설정](#단계-2-models-파일-설정)
  - [단계 3: Conda 환경 구성](#단계-3-conda-환경-구성)
  - [단계 4: Command Line Arguments 설정](#단계-4-command-line-arguments-설정)
  - [단계 5: Stable Diffusion 실행](#단계-5-stable-diffusion-실행)
- [CI/CD 파이프라인 설정 (Jenkins)](#cicd-파이프라인-설정-jenkins)
- [문제 해결 가이드](#문제-해결-가이드)
- [참고 자료](#참고-자료)
- [FAQ](#faq)

## 프로젝트 개요

**프로젝트명:** PQ (Prompt Quiz)

현대 사회에서 생성형 AI의 사용이 늘어나면서 프롬프트 입력에 따라 다양한 결과물이 생성됩니다. 이에 따라 프롬프트 엔지니어링 교육이 중요해지고 있으며, 프롬프트의 미묘한 차이를 이해하고 조정하는 기술이 필요합니다.

PQ(Prompt Quiz) 프로젝트는 이러한 필요성에 따라, 개발에 익숙하지 않은 사람들도 프롬프트의 세부적인 차이를 쉽게 이해할 수 있도록 돕는 게임 기반 학습 플랫폼을 제공하고자 기획되었습니다. 사용자들은 실시간으로 함께 게임을 하며 생성형 AI가 만든 이미지의 프롬프트를 맞추는 기능을 즐길 수 있습니다.

게임은 객관식과 주관식 두 가지 유형으로 나뉘며, 주관식 게임에서는 시간이 지남에 따라 초성 힌트가 제공되어 정답을 유추하기 쉽게 합니다. 각 입력마다 유사도가 제공되어 정확도를 파악할 수 있으며, 사용자들은 게임에서 얻은 점수로 랭킹을 확인할 수 있습니다. 또한 실시간 채팅을 통해 다른 사용자들과 소통하며 게임을 즐길 수 있습니다.

<div align="right">

[[맨 위로](#)]

</div>

## 시스템 요구 사항

### 원본 시스템 (Linux)

- Ubuntu 20.04 LTS 또는 그 이상
- Docker 26.1.0 또는 그 이상
- Git 2.25.1 또는 그 이상

### 타겟 시스템 (Linux)

- Ubuntu 20.04 LTS 또는 그 이상
- Docker 26.1.0 또는 그 이상
- Git 2.25.1 또는 그 이상

<div align="right">

[[맨 위로](#)]

</div>

## 포팅 전 준비사항

1. **필요한 소프트웨어 설치:**

   - Git
   - Docker
   - Node.js 및 npm (프론트엔드 개발을 위해 필요)
   - OpenJDK 17 이상 (백엔드 Spring Boot 개발을 위해 필요)
   - Python 3.10.6 및 Conda (Stable Diffusion 환경 구성을 위해 필요)

<div align="right">

[[맨 위로](#)]

</div>

2. **환경 변수 설정(FE):**

   - 프로젝트의 `frontend` 디렉토리에 `.env` 파일을 생성하고 필요한 환경 변수를 설정합니다.
   - 예시:
     ```bash
     # .env 파일 예시
     REACT_APP_SERVER=https://your_server_url
     REACT_APP_SOCKET=https://your_socket_server_url/ws
     KAKAO_API_JAVASCRIPT_KEY=your_kakao_api_javascript_key
     ```

<div align="right">

[[맨 위로](#)]

</div>

3. **환경 변수 설정(BE):**

   - 프로젝트의 `backend` 디렉토리에 `application.properties` 파일을 생성하고  
     필요한 환경 변수를 설정합니다.
   - 예시:

     ```bash
     # application.properties 파일 예시
     spring.application.name=APM

     # GPU Server IP
     cloud.aws.gpu.server.ip=your_gpu_server_ip:port

     # MySQL Server Settings
     spring.datasource.url=jdbc:mysql://your_mysql_server_url:port/database_name?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useUnicode=true
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

     # MySQL Develop Settings
     #spring.datasource.url=jdbc:mysql://localhost:3306/apm?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8& useUnicode=true
     #spring.datasource.username=root
     #spring.datasource.password=1234
     #spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

     ### JPA/Hibernate Settings
     spring.jpa.show-sql=true
     spring.jpa.open-in-view=false
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.properties.hibernate.format_sql=true
     spring.jpa.properties.hibernate.use_sql_comments=true
     spring.jpa.properties.hibernate.jdbc.time_zone=Asia/Seoul

     # Redis Settings
     spring.data.redis.port=6379
     spring.data.redis.host=your_redis_host
     spring.data.redis.password=your_redis_password

     # InfluxDB Settings
     influx.url=http://your_influx_url:port
     influx.token=your_influx_token
     influx.org=your_influx_org
     influx.bucket=your_influx_bucket

     # JWT Settings
     jwt.secret=your_jwt_secret
     jwt.accessExpTime=1800000
     jwt.refreshExpTime=7200000

     # S3 Credential Settings
     cloud.aws.credentials.access-key=your_aws_access_key
     cloud.aws.credentials.secret-key=your_aws_secret_key
     cloud.aws.region.static=ap-northeast-2
     cloud.aws.bucket.name=your_s3_bucket_name

     # File Upload Size Settings
     spring.servlet.multipart.maxFileSize=10MB
     spring.servlet.multipart.maxRequestSize=50MB

     ```

<div align="right">

[[맨 위로](#)]

</div>

## 프론트엔드 포팅 절차 (React)

### 단계 1: Dockerfile 작성

프로젝트의 `frontend` 디렉토리에 `Dockerfile`을 작성합니다.

```dockerfile
# Node.js 공식 이미지 사용. 버전 20.12.2, 경량화된 Alpine Linux 기반
FROM node:20.12.2-alpine

# 작업 디렉토리 설정. 컨테이너 내 앱의 기본 경로
WORKDIR /home/app

# 현재 디렉토리의 package.json과 package-lock.json 파일이 존재한다면
# 컨테이너의 작업 디렉토리로 package.json과 package-lock.json 복사
COPY package*.json ./

# package.json에 명시된 애플리케이션 의존성 설치
# package-lock.json이 있을 경우 더 빠르게 설치 가능
RUN npm install

# 현재 디렉토리의 모든 파일을 컨테이너의 작업 디렉토리로 복사
COPY . .

# React 애플리케이션 빌드
RUN npm run build

# React 애플리케이션 실행
CMD ["npm", "start"]

# 3000번 포트 개방
EXPOSE 3000
```

<div align="right">

[[맨 위로](#)]

</div>

### 단계 2: Shell Script 작성

프로젝트의 `frontend` 디렉토리에 `build-frontend.sh`와 `deploy-frontend.sh` 파일을 작성합니다.

`build-frontend.sh`

```bash
#!/bin/bash

IMAGE_NAME="server/frontend"
IMAGE_ID=$(docker images -q $IMAGE_NAME)

CONTAINER_NAME="server-frontend"
CONTAINER_ID=$(docker ps -aqf "name=$CONTAINER_NAME")

echo -e "\n<<<<<<<<<< Frontend Build Process Start >>>>>>>>>>\n"

echo ">>> CURRENT DOCKER INFORMATION:"
echo ">>> DOCKER IMAGE NAME: $IMAGE_NAME"
echo ">>> DOCKER IMAGE ID: $IMAGE_ID"
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

# Remove Existing Docker Image
echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 확인."
    echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 시작..."
    docker rmi -f $IMAGE_ID || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 실패."
        exit 1
    }
    echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 완료."
fi
echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 여부 검사 완료."
echo ""

# Build Docker Image
echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 시작..."
docker build -t $IMAGE_NAME . || {
    echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 실패."
    exit 1
}
echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 완료."
echo ""

echo -e "\n<<<<<<<<<< Frontend Build Complete Successfully >>>>>>>>>>\n"

```

<div align="right">

[[맨 위로](#)]

</div>

`deploy-frontend.sh`

```bash
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

# Run Docker Container
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 시작..."
docker run -d -p 3000:3000 \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 실행 실패."
        exit 1
}
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 완료."
echo ""

# Copy Static Build Directory from Docker Container to Host
echo ">>> 빌드 정적파일 복사 시작..."
mkdir -p /var/jenkins_home/application
docker cp $CONTAINER_NAME:/home/app/dist /var/jenkins_home/application || {
        echo ">>> 빌드 정적파일 복사 실패."
        exit 1
}
echo ">>> 빌드 정적파일 복사 완료: /var/jenkins_home/application/dist"
echo ""

echo -e "\n<<<<<<<<<< Frontend Deploy Complete Successfully >>>>>>>>>>\n"

```

<div align="right">

[[맨 위로](#)]

</div>

### 단계 3: .env 작성

프로젝트의 `frontend` 디렉토리에 `.env` 파일을 작성합니다.

```bash
# .env 파일 예시
REACT_APP_SERVER=https://your_server_url
REACT_APP_SOCKET=https://your_socket_server_url/ws
KAKAO_API_JAVASCRIPT_KEY=your_kakao_api_javascript_key

```

<div align="right">

[[맨 위로](#)]

</div>

## 백엔드 포팅 절차

### Spring Boot

#### 단계 1: Dockerfile 작성

프로젝트의 `backend` 디렉토리에 `Dockerfile`을 작성합니다.

```dockerfile
# OpenJDK 17을 포함하는 경량화된 Alpine Linux 베이스 이미지 사용
FROM openjdk:17-jdk-buster

# 컨테이너 내부의 작업 디렉토리를 /home/app로 설정
WORKDIR /home/app

# 호스트 시스템의 SpringBoot 애플리케이션 JAR 파일을 컨테이너 내부 작업 디렉토리로 복사
COPY build/libs/*.jar app.jar

RUN apt-get update && \
    apt-get install -y fonts-noto-cjk && \
    apt-get install -y fonts-nanum-extra && \
    rm -rf /var/lib/apt/lists/* && \
    fc-cache -fv

# 컨테이너가 시작될 때 실행될 명령어 정의, 작업 디렉토리 /home/app/app.jar 파일 실행
ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "./app.jar"]
# ENTRYPOINT ["java", "-Djava.library.path=/home/backend/lib/", "-jar", "./app.jar"]

# 컨테이너의 8080 포트를 외부로 노출
EXPOSE 8080
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 2: Shell Script 작성

프로젝트의 `backend` 디렉토리에 `build-backend.sh`와 `deploy-backend.sh`파일을 작성합니다.

`build-backend.sh`

```bash
#!/bin/bash

IMAGE_NAME="server/backend"
IMAGE_ID=$(docker images -q $IMAGE_NAME)

CONTAINER_NAME="server-backend"
CONTAINER_ID=$(docker ps -aqf "name=$CONTAINER_NAME")

echo -e "\n<<<<<<<<<< Backend Build Start >>>>>>>>>>\n"

echo ">>> CURRENT DOCKER INFORMATION:"
echo ">>> DOCKER IMAGE NAME: $IMAGE_NAME"
echo ">>> DOCKER IMAGE ID: $IMAGE_ID"
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

# Remove Existing Docker Image
echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 확인."
    echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 시작..."
    docker rmi -f $IMAGE_ID || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 실패."
        exit 1
    }
    echo ">>> DOCKER IMAGE $IMAGE_NAME 삭제 완료."
fi
echo ">>> DOCKER IMAGE $IMAGE_NAME 존재 여부 검사 완료."
echo ""

# Build Docker Image
echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 시작..."
docker build -t $IMAGE_NAME . || {
    echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 실패."
    exit 1
}
echo ">>> DOCKER IMAGE $IMAGE_NAME 빌드 완료."
echo ""

echo -e "\n<<<<<<<<<< Backend Build Complete Successfully >>>>>>>>>>\n"

```

<div align="right">

[[맨 위로](#)]

</div>

`deploy-backend.sh`

```bash
#!/bin/bash

IMAGE_NAME="server/backend"
CONTAINER_NAME="server-backend"
CONTAINER_ID=$(docker ps -aqf "name=$CONTAINER_NAME")

echo -e "\n<<<<<<<<<< Backend Deploy Process Start >>>>>>>>>>\n"

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

# Run Docker Container
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 시작..."
docker run -d -p 8080:8080 \
    -v /home/ubuntu/uploads:/upload \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 실행 실패."
        exit 1
}
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 완료."
echo ""

echo -e "\n<<<<<<<<<< Backend Deploy Complete Successfully >>>>>>>>>>\n"

```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 3: application.properties 작성

프로젝트의 `backend` 디렉토리에 `application.properties` 파일을 작성하고 필요한 환경 변수를 설정합니다.

```bash
# application.properties 파일 예시
spring.application.name=APM

# GPU Server IP
cloud.aws.gpu.server.ip=your_gpu_server_ip:port

# MySQL Server Settings
spring.datasource.url=jdbc:mysql://your_mysql_server_url:port/database_name?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8&useUnicode=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# MySQL Develop Settings
#spring.datasource.url=jdbc:mysql://localhost:3306/apm?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8& useUnicode=true
#spring.datasource.username=root
#spring.datasource.password=1234
#spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

### JPA/Hibernate Settings
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.jdbc.time_zone=Asia/Seoul

# Redis Settings
spring.data.redis.port=6379
spring.data.redis.host=your_redis_host
spring.data.redis.password=your_redis_password

# InfluxDB Settings
influx.url=http://your_influx_url:port
influx.token=your_influx_token
influx.org=your_influx_org
influx.bucket=your_influx_bucket

# JWT Settings
jwt.secret=your_jwt_secret
jwt.accessExpTime=1800000
jwt.refreshExpTime=7200000

# S3 Credential Settings
cloud.aws.credentials.access-key=your_aws_access_key
cloud.aws.credentials.secret-key=your_aws_secret_key
cloud.aws.region.static=ap-northeast-2
cloud.aws.bucket.name=your_s3_bucket_name

# File Upload Size Settings
spring.servlet.multipart.maxFileSize=10MB
spring.servlet.multipart.maxRequestSize=50MB

```

<div align="right">

[[맨 위로](#)]

</div>

### FastAPI

#### 단계 1: Dockerfile 작성

프로젝트의 `pycosine` 디렉토리에 `Dockerfile`을 작성합니다.

```dockerfile
# 베이스 이미지
FROM python:3.10.6-slim

# 필요한 패키지 설치
RUN apt-get update && \
    (dpkg -l | grep -qw wget || apt-get install -y wget) && \
    (dpkg -l | grep -qw gzip || apt-get install -y gzip)

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사 및 설치
COPY requirements.txt .
RUN pip install -r requirements.txt

# 모델 다운로드 쉘 스크립트 복사 및 실행
COPY download-model.sh .
RUN chmod +x download-model.sh && ./download-model.sh

# 다운로드된 파일 확인 (디버깅 용도)
RUN ls -l models && ls -l models/cc.ko.300.vec

# 나머지 애플리케이션 파일 복사
COPY . /app

# 포트 설정
EXPOSE 8000

# 애플리케이션 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]

```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 2: Shell Script 작성

프로젝트의 `pycosine` 디렉토리에 `build-pycosine.sh`와 `deploy-pycosine.sh`파일을 작성합니다.

`build-pycosine.sh`

```bash
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

```

<div align="right">

[[맨 위로](#)]

</div>

`deploy-pycosine.sh`

```bash
#!/bin/bash

IMAGE_NAME="server/pycosine"
CONTAINER_NAME="server-pycosine"
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")


echo -e "\n<<<<<<<<<< PyCosine Deploy Process Start >>>>>>>>>>\n"


echo ">>> CURRENT DOCKER INFORMATION:"
echo ">>> DOCKER CONTAINER NAME: $CONTAINER_NAME"
echo ">>> DOCKER CONTAINER ID: $CONTAINER_ID"
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


## Run Docker Container
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 시작..."
sudo docker run -d -p 8000:8000 \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> DOCKER IMAGE $IMAGE_NAME 실행 실패."
        exit 1
}
echo ">>> DOCKER CONTAINER $CONTAINER_NAME 실행 완료."
echo ""


echo -e "\n<<<<<<<<<< PyCosine Deploy Complete Successfully >>>>>>>>>>\n"

```

<div align="right">

[[맨 위로](#)]

</div>
