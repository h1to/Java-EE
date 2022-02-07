package repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentRepositoryImpl implements StudentRepository{

    @Override
    public List<Student> getAll() {
        return Student.getStudents();
    }

    @Override
    public Optional<Student> findByName(String name) {
        List<Student> studentList = Student.getStudents();
        Optional<Student> studentOptional = studentList.stream().filter(s -> s.getFirstName().equals(name)).findFirst();
        return studentOptional;
    }

    @Override
    public Optional<Student> findBySurname(String surname) {
        List<Student> studentList = Student.getStudents();
        Optional<Student> studentOptional = studentList.stream().filter(s -> s.getLastName().equals(surname)).findFirst();
        return studentOptional;
    }

    @Override
    public Optional<Student> findByCity(String city) {
        List<Student> studentList = Student.getStudents();
        Optional<Student> studentOptional = studentList.stream().filter(s -> s.getCity().equals(city)).findFirst();
        return studentOptional;
    }

    @Override
    public List<Student> gpaMoreThan(Double gpa) {
        List<Student> studentList = Student.getStudents();
        return studentList.stream().filter(s -> s.getGpa() > gpa).collect(Collectors.toList());
    }

    @Override
    public List<Student> gpaLessThan(Double gpa) {
        List<Student> studentList = Student.getStudents();
        return studentList.stream().filter(s -> s.getGpa() < gpa).collect(Collectors.toList());
    }

    @Override
    public List<Student> gpaEquals(Double gpa) {
        List<Student> studentList = Student.getStudents();
        return studentList.stream().filter(s -> s.getGpa() == gpa).collect(Collectors.toList());
    }

    @Override
    public String hasScholarship(String name) {
        Optional<Student> studentOpt = findByName(name);
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


}
