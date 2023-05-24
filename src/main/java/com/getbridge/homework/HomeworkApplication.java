package com.getbridge.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HomeworkApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

}
