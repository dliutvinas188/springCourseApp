package org.example.courseapp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {
    @Bean
    CommandLineRunner initDatabase(CourseRepository repository){
        return args -> {
            repository.save(new Course("Spring Boot Basics","Mark"));
            repository.save(new Course("REST APIs","Alastair"));
        };
    }
}
