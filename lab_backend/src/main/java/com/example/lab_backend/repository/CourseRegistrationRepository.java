package com.example.lab_backend.repository;

import com.example.lab_backend.model.CourseRegistration;
import com.example.lab_backend.model.Student;
import com.example.lab_backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByStudent(Student student);
    List<CourseRegistration> findByStudentAndStatus(Student student, String status);
    List<CourseRegistration> findByCourse(Course course);
    List<CourseRegistration> findByStudentAdviserIdAndStatus(Long adviserId, String status);
} 