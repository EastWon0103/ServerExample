## Simple Server
> 부하 테스트를 위해 구축된 간단한 스프링 서버
### 1. Overview
**네트워크 통신**    
네트워크 통신은 NGINX(리버스 프록시) -> 스프링 서버 -> MySQL 순으로 진행됩니다.    

**구현 항목**
- (GET) /api/posts/{id}: 게시글 id로 조회
- (GET) /api/posts: 게시글 무한 스크롤
  - Parameter
    - offset: 현재 오프셋(default = 0)
    - limit: 읽고 싶은 길이(default = 5)
- (POST) /api/posts: 게시글 업로드
  - RequestBody
    - title: 제목
    - content: 내용
    - author: 글쓴이 이름
- (PUT) /api/posts/{id}: 게시글 id로 수정
  - RequestBody
    - title: 제목
    - content: 내용
- (DELETE) /api/posts/{id}: 게시글 id로 삭제 입니다.

### 2. Run
요구 사항: Docker

`docker-compose.yml`이 위치한 곳에서 아래의 명령어를 실행시켜 주면 됩니다.
```
docker compose up -d --force-recreate --build
```

Spring Server는 빌드가 된 후에 실행되니, `docker ps -a` 명령어로 `nginx`, `simple-server`, `main-db`가 실행되는지 확인해주세요.
```
CONTAINER ID   IMAGE                      COMMAND                   CREATED          STATUS                      PORTS                               NAMES
25a8c3348c83   openjdk:17                 "java -jar SimpleCru…"   31 minutes ago   Up 31 minutes               0.0.0.0:7080->8080/tcp              simple-server
bfeb8bdf8c68   nginx                      "/docker-entrypoint.…"   31 minutes ago   Up 31 minutes               0.0.0.0:8888->80/tcp                proxy_server
7044509ced62   gradle:8.9.0-jdk17-jammy   "/bin/bash /__cacert…"   31 minutes ago   Exited (0) 31 minutes ago                                       spring-builder
9d6b29902549   mysql:8.0.33-oracle        "docker-entrypoint.s…"   31 minutes ago   Up 31 minutes               33060/tcp, 0.0.0.0:3310->3306/tcp   main-db
```