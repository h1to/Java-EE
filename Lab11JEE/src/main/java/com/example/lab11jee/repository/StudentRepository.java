package com.example.lab11jee.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> getAll ();
    List<Student> findByName(String name, List<Student> studentList);
    List<Student> findBySurname(String surname, List<Student> studentList);
    List<Student> findByCity(String city, List<Student> studentList);
    List<Student> gpaMoreThan(BigDecimal gpa, List<Student> studentList);
    List<Student> gpaLessThan(BigDecimal gpa, List<Student> studentList);
    List<Student> gpaEquals(BigDecimal gpa, List<Student> studentList);
    Boolean hasScholarship(int id, List<Student> studentList);
    Optional<Student> findById (int id, List<Student> studentList);
}
