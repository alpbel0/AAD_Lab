package com.example.lab_backend.controller;

import com.example.lab_backend.model.Course;
import com.example.lab_backend.model.CourseRegistration;
import com.example.lab_backend.model.Student;
import com.example.lab_backend.service.CourseRegistrationService;
import com.example.lab_backend.service.CourseService;
import com.example.lab_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class CourseRegistrationController {

    private final CourseRegistrationService registrationService;
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public CourseRegistrationController(
            CourseRegistrationService registrationService,
            StudentService studentService,
            CourseService courseService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseRegistration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseRegistration> getRegistrationById(@PathVariable Long id) {
        return registrationService.getRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getRegistrationsByStudentId(@PathVariable Long studentId) {
        Optional<Student> optionalStudent = studentService.getStudentById(studentId);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = optionalStudent.get();
        Map<String, Object> response = new HashMap<>();
        response.put("allCourses", courseService.getAllCourses());
        response.put("pendingRegistrations", registrationService.getPendingRegistrationsByStudent(student));
        response.put("approvedRegistrations", registrationService.getApprovedRegistrationsByStudent(student));
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/adviser/{adviserId}")
    public ResponseEntity<Map<String, Object>> getDataForAdviser(@PathVariable Long adviserId) {
        Map<String, Object> response = new HashMap<>();
        response.put("pendingRegistrations", registrationService.getPendingRegistrationsByAdviserId(adviserId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CourseRegistration> registerCourse(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        
        Optional<Student> optionalStudent = studentService.getStudentById(studentId);
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        
        if (optionalStudent.isEmpty() || optionalCourse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        CourseRegistration registration = 
                registrationService.createRegistration(optionalStudent.get(), optionalCourse.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CourseRegistration> updateRegistrationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        
        CourseRegistration updated = registrationService.updateRegistrationStatus(id, status);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        if (registrationService.getRegistrationById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
} 