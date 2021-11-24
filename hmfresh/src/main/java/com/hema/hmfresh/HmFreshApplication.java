package com.hema.hmfresh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hema.hmfresh.mapper")
@SpringBootApplication
public class HmFreshApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmFreshApplication.class, args);
    }

}
