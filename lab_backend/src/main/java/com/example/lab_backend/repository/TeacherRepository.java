package com.example.lab_backend.repository;

import com.example.lab_backend.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByNameContainingIgnoreCase(String name);
    List<Teacher> findBySurnameContainingIgnoreCase(String surname);
    List<Teacher> findByDepartment(String department);
}