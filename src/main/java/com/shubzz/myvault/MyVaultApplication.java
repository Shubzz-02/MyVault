package com.shubzz.myvault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SpringBootApplication
@EnableMongoRepositories
public class MyVaultApplication extends SpringBootServletInitializer {

    private final StringRedisTemplate stringRedisTemplate;

    public MyVaultApplication(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyVaultApplication.class, args);
    }

    @PostConstruct
    public void connection() {
        try {
            Objects.requireNonNull(stringRedisTemplate.getConnectionFactory()).getConnection();
        } catch (Exception e) {
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("- Redis host and port is not availables. please check application configuration file. -");
            System.out.println("-------------------------------------------------------------------------------------------");
            System.exit(-1);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyVaultApplication.class);
    }

}


//https://www.javainuse.com/spring/boot-jwt