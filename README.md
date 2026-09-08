# puron-gateway

Spring Cloud Gateway 기반 API 게이트웨이. 프론트엔드/백엔드 오리진을 하나의 진입점으로 통합해
CORS 문제를 회피하고, JWT 액세스 토큰을 게이트웨이에서 자체 검증(백엔드 호출 없이)한다.

라우팅 전용이라 도메인 경계가 없어 modular-monolith 구조는 적용하지 않는다.

## 기술 스택

- Java 21
- Spring Boot 3.4.1 / Spring Cloud 2024.0.0 (`spring-cloud-starter-gateway`)
- Spring Boot Actuator (`health`, `info` 노출)
- jjwt 0.12.6 (게이트웨이 자체 JWT 서명 검증)
- Gradle (wrapper 포함)

## 환경별 구성

| profile | 게이트웨이 포트 | `/api/**` → 백엔드 | 그 외 → 프론트엔드 |
|---------|:--:|:--:|:--:|
| plan    | 8080 | localhost:8081 | localhost:3001 |
| dev     | 8090 | localhost:8082 | localhost:3002 |
| prod    | 8000 | localhost:8083 | localhost:3003 |

공통 값은 `application.yml`, profile별 오버라이드는 `application-{profile}.yml`에 있다.

### JWT 시크릿

각 profile의 `jwt.secret`은 환경변수로 주입한다. 게이트웨이 자체 서명 검증이 성공하려면
해당 백엔드의 시크릿과 반드시 동일한 값이어야 한다.

| profile | 환경변수 | 기본값 |
|---------|---------|--------|
| plan | `JWT_SECRET_PLAN` | 로컬 전용 기본값 존재 |
| dev  | `JWT_SECRET_DEV`  | 로컬 전용 기본값 존재 |
| prod | `JWT_SECRET_PROD` | 없음 (기동 시 명시 필수) |

## 실행

```bash
# dev 환경
./gradlew bootRun --args='--spring.profiles.active=dev'

# 빌드 후 실행
./gradlew build
java -jar build/libs/puron-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 헬스 체크

```bash
curl http://localhost:8090/actuator/health
```
