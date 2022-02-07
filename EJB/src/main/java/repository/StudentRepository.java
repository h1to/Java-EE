package repository;

import database.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> findAll();

    Student findByLastName(String lastName);
}
