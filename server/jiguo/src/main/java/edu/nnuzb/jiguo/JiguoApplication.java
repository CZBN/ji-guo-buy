package edu.nnuzb.jiguo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.nnuzb.jiguo.dao")
public class JiguoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiguoApplication.class, args);
    }
}