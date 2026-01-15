# Velog-like Blog Project

Spring Boot 3.4 + MySQL 8.4 LTS 기반의 개인 블로그 백엔드 프로젝트입니다.

## 1. 요구사항
- Java 21
- Docker & Docker Compose
- Gradle

## 2. 실행 방법

### DB 실행 (Docker)
```bash
docker-compose up -d
```
*MySQL 8.4 컨테이너가 3306 포트로 실행됩니다.*

### 앱 실행
```bash
./gradlew bootRun
```

### 초기 관리자 계정
- Username: `admin`
- Password: `password`
*(application.yml에서 변경 가능)*

## 3. API 문서
서버 실행 후 아래 주소에서 Swagger UI를 확인할 수 있습니다.
- http://localhost:8080/swagger-ui/index.html
- http://localhost:8080/v3/api-docs

## 4. 주요 기능
### Public
- 게시글 목록 (Published 상태만)
- 상세 조회
- 검색 (제목/본문)
- 카테고리/태그 필터링

### Admin
- 로그인/로그아웃 (/api/admin/auth/login)
- 게시글 작성/수정/삭제
- 이미지 업로드 (Local Storage)
- 카테고리/태그 관리
