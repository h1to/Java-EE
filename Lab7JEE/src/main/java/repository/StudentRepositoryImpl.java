package repository;

import database.Student;

import javax.ejb.Stateful;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
public class StudentRepositoryImpl implements StudentRepository{
    @Override
    public List<Student> findAll() {
        List<Student> studentList = Student.getStudents();
        return studentList;
    }

    @Override
    public Student findByLastName(String lastName) {
        List<Student> studentList = Student.getStudents();
        List<Student> student = (List<Student>) studentList.stream().filter(s ->
                s.getLastName().equals(lastName)).collect(Collectors.toList());
        return student.get(0);
    }
}
