package com.puron.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * puron-gateway 진입점 — 프론트/백엔드 오리진 통합(CORS 회피) + JWT 액세스 토큰 자체 검증 전용.
 * modular-monolith 미적용(라우팅 전용이라 도메인 경계 없음).
 * (docs/05-deployment/phase-1/environment-setup-plan.md 5절)
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
