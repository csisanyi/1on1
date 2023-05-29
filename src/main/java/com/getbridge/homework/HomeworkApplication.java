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
                UserDto user3 = new UserDto("user3", "user@three.com", "password");
                UserDto user4 = new UserDto("user4", "user@four.com", "password");
                UserDto user5 = new UserDto("user5", "user@five.com", "password");
                UserDto user6 = new UserDto("user6", "user@six.com", "password");

                String id1 = userRepository.save(util.dtoToUsr(user1)).getId();
                String id2 = userRepository.save(util.dtoToUsr(user2)).getId();
                String id3 = userRepository.save(util.dtoToUsr(user3)).getId();
                String id4 = userRepository.save(util.dtoToUsr(user4)).getId();
                String id5 = userRepository.save(util.dtoToUsr(user5)).getId();
                String id6 = userRepository.save(util.dtoToUsr(user6)).getId();
                List<String> participantIds1 = List.of(id1, id2, id3);
                List<String> participantIds2 = List.of(id4, id5);
                List<String> participantIds3 = List.of(id1, id6);
                List<String> participantIds4 = List.of(id1, id2,id3,id4,id5,id6);
                OneOnOneDto oneOnOneDto1 = new OneOnOneDto("Title", participantIds1, LocalDateTime.now(), "Description", "location");
                OneOnOneDto oneOnOneDto2 = new OneOnOneDto("Title", participantIds2, LocalDateTime.now(), "Description", "location");
                OneOnOneDto oneOnOneDto3 = new OneOnOneDto("Title", participantIds3, LocalDateTime.now(), "Description", "location");
                OneOnOneDto oneOnOneDto4 = new OneOnOneDto("Title", participantIds4, LocalDateTime.now(), "Description", "location");
                oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOneDto1));
                oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOneDto2));
                oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOneDto3));
                oneOnOneRepository.save(util.dtoToOneOnOne(oneOnOneDto4));
                }
        }
    }
}

