package org.example.courseapp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 50, message = "Title must be between 2 and 50 characters")
    private String title;

    @NotBlank(message = "Instructor is required")
    @Size(min = 2, max = 50, message = "Instructor name must be between 2 and 50 characters")
    private String instructor;
    
    @NotBlank(message = "Course code is required")
    @Column(unique = true)
    private String courseCode;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    public Course() {
    }

    public Course(String title, String instructor, String courseCode, String description) {
        this.title = title;
        this.instructor = instructor;
        this.courseCode = courseCode;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}