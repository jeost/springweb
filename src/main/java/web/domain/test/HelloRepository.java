package web.domain.test;

import org.springframework.data.jpa.repository.JpaRepository;
import web.domain.test.HelloEntity;

public interface HelloRepository extends JpaRepository<HelloEntity, Long> {
}
