package cn.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 用于AbsAudit 类
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}