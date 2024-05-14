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
RUN ls -l models && ls -l models/cc.ko.300.bin

# 나머지 애플리케이션 파일 복사
COPY . /app

# 포트 설정
EXPOSE 8000

# 애플리케이션 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
