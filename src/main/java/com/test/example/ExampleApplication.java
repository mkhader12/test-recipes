package com.test.example;

import com.test.example.db.User;
import com.test.example.db.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandlineRunner(UserRepository userRepository) throws Exception {

        return args -> {
            userRepository.deleteAllInBatch();
            ArrayList<User> users = new ArrayList<>();
            users.add(createUser("First", "Last"));
            users.add(createUser("Mr.", "Frost"));
            users.add(createUser("Santa", "Clause"));
            users.add(createUser("Peter", "Pan"));
            users.add(createUser("Cinder", "ella"));
            users.add(createUser("What", "ever"));
            userRepository.saveAll(users);
        };
    }

    private User createUser(String first, String last) {
        User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        return user;
    }
}
