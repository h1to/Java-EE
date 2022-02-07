package com.example.lab9jee_rest.repository;

import javax.enterprise.context.RequestScoped;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class StudentRepositoryImpl implements StudentRepository{

    @Override
    public List<Student> getAll() {
        return Student.getStudents();
    }

    @Override
    public List<Student> findByName(String name, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getFirstName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public List<Student> findBySurname(String surname, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getLastName().equals(surname)).collect(Collectors.toList());
    }

    @Override
    public List<Student> findByCity(String city, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getCity().equals(city)).collect(Collectors.toList());
    }

    @Override
    public List<Student> gpaMoreThan(BigDecimal gpa, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getGpa().compareTo(gpa) == 1 ).collect(Collectors.toList());
    }

    @Override
    public List<Student> gpaLessThan(BigDecimal gpa, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getGpa().compareTo(gpa) == -1).collect(Collectors.toList());
    }

    @Override
    public List<Student> gpaEquals(BigDecimal gpa, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getGpa().compareTo(gpa) == 0).collect(Collectors.toList());
    }

    @Override
    public String hasScholarship(int id, List<Student> studentList) {
        Optional<Student> studentOpt = findById(id, studentList);
        if(studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (student.getHaveScholarship()) {
                return "Has scholarship";
            }
            else {
                return "Has no scholarship";
            }
        }
        else {
            return "There is no student with such name";
        }
    }

    @Override
    public Optional<Student> findById(int id, List<Student> studentList) {
        return studentList.stream().filter(s -> s.getId() == id).findFirst();
    }
}
