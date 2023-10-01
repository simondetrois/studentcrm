package scrm.backend.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["scrm.backend.backend.controller", "scrm.backend.backend.exceptionhandling",
        "scrm.backend.backend.model.dto", "scrm.backend.backend.model.entity", "scrm.backend.backend.repository",
        "scrm.backend.backend.services"]
)
@EnableJpaRepositories("scrm.backend.backend.*")
@EntityScan("scrm.backend.backend.model")
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
