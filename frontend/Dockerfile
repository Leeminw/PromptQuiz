# Node.js 공식 이미지 사용. 버전 20.11.1, 경량화된 Alpine Linux 기반
FROM node:20.12.2-alpine

# 작업 디렉토리 설정. 컨테이너 내 앱의 기본 경로
WORKDIR /home/app

# 현재 디렉토리의 package.json과 package-lock.json 파일이 존재한다면
# 컨테이너의 작업 디렉토리로 package.json과 package-lock.json 복사
COPY package*.json ./

# 개발 의존성 포함하여 모든 패키지 설치
ARG NODE_ENV=development
ENV NODE_ENV=${NODE_ENV}

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
