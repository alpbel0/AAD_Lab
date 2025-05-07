package com.example.lab_backend.controller;

import com.example.lab_backend.model.Teacher;
import com.example.lab_backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Teacher>> searchTeachers(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        String surname = params.get("surname");
        String department = params.get("department");

        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(teacherService.getTeachersByName(name));
        } else if (surname != null && !surname.isEmpty()) {
            return ResponseEntity.ok(teacherService.getTeachersBySurname(surname));
        } else if (department != null && !department.isEmpty()) {
            return ResponseEntity.ok(teacherService.getTeachersByDepartment(department));
        } else {
            return ResponseEntity.ok(teacherService.getAllTeachers());
        }
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.saveTeacher(teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        return teacherService.getTeacherById(id)
                .map(existingTeacher -> {
                    teacher.setId(id);
                    return ResponseEntity.ok(teacherService.saveTeacher(teacher));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        if (teacherService.existsById(id)) {
            teacherService.deleteTeacher(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}