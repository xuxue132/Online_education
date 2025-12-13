package com.example.community_education;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.community_education.Dao")
public class CommunityEducationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityEducationApplication.class, args);
    }

}
