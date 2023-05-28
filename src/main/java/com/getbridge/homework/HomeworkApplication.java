package com.getbridge.homework;

import com.getbridge.homework.rest.dto.UserDto;
import com.getbridge.homework.rest.dto.OneOnOneDto;
import com.getbridge.homework.rest.repository.OneOnOneRepository;
import com.getbridge.homework.rest.repository.UserRepository;
import com.getbridge.homework.rest.service.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.util.List;

@EnableWebMvc
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

    @Component
    public static class DatabaseInitializer implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private OneOnOneRepository oneOnOneRepository;

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
                UserDto user1 = new UserDto("user1", "user@one.com", "password");
                UserDto user2 = new UserDto("user2", "user@two.com", "password");

                String id1 = userRepository.save(util.dtoToUsr(user1)).getId();
                String id2 = userRepository.save(util.dtoToUsr(user2)).getId();

                if (oneOnOneRepository.count() == 0) {
                    List<String> participantIds = List.of(id1, id2);
                    OneOnOneDto oneOnOneDto = new OneOnOneDto("Title", participantIds, LocalDateTime.now(), "Description", "location");
                    oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOneDto));
                }
            }
        }
    }
}

