package com.example.lab_backend.repository;

import com.example.lab_backend.model.CourseRegistration;
import com.example.lab_backend.model.Student;
import com.example.lab_backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByStudent(Student student);
    List<CourseRegistration> findByStudentAndStatus(Student student, String status);
    List<CourseRegistration> findByCourse(Course course);
    
    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.student.adviser.id = :adviserId AND cr.status = :status")
    List<CourseRegistration> findByStudentAdviserIdAndStatus(@Param("adviserId") Long adviserId, @Param("status") String status);
}