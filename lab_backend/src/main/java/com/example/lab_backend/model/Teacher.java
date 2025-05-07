package com.example.lab_backend.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String department;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Course> courses;

    @OneToMany(mappedBy = "adviser")
    @JsonIgnore
    private List<Student> advisingStudents;

    // Constructors
    public Teacher() {
    }

    public Teacher(String name, String surname, String department) {
        this.name = name;
        this.surname = surname;
        this.department = department;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Student> getAdvisingStudents() {
        return advisingStudents;
    }

    public void setAdvisingStudents(List<Student> advisingStudents) {
        this.advisingStudents = advisingStudents;
    }
}