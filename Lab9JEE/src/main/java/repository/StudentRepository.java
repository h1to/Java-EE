package repository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> getAll ();
    Optional<Student> findByName(String name);
    Optional<Student> findBySurname(String surname);
    Optional<Student> findByCity(String city);
    List<Student> gpaMoreThan(Double gpa);
    List<Student> gpaLessThan(Double gpa);
    List<Student> gpaEquals(Double gpa);
    String hasScholarship(String name);

}
