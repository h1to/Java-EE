package com.example.lab9jee_rest.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jetbrains.annotations.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "student")
@Entity
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;
    @NotNull
    private Boolean isHaveScholarship;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate lastSubmissionDate;
    @JsonFormat
    private BigDecimal gpa;
    private String city;

    public Student() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static StudentBuilder studentBuilder() {
        return new Student().new StudentBuilder();
    }

    public class StudentBuilder {
        private StudentBuilder() {
        }

        public StudentBuilder fname(String fname) {
            Student.this.firstName = fname;
            return this;
        }

        public StudentBuilder lname(String lname) {
            Student.this.lastName = lname;
            return this;
        }

        public StudentBuilder date(LocalDate date) {
            Student.this.lastSubmissionDate = date;
            return this;
        }

        public StudentBuilder gpa(BigDecimal gpa) {
            Student.this.gpa = gpa;
            return this;
        }

        public StudentBuilder city(String city) {
            Student.this.city = city;
            return this;
        }

        public StudentBuilder isHaveScholarship(Boolean isHaveScholarship) {
            Student.this.isHaveScholarship = isHaveScholarship;
            return this;
        }

        public StudentBuilder id(int id) {
            Student.this.id = id;
            return this;
        }

        public Student build() {
            return Student.this;
        }
    }

    public static List<Student> getStudents(){
        Student student1 = Student.studentBuilder()
                .id(1)
                .lname("Ivanov")
                .fname("Ivan")
                .city("Almaty")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student2 = Student.studentBuilder()
                .id(2)
                .lname("Browning")
                .fname("Shanae")
                .city("Almaty")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student3 = Student.studentBuilder()
                .id(3)
                .lname("Larsen")
                .fname("Conah")
                .city("Pavlodar")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student4 = Student.studentBuilder()
                .id(4)
                .lname("Mccormick")
                .fname("Naseem")
                .city("Pavlodar")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student5 = Student.studentBuilder()
                .id(5)
                .lname("Lloyd")
                .fname("Jaime")
                .city("Oskemen")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student6 = Student.studentBuilder()
                .id(6)
                .lname("Mcneill")
                .fname("Heather")
                .city("Shymkent")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student7 = Student.studentBuilder()
                .id(7)
                .lname("Mcneill")
                .fname("Bethany")
                .city("Shymkent")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student8 = Student.studentBuilder()
                .id(8)
                .lname("Glenn")
                .fname("Esmai")
                .city("Pavlodar")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student9 = Student.studentBuilder()
                .id(9)
                .lname("Pittman")
                .fname("Sherri")
                .city("Almaty")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        Student student10 = Student.studentBuilder()
                .id(10)
                .lname("Finch")
                .fname("Conah")
                .city("Almaty")
                .gpa(new BigDecimal("3.11").setScale(2))
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-12"))
                .build();

        return Arrays.asList(student1, student2, student3, student4,
                student5, student6, student7, student8, student9, student10);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getLastSubmissionDate() {
        return lastSubmissionDate;
    }

    public void setLastSubmissionDate(LocalDate lastSubmissionDate) {
        this.lastSubmissionDate = lastSubmissionDate;
    }

    public BigDecimal getGpa() {
        return gpa;
    }

    public void setGpa(BigDecimal gpa) {
        this.gpa = gpa;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getHaveScholarship() {
        return isHaveScholarship;
    }

    public void setHaveScholarship(Boolean haveScholarship) {
        isHaveScholarship = haveScholarship;
    }

    @Override
    public String toString() {
        return "{" +
                "\nfirstName='" + firstName + '\'' +
                ", \nlastName='" + lastName + '\'' +
                ", \nlastSubmissionDate=" + lastSubmissionDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                ", \ngpa=" + gpa +
                ", \ncity='" + city + '\'' +
                ", \nisHaveScholarship=" + isHaveScholarship +
                '}';
    }
}
