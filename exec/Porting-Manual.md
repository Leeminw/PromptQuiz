# PQ(Prompt Quiz) 포팅 매뉴얼

## 목차

- [프로젝트 개요](#프로젝트-개요)
- [시스템 요구 사항](#시스템-요구-사항)
  - [원본 시스템(Linux)](#원본-시스템-linux)
  - [타겟 시스템(Linux)](#타겟-시스템-linux)
- [포팅 전 준비사항](#포팅-전-준비사항)
- [프론트엔드 포팅 절차 (React)](#프론트엔드-포팅-절차-react)
  - [단계 1: Dockerfile 작성](#단계-1-dockerfile-작성)
  - [단계 2: Shell Script 작성](#단계-2-shell-script-작성)
  - [단계 3: .env 작성](#단계-3-env-작성)
- [백엔드 포팅 절차](#백엔드-포팅-절차)
  - [1. Spring Boot](#1-spring-boot)
    - [단계 1: Dockerfile 작성](#eb8ba8eab384-1-dockerfile-ec9e91ec84b1-1)
    - [단계 2: Shell Script 작성](#eb8ba8eab384-2-shell-script-ec9e91ec84b1-1)
    - [단계 3: application.properties 작성](#단계-3-applicationproperties-작성)
  - [2. FastAPI](#2-fastapi)
    - [단계 1: Dockerfile 작성](#eb8ba8eab384-1-dockerfile-ec9e91ec84b1-2)
    - [단계 2: Shell Script 작성](#eb8ba8eab384-2-shell-script-ec9e91ec84b1-2)
- [데이터베이스 포팅 절차](#데이터베이스-포팅-절차)
  - [1. MySQL 설치](#1-mysql-설치)
    - [단계 1: MySQL 설치 스크립트 작성](#단계-1-mysql-설치-스크립트-작성)
    - [단계 2: MySQL 설치 스크립트 실행](#단계-2-mysql-설치-스크립트-실행)
    - [단계 3: MySQL 삭제 스크립트 작성 (선택사항)](#단계-3-mysql-삭제-스크립트-작성-선택사항)
    - [단계 4: MySQL 삭제 스크립트 실행 (선택사항)](#단계-4-mysql-삭제-스크립트-실행-선택사항)
  - [2. Redis 설치](#2-redis-설치)
    - [단계 1: Redis 설치 스크립트 작성](#단계-1-redis-설치-스크립트-작성)
    - [단계 2: Redis 설치 스크립트 실행](#단계-2-redis-설치-스크립트-실행)
  - [3. InfluxDB 설치](#3-influxdb-설치)
    - [단계 1: InfluxDB 설치 스크립트 작성](#단계-1-influxdb-설치-스크립트-작성)
    - [단계 2: InfluxDB 설치 스크립트 실행](#단계-2-influxdb-설치-스크립트-실행)
- [배포 및 인프라 포팅 절차](#배포-및-인프라-포팅-절차)
  - [1. 서버 환경 설정](#1-서버-환경-설정)
    - [단계 1: 서버 시간 설정](#단계-1-서버-시간-설정)
    - [단계 2: 미러 서버 변경](#단계-2-미러-서버-변경)
    - [단계 3: 패키지 업데이트](#단계-3-패키지-업데이트)
    - [단계 4: 가상 메모리 할당](#단계-4-가상-메모리-할당)
  - [2. Docker 환경 구성](#2-docker-환경-구성)
    - [단계 1: Docker apt repository 설정](#단계-1-docker-apt-repository-설정)
    - [단계 2: Docker 패키지 설치](#단계-2-docker-패키지-설치)
    - [단계 3: Docker 설치 확인](#단계-3-docker-설치-확인)
  - [3. Jenkins 설정](#3-jenkins-설정)
    - [단계 1: Dockerfile 작성](#단계-1-dockerfile-작성)
    - [단계 2: Shell Script 작성](#단계-2-shell-script-작성)
    - [단계 3: Docker Jenkins 설치](#단계-3-docker-jenkins-설치)
  - [4. Nginx 설정](#4-nginx-설정)
    - [단계 1: Nginx 설치](#단계-1-nginx-설치)
    - [단계 2: 방화벽 설정](#단계-2-방화벽-설정)
    - [단계 3: Nginx 설정 파일 편집](#단계-3-nginx-설정-파일-편집)
    - [단계 4: Nginx 서비스 시작](#단계-4-nginx-서비스-시작)
    - [단계 5: LetsEncrypt & Certbot 설치](#단계-5-letsencrypt--certbot-설치)
    - [단계 6: Certbot을 사용하여 SSL 인증서 설정](#단계-6-certbot을-사용하여-ssl-인증서-설정)
    - [단계 7: NginX 설정](#단계-7-nginx-설정)
- [모니터링 포팅 절차](#모니터링-포팅-절차)
  - [1. Grafana 설정](#1-grafana-설정)
    - [단계 1: Grafana 설치 스크립트 작성](#단계-1-grafana-설치-스크립트-작성)
    - [단계 2: Grafana 설치 스크립트 실행](#단계-2-grafana-설치-스크립트-실행)
  - [2. Prometheus 설정](#2-prometheus-설정)
    - [단계 1: Prometheus 설치 스크립트 작성](#단계-1-prometheus-설치-스크립트-작성)
    - [단계 2: Prometheus 설치 스크립트 실행](#단계-2-prometheus-설치-스크립트-실행)
  - [3. Node Exporter 설정](#3-node-exporter-설정)
    - [단계 1: Node Exporter 설치 스크립트 작성](#단계-1-node-exporter-설치-스크립트-작성)
    - [단계 2: Node Exporter 설치 스크립트 실행](#단계-2-node-exporter-설치-스크립트-실행)
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

### 1. Spring Boot

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

### 2. FastAPI

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

## 데이터베이스 포팅 절차

### 1. MySQL 설치

MySQL 데이터베이스를 설치하고 설정하는 절차를 설명합니다.  
아래의 스크립트를 사용하여 MySQL을 설치하고 필요한 설정을 수행할 수 있습니다.

#### 단계 1: MySQL 설치 스크립트 작성

다음 내용을 `install-mysql.sh` 파일에 작성합니다:

```bash
#!/bin/bash

# 변수명 설정
ROOT_PASSWORD='your_root_password'
NEW_USERNAME='your_new_username'
NEW_PASSWORD='your_new_password'

# 방화벽 설정
if sudo ufw status | grep -qw inactive; then
    echo "방화벽이 비활성화되어 있습니다. 방화벽을 활성화합니다."
    sudo ufw enable
fi
sudo ufw allow 3306

# MySQL 설치
echo "MySQL 설치를 시작합니다..."
sudo apt-get update
sudo apt-get install -y mysql-server

# MySQL 서비스 시작
sudo systemctl start mysql
sudo systemctl enable mysql

# 루트 비밀번호 설정 및 보안 설치 실행
sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '${ROOT_PASSWORD}';"
sudo mysql -e "FLUSH PRIVILEGES;"

# 사용자 추가 스크립트
sudo mysql -e "CREATE USER '${NEW_USERNAME}'@'%' IDENTIFIED BY '${NEW_PASSWORD}';"
sudo mysql -e "GRANT ALL PRIVILEGES ON *.* TO '${NEW_USERNAME}'@'%' WITH GRANT OPTION;"
sudo mysql -e "FLUSH PRIVILEGES;"

# MySQL 설정 파일에서 bind-address 값을 0.0.0.0으로 변경하여 어느 주소에서든 접근 가능하도록 설정
sudo sed -i '/bind-address/s/^#//g' /etc/mysql/mysql.conf.d/mysqld.cnf
sudo sed -i 's/127.0.0.1/0.0.0.0/g' /etc/mysql/mysql.conf.d/mysqld.cnf

# MySQL 서비스 재시작
sudo systemctl restart mysql

echo "MySQL 설치 및 사용자 추가가 완료되었습니다."
```

#### 단계 2: MySQL 설치 스크립트 실행

`install-mysql.sh` 스크립트를 실행하여 MySQL을 설치하고 설정합니다.

```bash
$ chmod +x install-mysql.sh
$ ./install_mysql.sh
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 3: MySQL 삭제 스크립트 작성 (선택사항)

다음 내용을 `uninstall-mysql.sh` 파일에 작성하여 MySQL을 완전히 삭제할 수 있습니다.

```bash
#!/bin/bash

# 사용자에게 MySQL 삭제 확인 메시지 표시
echo "MySQL 클린 삭제를 시작합니다. 모든 MySQL 데이터가 제거됩니다."
read -rp "정말로 MySQL을 클린 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다. (y/n): " confirm

if [[ $confirm == [yY] || $confirm == [yY][eE][sS] ]]; then
    echo "MySQL 클린 삭제를 시작합니다..."

    # MySQL 서비스 중지
    sudo systemctl stop mysql

    # MySQL 패키지 및 관련 패키지 제거
    sudo apt-get remove --purge -y mysql-server mysql-client mysql-common mysql-server-core-* mysql-client-core-*
    sudo apt-get autoremove -y
    sudo apt-get autoclean -y

    # MySQL 설정 파일 및 데이터베이스 디렉토리 삭제
    sudo rm -rf /etc/mysql /var/lib/mysql

    # MySQL 로그 파일 삭제
    sudo rm -rf /var/log/mysql

    # MySQL 사용자 및 그룹 삭제 (선택적)
    sudo deluser mysql
    sudo delgroup mysql

    echo "MySQL이 시스템에서 완전히 제거되었습니다."
else
    echo "MySQL 클린 삭제가 취소되었습니다."
fi
```

#### 단계 4: MySQL 삭제 스크립트 실행 (선택사항)

`uninstall-mysql.sh` 스크립트를 실행하여 MySQL을 완전히 삭제합니다.

```bash
$ chmod +x uninstall_mysql.sh
$ ./uninstall_mysql.sh
```

<div align="right">

[[맨 위로](#)]

</div>

### 2. Redis 설치

Redis를 설치하고 설정하는 절차를 설명합니다.  
아래의 스크립트를 사용하여 Redis를 설치하고 필요한 설정을 수행할 수 있습니다.

#### 단계 1: Redis 설치 스크립트 작성

다음 내용을 `install-redis.sh` 파일에 작성합니다:

```bash
#!/bin/bash

# 변수명 설정
REDIS_PASSWORD='your_redis_password'
IMAGE_NAME="redis:latest"
CONTAINER_NAME="server-redis"

IMAGE_ID=$(sudo docker images -q $IMAGE_NAME)
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")

echo ">>> CURRENT DOCKER INFORMATION:"
echo "$IMAGE_NAME IMAGE_ID: $IMAGE_ID"
echo "$CONTAINER_NAME CONTAINER_ID: $CONTAINER_ID"
echo ""

# 방화벽 설정
if sudo ufw status | grep -qw inactive; then
    echo "방화벽이 비활성화되어 있습니다. 방화벽을 활성화합니다."
    sudo ufw enable
fi
sudo ufw allow 6379

# Stop & Remove Existing Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 시작...\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 시작..."
    sudo docker stop $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 중지 실패."
        exit 1
    }
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 완료.\n"

    echo ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 시작..."
    sudo docker rm $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 삭제 실패."
        exit 1
    }
    echo -e ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 완료.\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 완료."
fi
echo -e ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 완료.\n"

# Remove Existing Docker Image
echo ">>> $IMAGE_NAME 이미지 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 시작..."
    sudo docker rmi $IMAGE_ID || {
        echo ">>> 기존 $IMAGE_NAME 이미지 삭제 실패."
        exit 1
    }
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 완료."
fi
echo -e ">>> $IMAGE_NAME 이미지 존재 여부 검사 완료.\n"

# Run Docker Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 시작..."
sudo docker run -d -p 6379:6379 \
    -e REDIS_PASSWORD="$REDIS_PASSWORD" \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> $CONTAINER_NAME 컨테이너 실행 실패."
        exit 1
    }
echo ">>> $CONTAINER_NAME 컨테이너 실행 완료."

echo ">>> Redis 설치가 완료되었습니다."
```

#### 단계 2: Redis 설치 스크립트 실행

`install-redis.sh` 스크립트를 실행하여 Redis를 설치하고 설정합니다.

```bash
chmod +x install-redis.sh
./install-redis.sh
```

<div align="right">

[[맨 위로](#)]

</div>

### 3. InfluxDB 설치

InfluxDB를 설치하고 설정하는 절차를 설명합니다. 아래의 스크립트를 사용하여 InfluxDB를 설치하고 필요한 설정을 수행할 수 있습니다.

#### 단계 1: InfluxDB 설치 스크립트 작성

다음 내용을 `install-influxdb.sh` 파일에 작성합니다:

```bash
#!/bin/bash
set -e

# 변수값 설정
PORT=${INFLUXDB_PORT:-8086}
IMAGE_NAME=${INFLUXDB_IMAGE_NAME:-"influxdb:2"}
CONTAINER_NAME=${INFLUXDB_CONTAINER_NAME:-"server-influxdb2"}

VOLUME_DATA=${INFLUXDB_VOLUME_DATA:-"influxdb2-data"}
VOLUME_CONFIG=${INFLUXDB_VOLUME_CONFIG:-"influxdb2-config"}

USERNAME=${INFLUXDB_USERNAME:-"admin"}
PASSWORD=${INFLUXDB_PASSWORD:-"admin-influxdb"}

ORG_NAME=${INFLUXDB_ORG_NAME:-"my-org"}
BUCKET_NAME=${INFLUXDB_BUCKET_NAME:-"my-bucket"}

IMAGE_ID=$(docker images -q ${IMAGE_NAME})
CONTAINER_ID=$(docker ps -aqf name=${CONTAINER_NAME})

# 동적으로 사각형 틀의 너비를 설정하는 함수 (한글 고려)
print_box() {
    local max_length=75
    local content=("$@")

    # 가장 긴 문자열의 길이 탐색 (한글 한 글자당 2개의 문자를 사용하는 것으로 계산)
    for line in "${content[@]}"; do
        local length=0
        for (( i=0; i<${#line}; i++ )); do
            char="${line:i:1}"
            if [[ "$char" =~ [가-힣] ]]; then
                (( length+=2 ))
            else
                (( length+=1 ))
            fi
        done
        (( length > max_length )) && max_length=$length
    done
    local box_width=$(( max_length + 4 ))

    # 상자의 내용 출력
    echo "$(printf '*%.0s' $(seq 1 $box_width))"
    for line in "${content[@]}"; do
        if [[ "$line" == "===" ]]; then
            printf "* %s *\n" "$(printf '=%.0s' $(seq 1 $max_length))"
        else
            local length=0
            for (( i=0; i<${#line}; i++ )); do
                char="${line:i:1}"
                if [[ "$char" =~ [가-힣] ]]; then
                    (( length+=2 ))
                else
                    (( length+=1 ))
                fi
            done
            local padding=$(( max_length - length ))
            printf "* %s%*s *\n" "$line" "$padding" ""
        fi
    done
    echo "$(printf '*%.0s' $(seq 1 $box_width))"
}

# 관리자 권한 확인
if [ "$EUID" -ne 0 ]; then
    print_box "WARN: INSUFFICIENT PRIVILEGES DETECTED" \
        "INFO: RUNNING WITH PRIVILEGES '$(whoami)'" \
        "===" \
        "This script requires root privileges to execute properly." \
        "Without root privileges, this script may result in errors." \
        "" \
        "To ensure stable execution," \
        "Please re-run this script as root or with sudo." \
        "" \
        "If you encounter any problems during execution," \
        "Try following commands below:" \
        "    \$ sudo $0 $@" \
        ""
else
    print_box "INFO: RUNNING WITH PRIVILEGES '$(whoami)'" \
        "EXECUTION: \$ $0 $@ (with root privileges)"
fi
echo ""

# Print Current Docker Information
print_box "INFO: CURRENT DOCKER INFORMATION" \
          "===" \
          "INFO: DOCKER IMAGE INFORMATION" \
          "- IMAGE ID: ${IMAGE_ID}" \
          "- IMAGE NAME: ${IMAGE_NAME}" \
          "===" \
          "INFO: DOCKER CONTAINER INFORMATION" \
          "- CONTAINER ID: ${CONTAINER_ID}" \
          "- CONTAINER NAME: ${CONTAINER_NAME}"
echo ""

# UFW 상태 확인 및 활성화 여부 물어보기
print_box "INFO: UFW 방화벽 설정 시작..."
if ufw status | grep -q "Status: inactive"; then
    read -p ">>> UFW 방화벽이 비활성화 상태입니다. 활성화하시겠습니까? (y/n): " yn
    case $yn in
        [Yy]* ) echo ">>> UFW 방화벽을 활성화합니다..."
            ufw enable || {
                echo ">>> UFW 방화벽 활성화 실패."
                exit 1
            }
            echo ">>> UFW 방화벽이 활성화되었습니다.";;
        [Nn]* ) echo ">>> UFW 방화벽 활성화가 취소되었습니다."
            exit 1;;
        * ) echo ">>> 잘못된 입력입니다. (y/n만 입력 가능)"
            exit 1;;
    esac
fi

# 포트 규칙 추가 여부 물어보기
if ! ufw status | grep -q "${PORT}/tcp"; then
    read -p ">>> 포트 ${PORT}에 대한 UFW 규칙을 추가하시겠습니까? (y/n): " yn
    case $yn in
        [Yy]* ) echo ">>> 포트 ${PORT}에 대한 UFW 규칙 추가..."
            ufw allow ${PORT}/tcp || {
                echo ">>> 포트 ${PORT}에 대한 UFW 규칙 추가 실패."
                exit 1
            }
            echo ">>> 포트 ${PORT}에 대한 UFW 규칙이 추가되었습니다.";;
        [Nn]* ) echo ">>> 포트 ${PORT}에 대한 UFW 규칙 추가가 취소되었습니다."
            exit 1;;
        * ) echo ">>> 잘못된 입력입니다. (y/n만 입력 가능)";
            exit 1;;
    esac
fi
echo -e ">>> UFW 방화벽 설정 완료.\n"

# Stop & Remove Existing Container
print_box "INFO: ${CONTAINER_NAME} 컨테이너 실행 여부 검사 시작..."
if [ -n "${CONTAINER_ID}" ]; then
    echo -e ">>> 실행중인 ${CONTAINER_NAME} 컨테이너 중지 및 삭제 시작...\n"

    echo ">>> 실행중인 ${CONTAINER_NAME} 컨테이너 중지 시작..."
    docker stop ${CONTAINER_ID} || {
        echo ">>> ${CONTAINER_NAME} 컨테이너 중지 실패."
        echo ">>> 자세한 내용은 Docker 로그를 확인하세요."
        exit 1
    }
    echo -e ">>> 실행중인 ${CONTAINER_NAME} 컨테이너 중지 완료.\n"

    echo ">>> 중지상태인 ${CONTAINER_NAME} 컨테이너 삭제 시작..."
    docker rm ${CONTAINER_ID} || {
        echo ">>> ${CONTAINER_NAME} 컨테이너 삭제 실패."
        exit 1
    }
    echo -e ">>> 중지상태인 ${CONTAINER_NAME} 컨테이너 삭제 완료.\n"

    echo ">>> 실행중인 ${CONTAINER_NAME} 컨테이너 중지 및 삭제 완료."
fi
echo -e ">>> ${CONTAINER_NAME} 컨테이너 실행 여부 검사 완료.\n"

# Remove Existing Docker Image
print_box "INFO: ${IMAGE_NAME} 이미지 존재 여부 검사 시작..."
if [ ! -z "${IMAGE_ID}" ]; then
    echo ">>> 기존 ${IMAGE_NAME} 이미지 삭제 시작..."
    docker rmi ${IMAGE_ID} || {
        echo ">>> 기존 ${IMAGE_NAME} 이미지 삭제 실패."
        exit 1
    }
    echo ">>> 기존 ${IMAGE_NAME} 이미지 삭제 완료."
fi
echo -e ">>> ${IMAGE_NAME} 이미지 존재 여부 검사 완료.\n"

# Run Docker Container
print_box "INFO: ${CONTAINER_NAME} 컨테이너 실행 시작..."
docker run -d -p ${PORT}:8086 \
    --name ${CONTAINER_NAME} \
    --volume $(pwd)/${VOLUME_DATA}:/var/lib/influxdb2 \
    --volume $(pwd)/${VOLUME_CONFIG}:/etc/influxdb2 \
    --env DOCKER_INFLUXDB_INIT_MODE=setup \
    --env DOCKER_INFLUXDB_INIT_USERNAME=${USERNAME} \
    --env DOCKER_INFLUXDB_INIT_PASSWORD=${PASSWORD} \
    --env DOCKER_INFLUXDB_INIT_ORG=${ORG_NAME} \
    --env DOCKER_INFLUXDB_INIT_BUCKET=${BUCKET_NAME} \
    ${IMAGE_NAME} || {
        echo ">>> ${CONTAINER_NAME} 컨테이너 실행 실패."
        exit 1
    }
echo ">>> ${CONTAINER_NAME} 컨테이너 실행 완료."

echo ">>> InfluxDB2 설치가 완료되었습니다."
exit 0
```

#### 단계 2: InfluxDB 설치 스크립트 실행

`install-influxdb.sh` 스크립트를 실행하여 InfluxDB를 설치하고 설정합니다.

```bash
chmod +x install-influxdb.sh
./install-influxdb.sh
```

<div align="right">

[[맨 위로](#)]

</div>

## 배포 및 인프라 포팅 절차

### 1. 서버 환경 설정

#### 단계 1: 서버 시간 설정

서버의 시간대를 설정합니다. 아래 명령어를 사용하여 서버 시간대를 `Asia/Seoul`로 변경합니다.

```bash
$ sudo timedatectl set-timezone Asia/Seoul
$ timedatectl
$ date
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 2: 미러 서버 변경

서버의 패키지 소스 리스트를 타겟 서버 리전에 맞게 미러 서버로 변경합니다.  
아래 명령어를 사용하여 미러 서버를 변경할 수 있습니다.

```bash
# 문자열 변경 명령어
:%s/원본 문장/변경 문장
```

```bash
# 카이스트 미러서버 변경 (타겟 서버 리전에 맞게 사용)
$ sudo vi /etc/apt/sources.list
:%s/kr.archive.ubuntu.com/ftp.kaist.ac.kr/
:%s/ap-southeast-2.ec2.archive.ubuntu.com/ftp.kaist.ac.kr/
:%s/ap-northeast-2.ec2.archive.ubuntu.com/ftp.kaist.ac.kr/
```

```bash
# 카카오 미러서버 변경 (타겟 서버 리전에 맞게 사용)
$ sudo vi /etc/apt/sources.list
$ :%s/kr.archive.ubuntu.com/mirror.kakao.com/
$ :%s/ap-southeast-2.ec2.archive.ubuntu.com/mirror.kakao.com/
$ :%s/ap-northeast-2.ec2.archive.ubuntu.com/mirror.kakao.com/
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 3: 패키지 업데이트

서버의 패키지 목록을 업데이트하고 최신 버전으로 업그레이드합니다.  
또한, 필요하지 않은 PPA 리포지토리를 제거합니다.

```bash
$ sudo apt update
$ sudo apt upgrade
$ sudo add-apt-repository --remove ppa:certbot/certbot
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 4: 가상 메모리 할당

서버의 가상 메모리를 설정합니다. 아래 명령어를 사용하여 Swap 메모리를 8GB로 설정합니다.

```bash
$ free -h                         # 메모리 크기를 사람이 읽기 쉬운 단위로 출력
$ sudo swapoff -v /swapfile       # swap 비활성

$ sudo fallocate -l 8G /swapfile  # Swap 메모리 할당 8GB
$ sudo chmod 600 /swapfile        # 권한 수정
$ sudo mkswap /swapfile           # swapfile 생성
$ sudo swapon /swapfile           # swapfile 활성화
$ sudo nano /etc/fstab            # 파일 편집
/swapfile none swap sw 0 0        # 내용 추가 (권장)
/swapfile swap swap defaults 0 0  # 내용 추가 (옵션)

$ free -h                         # 메모리 크기를 사람이 읽기 쉬운 단위로 출력
```

```bash
# [파일시스템장치] [마운트포인트] [파일시스템 종류] [옵션] [dump설정] [파일점검옵션]
    /swapfile        swap          swap     defaults    0          0
    /swapfile        none          swap        sw       0          0
```

<div align="right">

[[맨 위로](#)]

</div>

### 2. Docker 환경 구성

서버에서 Docker를 설치하고 구성합니다.

#### 단계 1: Docker apt repository 설정

Docker의 공식 GPG 키를 추가하고, Docker 패키지의 소스 리스트를 추가합니다.

```bash
# Add Docker's official GPG key:
$ sudo apt-get update
$ sudo apt-get install ca-certificates curl
$ sudo install -m 0755 -d /etc/apt/keyrings
$ sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
$ sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
$ echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$ sudo apt-get update
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 2: Docker 패키지 설치

Docker 패키지 및 관련 도구를 설치합니다.

```bash
$ sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 3: Docker 설치 확인

Docker가 제대로 설치되었는지 확인합니다.

```bash
$ sudo docker run hello-world
```

<div align="right">

[[맨 위로](#)]

</div>

### 3. Jenkins 설정

서버에서 Jenkins를 Docker 컨테이너로 설치하고 구성합니다.

#### 단계 1: Dockerfile 작성

다음 내용을 사용하여 `Dockerfile`을 작성합니다.

```bash
# 기본 이미지 설정
FROM jenkins/jenkins:lts-jdk17

# 사용자 root로 변경
USER root

# Docker 공식 GPG 키 추가 및 Docker 저장소 설정
RUN apt-get update && \
    apt-get install -y ca-certificates curl && \
    install -m 0755 -d /etc/apt/keyrings && \
    curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc && \
    chmod a+r /etc/apt/keyrings/docker.asc && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo \"$VERSION_CODENAME\") stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Docker CLI 설치
RUN apt-get update && \
    apt-get install -y docker-ce docker-ce-cli containerd.io
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 2: Shell Script 작성

다음 내용을 사용하여 `install-jenkins.sh` 스크립트를 작성합니다.

```bash
#!/bin/bash

IMAGE_NAME="server/jenkins"
CONTAINER_NAME="server-jenkins"

IMAGE_ID=$(sudo docker images -q $IMAGE_NAME)
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")

echo ">>> CURRENT DOCKER INFORMATION:"
echo "$IMAGE_NAME IMAGE_ID: $IMAGE_ID"
echo -e "$CONTAINER_NAME CONTAINER_ID: $CONTAINER_ID\n"

# Stop & Remove Existing Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 시작...\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 시작..."
    sudo docker stop $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 중지 실패."
        exit 1
    }
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 완료.\n"

    echo ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 시작..."
    sudo docker rm $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 삭제 실패."
        exit 1
    }
    echo -e ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 완료.\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 완료."
fi
echo -e ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 완료.\n"

# Remove Existing Docker Image
echo ">>> $IMAGE_NAME 이미지 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 시작..."
    sudo docker rmi $IMAGE_ID || {
        echo ">>> 기존 $IMAGE_NAME 이미지 삭제 실패."
        exit 1
    }
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 완료."
fi
echo -e ">>> $IMAGE_NAME 이미지 존재 여부 검사 완료.\n"

# Build Docker Image

# 현재 사용자의 UID 추출
USER_UID=$(id -u $USER)
DOCKER_GID=$(getent group docker | cut -d: -f3)

# Docker 이미지를 빌드하면서 사용자 UID와 그룹 GID를 인자로 전달
echo ">>> $IMAGE_NAME 이미지 빌드 시작..."
sudo docker build -t $IMAGE_NAME . \
    --build-arg USER_UID=$USER_UID \
    --build-arg DOCKER_GID=$DOCKER_GID || {
        echo ">>> $IMAGE_NAME 이미지 빌드 실패."
        exit 1
    }
echo -e ">>> $IMAGE_NAME 이미지 빌드 완료.\n"

# Run Docker Container (USER jenkins)
echo ">>> $CONTAINER_NAME 컨테이너 실행 시작..."
sudo chown -R 1000:1000 /var/jenkins_home
sudo docker run -d \
    -p 8081:8080 -p 50000:50000 \
    -v /var/jenkins_home:/var/jenkins_home \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> $CONTAINER_NAME 컨테이너 실행 실패."
        exit 1
    }
echo ">>> $CONTAINER_NAME 컨테이너 실행 완료."
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 3: Docker Jenkins 설치

`install-jenkins.sh` 스크립트를 실행하여 Jenkins를 설치합니다.

```bash
$ sudo chmod +x ./install-jenkins.sh
$ ./install-jenkins.sh
```

<div align="right">

[[맨 위로](#)]

</div>

### 4. Nginx 설정

서버에서 Nginx를 설치하고 구성합니다.

#### 단계 1: Nginx 설치

아래 명령어를 사용하여 Nginx를 설치합니다.

```bash
$ sudo apt update
$ sudo apt install nginx -y
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 2: 방화벽 설정

Nginx가 사용할 포트를 방화벽에서 허용합니다.

```bash
$ sudo ufw enable
$ sudo ufw allow 80
$ sudo ufw allow 443
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 3: Nginx 설정 파일 편집

Nginx 설정 파일을 편집하여 필요한 설정을 추가합니다.

```bash
$ sudo vi /etc/nginx/sites-available/default

server {
    listen 80 default_server;
    listen [::]:80 default_server;

    server_name example.com www.example.com;

    location / {
        root /home/ubuntu/puzzlepop/deploy/dist;
        index index.html index.htm index.nginx-debian.html;
        try_files $uri $uri/ /index.html =404;
    }
}
```

#### 단계 4: Nginx 서비스 시작

Nginx 서비스를 시작합니다.

```bash
$ sudo systemctl start nginx
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 5: LetsEncrypt & Certbot 설치

LetsEncrypt와 Certbot을 설치하여 SSL 인증서를 설정합니다.

```bash
$ sudo apt-get install letsencrypt
$ sudo apt-get install certbot python3-certbot-nginx
```

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 6: Certbot을 사용하여 SSL 인증서 설정

Certbot을 사용하여 도메인에 대해 SSL 인증서를 설정합니다.

```bash
$ sudo certbot --nginx -d your_domain -d www.your_domain
$ sudo certbot --nginx
```

- 이메일 입력
- 약관 동의 - Y
- 이메일 수신동의
- 도메인 입력 - i10{팀코드}.p.ssafy.io
- HTTP 입력 시 리다이렉트 여부 - 2

<div align="right">

[[맨 위로](#)]

</div>

#### 단계 7: NginX 설정

```bash
$ sudo vi /etc/nginx/sites-available/default
```

```bash
server {
    listen 80 default_server;
    listen [::]:80 default_server;

    root /var/www/html;
    index index.html index.htm index.nginx-debian.html;

    server_name _;

    location / {
        try_files $uri $uri/ =404;
    }
}

server {
    root /var/jenkins_home/application/dist;
    index index.html index.htm index.nginx-debian.html;

    server_name k10a509.p.ssafy.io www.k10a509.p.ssafy.io promptquiz.site www.promptquiz.site;
    client_max_body_size 100M;

    # 정적 배포
    location / {
        try_files $uri $uri/ /index.html =404;

        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-NginX-Proxy true;

        proxy_connect_timeout 300s;
        proxy_send_timeout 300s;
        proxy_read_timeout 300s;
        send_timeout 300s;
    }

	# # 동적 배포
	# location /index {
	# 	proxy_pass http://localhost:3000;
    #     proxy_redirect default;
    #     charset utf-8;

    #     proxy_set_header X-Real-IP $remote_addr;
    #     proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    #     proxy_set_header X-Forwarded-Proto $scheme;
    #     proxy_set_header X-NginX-Proxy true;
	# }

    location /ws {
        proxy_pass http://localhost:8080;
        proxy_redirect default;
        charset utf-8;

        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-NginX-Proxy true;

        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_redirect default;
        charset utf-8;

        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-NginX-Proxy true;

        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }

    listen [::]:443 ssl ipv6only=on;
    listen 443 ssl;
    ssl_certificate /etc/letsencrypt/live/k10a509.p.ssafy.io/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/k10a509.p.ssafy.io/privkey.pem;
    include /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;
}

server {
	if ($host = k10a509.p.ssafy.io) {
		return 301 https://$host$request_uri;
	} # managed by Certbot

	if ($host = promptquiz.site) {
		return 301 https://k10a509.p.ssafy.io$request_uri;
	} # Gabia Proxy

    listen 80;
    listen [::]:80;
    server_name k10a509.p.ssafy.io;
    return 404;
}

```

<div align="right">

[[맨 위로](#)]

</div>

## 모니터링 포팅 절차

### 1. Grafana 설정

Grafana는 시계열 데이터 모니터링 및 시각화를 위한 오픈 소스 플랫폼입니다.  
다음은 Grafana를 설치하고 설정하는 절차입니다.

#### 단계 1: Grafana 설치 스크립트 작성

다음 내용을 사용하여 `install-grafana.sh` 스크립트를 작성합니다:

```bash
#!/bin/bash

# 변수명 설정
IMAGE_NAME="grafana/grafana"
CONTAINER_NAME="server-grafana"

IMAGE_ID=$(sudo docker images -q $IMAGE_NAME)
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")

echo ">>> CURRENT DOCKER INFORMATION:"
echo "$IMAGE_NAME IMAGE_ID: $IMAGE_ID"
echo "$CONTAINER_NAME CONTAINER_ID: $CONTAINER_ID"
echo ""

# 방화벽 설정
if sudo ufw status | grep -qw inactive; then
    echo "방화벽이 비활성화되어 있습니다. 방화벽을 활성화합니다."
    sudo ufw enable
fi
sudo ufw allow 9200

# Stop & Remove Existing Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 시작...\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 시작..."
    sudo docker stop $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 중지 실패."
        exit 1
    }
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 완료.\n"

    echo ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 시작..."
    sudo docker rm $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 삭제 실패."
        exit 1
    }
    echo -e ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 완료.\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 완료."
fi
echo -e ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 완료.\n"

# Remove Existing Docker Image
echo ">>> $IMAGE_NAME 이미지 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 시작..."
    sudo docker rmi $IMAGE_ID || {
        echo ">>> 기존 $IMAGE_NAME 이미지 삭제 실패."
        exit 1
    }
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 완료."
fi
echo -e ">>> $IMAGE_NAME 이미지 존재 여부 검사 완료.\n"

# Run Docker Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 시작..."
sudo docker run -d -p 9200:3000 \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> $CONTAINER_NAME 컨테이너 실행 실패."
        exit 1
    }
echo ">>> $CONTAINER_NAME 컨테이너 실행 완료."

echo ">>> Grafana 설치가 완료되었습니다."
```

#### 단계 2: Grafana 설치 스크립트 실행

`install-grafana.sh` 스크립트를 실행하여 Grafana를 설치합니다.

```bash
$ sudo chmod +x install-grafana.sh
$ ./install-grafana.sh
```

<div align="right">

[[맨 위로](#)]

</div>

### 2. Prometheus 설정

Prometheus는 시계열 데이터베이스와 모니터링 시스템입니다.  
다음은 Prometheus를 설치하고 설정하는 절차입니다.

#### 단계 1: Prometheus 설치 스크립트 작성

다음 내용을 사용하여 `install-prometheus.sh` 스크립트를 작성합니다:

```bash
#!/bin/bash

# 변수명 설정
IMAGE_NAME="prom/prometheus"
CONTAINER_NAME="server-prometheus"

IMAGE_ID=$(sudo docker images -q $IMAGE_NAME)
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")

echo ">>> CURRENT DOCKER INFORMATION:"
echo "$IMAGE_NAME IMAGE_ID: $IMAGE_ID"
echo "$CONTAINER_NAME CONTAINER_ID: $CONTAINER_ID"
echo ""

# 방화벽 설정
if sudo ufw status | grep -qw inactive; then
    echo "방화벽이 비활성화되어 있습니다. 방화벽을 활성화합니다."
    sudo ufw enable
fi
sudo ufw allow 9090

# Stop & Remove Existing Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 시작...\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 시작..."
    sudo docker stop $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 중지 실패."
        exit 1
    }
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 완료.\n"

    echo ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 시작..."
    sudo docker rm $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 삭제 실패."
        exit 1
    }
    echo -e ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 완료.\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 완료."
fi
echo -e ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 완료.\n"

# Remove Existing Docker Image
echo ">>> $IMAGE_NAME 이미지 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 시작..."
    sudo docker rmi $IMAGE_ID || {
        echo ">>> 기존 $IMAGE_NAME 이미지 삭제 실패."
        exit 1
    }
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 완료."
fi
echo -e ">>> $IMAGE_NAME 이미지 존재 여부 검사 완료.\n"

# Create Config File
echo ">>> Prometheus 설정 파일 생성 시작..."
sudo mkdir -p $HOME/prometheus
sudo touch $HOME/prometheus/prometheus.yml
sudo chmod 666 $HOME/prometheus/prometheus.yml
sudo cat << EOF > $HOME/prometheus/prometheus.yml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

EOF
sudo chmod 644 $HOME/prometheus/prometheus.yml
echo -e ">>> Prometheus 설정 파일 생성 완료.\n"

# Run Docker Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 시작..."
sudo docker run -d -p 9090:9090 \
    -v $HOME/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> $CONTAINER_NAME 컨테이너 실행 실패."
        exit 1
    }
echo ">>> $CONTAINER_NAME 컨테이너 실행 완료."

echo ">>> Prometheus 설치가 완료되었습니다."
```

#### 단계 2: Prometheus 설치 스크립트 실행

`install-prometheus.sh` 스크립트를 실행하여 Prometheus를 설치합니다.

```bash
$ sudo chmod +x install-prometheus.sh
$ ./install-prometheus.sh
```

<div align="right">

[[맨 위로](#)]

</div>

### 3. Node Exporter 설정

Node Exporter는 Prometheus를 사용하여 서버의 하드웨어 및 OS 메트릭을 수집하는 도구입니다.  
다음은 Node Exporter를 설치하고 설정하는 절차입니다.

#### 단계 1: Node Exporter 설치 스크립트 작성

다음 내용을 사용하여 `install-node-exporter.sh` 스크립트를 작성합니다:

```bash
#!/bin/bash

# 변수명 설정
OS="linux"
ARCH="amd64"
VERSION=1.3.1

IMAGE_NAME="server/node-exporter"
CONTAINER_NAME="server-node-exporter"

IMAGE_ID=$(sudo docker images -q $IMAGE_NAME)
CONTAINER_ID=$(sudo docker ps -aqf "name=$CONTAINER_NAME")

echo ">>> CURRENT DOCKER INFORMATION:"
echo "$IMAGE_NAME IMAGE_ID: $IMAGE_ID"
echo "$CONTAINER_NAME CONTAINER_ID: $CONTAINER_ID"
echo ""

# 방화벽 설정
if sudo ufw status | grep -qw inactive; then
    echo "방화벽이 비활성화되어 있습니다. 방화벽을 활성화합니다."
    sudo ufw enable
fi
sudo ufw allow 9100

# Stop & Remove Existing Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 시작..."
if [ ! -z "$CONTAINER_ID" ]; then
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 시작...\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 시작..."
    sudo docker stop $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 중지 실패."
        exit 1
    }
    echo -e ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 완료.\n"

    echo ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 시작..."
    sudo docker rm $CONTAINER_ID || {
        echo ">>> $CONTAINER_NAME 컨테이너 삭제 실패."
        exit 1
    }
    echo -e ">>> 중지상태인 $CONTAINER_NAME 컨테이너 삭제 완료.\n"

    echo ">>> 실행중인 $CONTAINER_NAME 컨테이너 중지 및 삭제 완료."
fi
echo -e ">>> $CONTAINER_NAME 컨테이너 실행 여부 검사 완료.\n"

# Remove Existing Docker Image
echo ">>> $IMAGE_NAME 이미지 존재 여부 검사 시작..."
if [ ! -z "$IMAGE_ID" ]; then
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 시작..."
    sudo docker rmi $IMAGE_ID || {
        echo ">>> 기존 $IMAGE_NAME 이미지 삭제 실패."
        exit 1
    }
    echo ">>> 기존 $IMAGE_NAME 이미지 삭제 완료."
fi
echo -e ">>> $IMAGE_NAME 이미지 존재 여부 검사 완료.\n"

# Edit Config File
echo ">>> Prometheus 설정 파일 수정 시작..."
sudo chmod 666 $HOME/prometheus/prometheus.yml
sudo cat << EOF >> $HOME/prometheus/prometheus.yml
scrape_configs:
  - job_name: 'node_exporter'
    static_configs:
      - targets: ['localhost:9100']

EOF
sudo chmod 644 $HOME/prometheus/prometheus.yml
echo -e ">>> Prometheus 설정 파일 수정 완료.\n"

# Build Docker Image
echo ">>> $IMAGE_NAME 이미지 빌드 시작..."
sudo docker build -t $IMAGE_NAME . \
    --build-arg OS=$OS \
    --build-arg ARCH=$ARCH \
    --build-arg VERSION=$VERSION || {
        echo ">>> $IMAGE_NAME 이미지 빌드 실패."
        exit 1
    }
echo -e ">>> $IMAGE_NAME 이미지 빌드 완료.\n"

# Run Docker Container
echo ">>> $CONTAINER_NAME 컨테이너 실행 시작..."
sudo docker run -d -p 9100:9100 \
    --name $CONTAINER_NAME $IMAGE_NAME || {
        echo ">>> $CONTAINER_NAME 컨테이너 실행 실패."
        exit 1
    }
echo ">>> $CONTAINER_NAME 컨테이너 실행 완료."

echo ">>> Node-Exporter 설치가 완료되었습니다."
```

#### 단계 2: Node Exporter 설치 스크립트 실행

`install-node-exporter.sh` 스크립트를 실행하여 Node Exporter를 설치합니다.

```bash
$ sudo chmod +x install-node-exporter.sh
$ ./install-node-exporter.sh
```

<div align="right">

[[맨 위로](#)]

</div>

## Stable Diffusion 포팅 절차

### 단계 1: Github Pull

```shell
$ sudo apt install wget git python3 python3-venv libgl1 libglib2.0-0
$ git clone https://github.com/AUTOMATIC1111/stable-diffusion-webui
$ wget -q https://raw.githubusercontent.com/AUTOMATIC1111/stable-diffusion-webui/master/webui.sh
```

<div align="right">

[[맨 위로](#)]

</div>

---

### 단계 2: Models 파일 설정

```shell
# Download CheckPoint
$ sudo mkdir -p ./models/Stable-diffusion && sudo wget -O ./models/Stable-diffusion/animePastelDream_softBakedVae.safetensors "https://civitai.com/api/download/models/28100"

$ sudo mkdir -p ./models/Stable-diffusion && sudo wget -O ./models/Stable-diffusion/disneyPixarCartoonTypeA_v10.safetensors "https://civitai.com/api/download/models/69832"

$ sudo mkdir -p ./models/Stable-diffusion && sudo wget -O ./models/Stable-diffusion/xxmix9realistic_v40.safetensors "https://civitai.com/api/download/models/102222"

# Download LoRa
$ sudo mkdir -p ./models/Lora && sudo wget -O ./models/Lora/more_details.safetensors "https://civitai.com/api/download/models/87153?type=Model&format=SafeTensor"

# Download VAE
$ sudo mkdir -p ./models/VAE && sudo wget -O ./models/VAE/vaeFtMse840000Ema_v100.pt "https://huggingface.co/casque/vaeFtMse840000Ema_v100/resolve/main/vaeFtMse840000Ema_v100.pt?download=true"
```

<div align="right">

[[맨 위로](#)]

</div>

---

### 단계 3: Conda 환경 구성

```shell
$ conda create -n venv python=3.10.6
$ conda activate venv
```

<div align="right">

[[맨 위로](#)]

</div>

---

### 단계 4: Command Line Arguments 설정

`webui-user.sh`

```shell
#!/bin/bash
#########################################################
# Uncomment and change the variables below to your need:#
#########################################################

# Install directory without trailing slash
#install_dir="/home/$(whoami)"

# Name of the subdirectory
#clone_dir="stable-diffusion-webui"

# Commandline arguments for webui.py, for example: export COMMANDLINE_ARGS="--medvram --opt-split-attention"
#export COMMANDLINE_ARGS=""
# export COMMANDLINE_ARGS="--api --listen --device-id 8 --skip-torch-cuda-test --opt-sdp-attention --precision full --no-half --no-half-vae --theme dark"
# export COMMANDLINE_ARGS="--api --listen --device-id 8 --skip-version-check --xformers --precision full --no-half --no-half-vae --theme dark"
export COMMANDLINE_ARGS="--api --listen --device-id 8 --opt-sdp-attention --precision full --no-half --no-half-vae --theme dark"


# python3 executable
#python_cmd="python3"

# git executable
#export GIT="git"

# python3 venv without trailing slash (defaults to ${install_dir}/${clone_dir}/venv)
#venv_dir="venv"

# script to launch to start the app
#export LAUNCH_SCRIPT="launch.py"

# install command for torch
#export TORCH_COMMAND="pip install torch==1.12.1+cu113 --extra-index-url https://download.pytorch.org/whl/cu113"
export TORCH_COMMAND="pip install torch==2.0.1+cu117 --extra-index-url https://download.pytorch.org/whl/cu117"

# Requirements file to use for stable-diffusion-webui
#export REQS_FILE="requirements_versions.txt"

# Fixed git repos
#export K_DIFFUSION_PACKAGE=""
#export GFPGAN_PACKAGE=""

# Fixed git commits
#export STABLE_DIFFUSION_COMMIT_HASH=""
#export CODEFORMER_COMMIT_HASH=""
#export BLIP_COMMIT_HASH=""

# Uncomment to enable accelerated launch
#export ACCELERATE="True"

# Uncomment to disable TCMalloc
#export NO_TCMALLOC="True"

###########################################
```

<div align="right">

[[맨 위로](#)]

</div>

---

### 단계 5: Stable Diffusion 실행

```shell
$ ./webui.sh
```

<div align="right">

[[맨 위로](#)]

</div>

## CI/CD 파이프라인 설정 (Jenkins)

```shell
// 공통 함수 정의
def sendMattermostNotification(String stage, String status) {
    script {
        def AUTHOR_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
        def AUTHOR_NAME = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()

        def color = (status == 'Success') ? 'good' : 'danger'
        def message = "${stage} ${status}: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${AUTHOR_ID}(${AUTHOR_NAME})\n(<${env.BUILD_URL}|Details>)"
        def endpoint = 'https://meeting.ssafy.com/hooks/ajhkt8iebtnffqpfrub54d1sto'
        def channel = 'a509-jenkins'

        mattermostSend (
            color: color,
            message: message,
            endpoint: endpoint,
            channel: channel,
        )
    }
}

pipeline {
    agent any

    // 필요한 변수 설정
    environment {
        PROJECT_DIR = 'your_project_directory_name'
        DOCKER_REGISTRY = 'your_docker_registry_url'

        BACKEND_IMAGE_NAME = 'server/backend'
        FRONTEND_IMAGE_NAME = 'server/frontend'
        BACKEND_CONTAINER_NAME = 'server-backend'
        FRONTEND_CONTAINER_NAME = 'server-frontend'
    }


    stages {
        stage('Checkout') {
            steps {
                echo 'Starting Repository Checkout'
                echo '<<<<<<<<<< Checkout Repository Start >>>>>>>>>>'

                git branch: 'develop', credentialsId: 'GITLAB_ACCESS_TOKEN',
                url: 'https://lab.ssafy.com/s10-final/S10P31A509.git'

                echo '<<<<<<<<<< Checkout Repository Complete Successfully >>>>>>>>>>'
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean build'

                    sh 'chmod +x ./build-backend.sh'
                    sh './build-backend.sh'
                }
            }
            post {
                success {
                    script {
                        sendMattermostNotification('BE/빌드', 'Success')
                    }
                }
                failure {
                    script {
                        sendMattermostNotification('BE/빌드', 'Failed')
                    }
                }
            }
        }

        stage('Test Backend') {
            steps {
                dir('backend') {
                    echo '<<<<<<<<<< Backend Tests Start >>>>>>>>>>'
                    sh './gradlew test'
                    echo '<<<<<<<<<< Backend Tests Complete Successfully >>>>>>>>>>'
                }
            }
            post {
                success {
                    script {
                        sendMattermostNotification('BE/테스트', 'Success')
                    }
                }
                failure {
                    script {
                        sendMattermostNotification('BE/테스트', 'Failed')
                    }
                }
            }
        }

        stage('Deploy Backend') {
            steps {
                dir('backend') {
                    sh 'chmod +x ./deploy-backend.sh'
                    sh './deploy-backend.sh'
                }
            }
            post {
                success {
                    script {
                        sendMattermostNotification('BE/배포', 'Success')
                    }
                }
                failure {
                    script {
                        sendMattermostNotification('BE/배포', 'Failed')
                    }
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'chmod +x ./build-frontend.sh'
                    sh './build-frontend.sh'
                }
            }
            post {
                success {
                    script {
                        sendMattermostNotification('FE/빌드', 'Success')
                    }
                }
                failure {
                    script {
                        sendMattermostNotification('FE/빌드', 'Failed')
                    }
                }
            }
        }

        // stage('Test Frontend') {
        //     steps {
        //         dir('frontend') {
        //             echo '<<< Frontend Tests Start >>>'
        //             sh 'npm test'
        //             echo '<<< Frontend Tests Complete Successfully >>>'
        //         }
        //     }
        //     post {
        //         success {
        //             script {
        //                 sendMattermostNotification('FE/테스트', 'Success')
        //             }
        //         }
        //         failure {
        //             script {
        //                 sendMattermostNotification('FE/테스트', 'Failed')
        //             }
        //         }
        //     }
        // }

        stage('Deploy Frontend') {
            steps {
                dir('frontend') {
                    sh 'chmod +x ./deploy-frontend.sh'
                    sh './deploy-frontend.sh'
                }
            }
            post {
                success {
                    script {
                        sendMattermostNotification('FE/배포', 'Success')
                    }
                }
                failure {
                    script {
                        sendMattermostNotification('FE/배포', 'Failed')
                    }
                }
            }
        }
    }

    post {
        always {
            // sh 'docker system prune -af'
            echo '>>>>>>>>>> Pipeline Execution Complete. <<<<<<<<<<'
        }
        success {
            echo '>>>>>>>>>> Pipeline Execution Success. <<<<<<<<<<'
            script {
                sendMattermostNotification('빌드/배포', 'Success')
            }
        }
        failure {
            echo '>>>>>>>>>> Pipeline Execution Failed. <<<<<<<<<<'
            script {
                sendMattermostNotification('빌드/배포', 'Failed')
            }
        }
    }
}

```

<div align="right">

[[맨 위로](#)]

</div>

---

## 문제 해결 가이드

- **문제:** Jenkins에서 Docker 명령어 실행 시 권한 문제 발생

  - **해결책:** Jenkins 사용자를 Docker 그룹에 추가하고, 시스템을 재부팅하여 변경사항을 적용합니다.

- **문제:** Linux에서 Spring Boot 애플리케이션이 데이터베이스에 연결되지 않음

  - **해결책:** `application.properties` 파일에서 데이터베이스 URL, 사용자 이름, 비밀번호가 올바르게 설정되었는지 확인합니다. MySQL 서비스가 실행 중인지도 확인합니다.

- **문제:** Next.js 애플리케이션이 Linux에서 빌드 실패

  - **해결책:** 모든 의존성이 최신 버전인지 확인하고, `node_modules` 폴더와 `yarn.lock` 또는 `package-lock.json` 파일을 삭제한 후 다시 설치합니다.

- **문제:** MySQL 컨테이너 접속 오류
  - **해결책:** Docker 컨테이너 네트워크 설정을 검토하고, MySQL 컨테이너의 로그를 확인하여 구체적인 오류 메시지를 분석합니다. `application.properties`의 데이터베이스 연결 설정이 올바른지 확인합니다.

<div align="right">

[[맨 위로](#)]

</div>

## 참고 자료

- 서버
  - [Ubuntu 공식 문서](https://ubuntu.com/)
  - [Docker 공식 문서](https://docs.docker.com/)
  - [Jenkins 공식 문서](https://www.jenkins.io/doc/)
  - [Nginx 공식 문서](https://nginx.org/en/docs/)
- 개밡
  - [SpringBoot 공식 문서](https://spring.io/projects/spring-boot)
  - [ReactJS 공식 문서](https://ko.legacy.reactjs.org/docs/getting-started.html)
  - [TypeScript 공식 문서](https://www.typescriptlang.org/docs/html)
- 데이터베이스
  - [MySQL 공식 문서](https://dev.mysql.com/doc/)
  - [Redis 공식 문서](https://redis.io/docs/latest/)
  - [InfluxDB 공식 문서](https://docs.influxdata.com/influxdb/v2/install/?t=Docker)
- 모니터링
  - [Grafana 공식 문서](https://grafana.com/docs/grafana/latest/)
  - [Prometheus 공식 문서](https://prometheus.io/docs/)
  - [NodeExporter 공식 문서](https://github.com/prometheus/node_exporter)
- AI
  - [Stable-Diffusion-Webui](https://github.com/AUTOMATIC1111/stable-diffusion-webui/wiki)

<div align="right">

[[맨 위로](#)]

</div>

## FAQ

**Q: Docker 이미지 빌드 시 'no space left on device' 오류가 발생하는 이유는?**
A: Docker가 사용하는 디스크 공간이 부족할 때 발생합니다. Docker 이미지와 컨테이너를 정리하여 공간을 확보해야 합니다.

<div align="right">

[[맨 위로](#)]

</div>
