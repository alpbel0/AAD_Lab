package com.example.lab_backend.service;

import com.example.lab_backend.model.Teacher;
import com.example.lab_backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public List<Teacher> getTeachersByName(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Teacher> getTeachersBySurname(String surname) {
        return teacherRepository.findBySurnameContainingIgnoreCase(surname);
    }

    public List<Teacher> getTeachersByDepartment(String department) {
        return teacherRepository.findByDepartment(department);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return teacherRepository.existsById(id);
    }
}