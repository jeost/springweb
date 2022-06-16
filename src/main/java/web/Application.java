package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA가 매핑된 엔티티(테이블)의 변화 감지
@SpringBootApplication // 스프링부트 자동 설정, 스프링 빈(클래스 메모리)읽기와 세션 모두 자동설정
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
