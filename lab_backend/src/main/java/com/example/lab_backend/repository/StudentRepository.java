package com.example.lab_backend.repository;

import com.example.lab_backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByEmail(String email);
    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findBySurnameContainingIgnoreCase(String surname);
}