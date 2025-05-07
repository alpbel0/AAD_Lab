package com.example.lab_backend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String studentId;
    
    @Temporal(TemporalType.DATE)
    private Date enrollment;

    @ManyToOne
    @JoinColumn(name = "adviser_id")
    private Teacher adviser;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CourseRegistration> courseRegistrations;

    // Constructors
    public Student() {
    }

    public Student(String name, String surname, String email, String studentId, Date enrollment) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.studentId = studentId;
        this.enrollment = enrollment;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Date enrollment) {
        this.enrollment = enrollment;
    }

    public Teacher getAdviser() {
        return adviser;
    }

    public void setAdviser(Teacher adviser) {
        this.adviser = adviser;
    }

    public List<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    public void setCourseRegistrations(List<CourseRegistration> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }
}