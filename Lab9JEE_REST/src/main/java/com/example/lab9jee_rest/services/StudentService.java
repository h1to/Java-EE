package com.example.lab9jee_rest.services;

import com.example.lab9jee_rest.repository.Student;
import com.example.lab9jee_rest.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Stateless
@RunWith(JUnit4.class)
public class StudentService {

    @Inject
    private StudentRepository studentRepository;

    public StudentService() {
    }

    @Test
    public List<Student> getAll() {
        return studentRepository.getAll();
    }

    @Test
    public List<Student> findBySurname(String surname, List<Student> studentList) {
        return studentRepository.findBySurname(surname, studentList);
    }

    @Test
    public List<Student> findByCity(String city, List<Student> studentList) {
        return studentRepository.findByCity(city, studentList);
    }

    @Test
    public List<Student> gpaMoreThan(BigDecimal gpa, List<Student> studentList) {
        return studentRepository.gpaMoreThan(gpa, studentList);
    }

    @Test
    public List<Student> gpaLessThan(BigDecimal gpa, List<Student> studentList) {
        return studentRepository.gpaLessThan(gpa, studentList);
    }

    @Test
    public List<Student> gpaEquals(BigDecimal gpa, List<Student> studentList) {
        return studentRepository.gpaEquals(gpa, studentList);
    }

    @Test
    public String hasScholarship(int id, List<Student> studentList) {
        return studentRepository.hasScholarship(id, studentList);
    }

    @Test
    public Optional<Student> findById (int id, List<Student> studentList) {
        return  studentRepository.findById(id, studentList);
    }
}
