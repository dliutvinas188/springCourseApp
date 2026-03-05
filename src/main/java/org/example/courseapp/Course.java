package org.example.courseapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private String instructor;

    //Empty Constructor
    public Course(){}

    //Constructor with title and instructor
    public Course(String title, String instructor){
        this.title = title;
        this.instructor = instructor;
    }

    //Getters and setter for variables
    public Long getId(){
        return Id;
    }

    public String getTitle(){
        return title;
    }
    public String getInstructor(){
        return instructor;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

}
