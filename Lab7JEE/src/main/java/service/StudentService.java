package service;

import database.Student;
import repository.StudentRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class StudentService {

    private StudentRepository studentRepository;

    @Inject
    public StudentService(StudentRepository studentRepository){this.studentRepository = studentRepository;}

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findByLastName(String lastName) {
        return Optional.of(studentRepository.findByLastName(lastName));
    }
}
