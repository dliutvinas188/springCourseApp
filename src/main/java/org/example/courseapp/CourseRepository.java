package org.example.courseapp;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    java.util.Optional<Course> findByCourseCode(String courseCode);
}
