package com.example.lab_backend.controller;

import com.example.lab_backend.model.Course;
import com.example.lab_backend.service.CourseService;
import com.example.lab_backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{courseCode}")
    public ResponseEntity<Course> getCourseByCourseCode(@PathVariable String courseCode) {
        return courseService.getCourseByCourseCode(courseCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Course>> getCoursesByName(@PathVariable String name) {
        return ResponseEntity.ok(courseService.getCoursesByName(name));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesByTeacher(@PathVariable Long teacherId) {
        return teacherService.getTeacherById(teacherId)
                .map(teacher -> ResponseEntity.ok(courseService.getCoursesByTeacher(teacher)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            boolean teacherExists = teacherService.existsById(course.getTeacher().getId());
            if (!teacherExists) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            boolean teacherExists = teacherService.existsById(course.getTeacher().getId());
            if (!teacherExists) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        course.setId(id);
        return ResponseEntity.ok(courseService.saveCourse(course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}