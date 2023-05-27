package com.getbridge.homework;

import com.getbridge.homework.rest.dto.UserDto;
import com.getbridge.homework.rest.repository.UserRepository;
import com.getbridge.homework.rest.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class HomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	@Component
	public static class DatabaseInitializer implements CommandLineRunner {

		@Autowired
		private UserRepository userRepository;

		@Autowired
		private Util util;

		@Autowired
		private MongoTemplate mongoTemplate;

		public DatabaseInitializer(UserRepository userRepository, Util util, MongoTemplate mongoTemplate) {
			this.userRepository = userRepository;
			this.util = util;
			this.mongoTemplate = mongoTemplate;
		}

		@Override
		public void run(String... args) throws Exception {
			if (userRepository.count() == 0) {
				UserDto user1 = new UserDto("user1", "user@one.com");
				UserDto user2 = new UserDto("user2", "user@two.com");
				userRepository.save(util.dtoToUsr(user1));
				userRepository.save(util.dtoToUsr(user2));
			}
		}
	}
}
