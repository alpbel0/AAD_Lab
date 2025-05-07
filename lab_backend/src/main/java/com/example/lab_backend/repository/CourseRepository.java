package com.example.lab_backend.repository;

import com.example.lab_backend.model.Course;
import com.example.lab_backend.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByTeacher(Teacher teacher);
}