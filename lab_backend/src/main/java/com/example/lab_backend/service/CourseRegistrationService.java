package com.example.lab_backend.service;

import com.example.lab_backend.model.Course;
import com.example.lab_backend.model.CourseRegistration;
import com.example.lab_backend.model.Student;
import com.example.lab_backend.repository.CourseRegistrationRepository;
import com.example.lab_backend.repository.CourseRepository;
import com.example.lab_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseRegistrationService(
            CourseRegistrationRepository courseRegistrationRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<CourseRegistration> getAllRegistrations() {
        return courseRegistrationRepository.findAll();
    }

    public Optional<CourseRegistration> getRegistrationById(Long id) {
        return courseRegistrationRepository.findById(id);
    }

    public List<CourseRegistration> getRegistrationsByStudent(Student student) {
        return courseRegistrationRepository.findByStudent(student);
    }

    public List<CourseRegistration> getPendingRegistrationsByStudent(Student student) {
        return courseRegistrationRepository.findByStudentAndStatus(student, "PENDING");
    }

    public List<CourseRegistration> getApprovedRegistrationsByStudent(Student student) {
        return courseRegistrationRepository.findByStudentAndStatus(student, "APPROVED");
    }

    public List<CourseRegistration> getRegistrationsByCourse(Course course) {
        return courseRegistrationRepository.findByCourse(course);
    }

    public List<CourseRegistration> getPendingRegistrationsByAdviserId(Long adviserId) {
        return courseRegistrationRepository.findByStudentAdviserIdAndStatus(adviserId, "PENDING");
    }

    public CourseRegistration createRegistration(Student student, Course course) {
        CourseRegistration registration = new CourseRegistration();
        registration.setStudent(student);
        registration.setCourse(course);
        registration.setRegistrationDate(new Date());
        registration.setStatus("PENDING");
        return courseRegistrationRepository.save(registration);
    }

    public CourseRegistration updateRegistrationStatus(Long id, String status) {
        Optional<CourseRegistration> optionalRegistration = courseRegistrationRepository.findById(id);
        if (optionalRegistration.isPresent()) {
            CourseRegistration registration = optionalRegistration.get();
            registration.setStatus(status);
            return courseRegistrationRepository.save(registration);
        }
        return null;
    }

    public void deleteRegistration(Long id) {
        courseRegistrationRepository.deleteById(id);
    }

    // Test amaçlı onay bekleyen kayıt oluşturma metodu
    public boolean createTestPendingRegistration(Long studentId, Long adviserId, Long courseId) {
        try {
            Optional<Student> studentOptional = studentRepository.findById(studentId);
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            
            if (studentOptional.isPresent() && courseOptional.isPresent()) {
                Student student = studentOptional.get();
                Course course = courseOptional.get();
                
                CourseRegistration registration = new CourseRegistration();
                registration.setStudent(student);
                registration.setCourse(course);
                registration.setRegistrationDate(new Date());
                registration.setStatus("PENDING");
                courseRegistrationRepository.save(registration);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
} 