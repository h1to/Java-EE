package com.example.lab11jee.services;

import com.example.lab11jee.repository.Student;
import com.example.lab11jee.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

@Stateful
@RunWith(JUnit4.class)
public class StudentService {

    @Inject
    private StudentRepository studentRepository;

    public StudentService() {
    }

    @Test
    public void getAll(List<Student> studentList) {
        studentList = studentRepository.getAll();
        assertEquals(studentList, studentRepository.getAll());
    }

    @Test
    public void findBySurname(String surname, List<Student> studentList) {
        List<Student> actual = studentRepository.findBySurname(surname, studentList);
        List<Student> expected = studentList.stream().filter(s -> s.getLastName().equals(surname)).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void findByCity(String city, List<Student> studentList) {
        List<Student> actual = studentRepository.findByCity(city, studentList);
        List<Student> expected = studentList.stream().filter(s -> s.getCity().equals(city)).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void gpaMoreThan(BigDecimal gpa, List<Student> studentList) {
        List<Student> actual = studentRepository.gpaMoreThan(gpa, studentList);
        List<Student> expected = studentList.stream().filter(s -> s.getGpa().compareTo(gpa) == 1).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void gpaLessThan(BigDecimal gpa, List<Student> studentList) {
        List<Student> actual = studentRepository.gpaLessThan(gpa, studentList);
        List<Student> expected = studentList.stream().filter(s -> s.getGpa().compareTo(gpa) == -1).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void gpaEquals(BigDecimal gpa, List<Student> studentList) {
        List<Student> actual = studentRepository.gpaEquals(gpa, studentList);
        List<Student> expected = studentList.stream().filter(s -> s.getGpa().compareTo(gpa) == 0).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void hasScholarship(Integer id, List<Student> studentList) {
        Boolean actual = studentRepository.hasScholarship(id, studentList);
        Boolean expected = studentRepository.findById(id, studentList).get().getHaveScholarship();
        assertEquals(expected, actual);
    }

    @Test
    public void findById (Integer id, List<Student> studentList) {
        Student actual = studentRepository.findById(id, studentList).get();
        Student expected = studentList.stream().filter(s -> s.getId() == id).findFirst().get();
        assertEquals(expected, actual);
    }
}
