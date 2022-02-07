package database;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private LocalDate lastSubmissionDate;
    private Double gpa;
    private String city;
    private Boolean isHaveScholarship;


    public Student() {
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

        public StudentBuilder gpa(Double gpa) {
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

        public Student build() {
            return Student.this;
        }
    }


    public static List<Student> getStudents(){
        Student student1 = Student.studentBuilder()
                .lname("Ivanov")
                .fname("Ivan")
                .city("Almaty")
                .gpa(3.11)
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-21"))
                .build();

        Student student2 = Student.studentBuilder()
                .lname("Browning")
                .fname("Shanae")
                .city("Almaty")
                .gpa(2.91)
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-22"))
                .build();

        Student student3 = Student.studentBuilder()
                .lname("Larsen")
                .fname("Conah")
                .city("Pavlodar")
                .gpa(3.21)
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-21"))
                .build();

        Student student4 = Student.studentBuilder()
                .lname("Mccormick")
                .fname("Naseem")
                .city("Pavlodar")
                .gpa(3.91)
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-21"))
                .build();

        Student student5 = Student.studentBuilder()
                .lname("Lloyd")
                .fname("Jaime")
                .city("Oskemen")
                .gpa(2d)
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-30"))
                .build();

        Student student6 = Student.studentBuilder()
                .lname("Mcneill")
                .fname("Heather")
                .city("Shymkent")
                .gpa(2.33)
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-30"))
                .build();

        Student student7 = Student.studentBuilder()
                .lname("Mcneill")
                .fname("Bethany")
                .city("Shymkent")
                .gpa(2.50)
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-27"))
                .build();

        Student student8 = Student.studentBuilder()
                .lname("Glenn")
                .fname("Esmai")
                .city("Pavlodar")
                .gpa(3.58)
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-21"))
                .build();

        Student student9 = Student.studentBuilder()
                .lname("Pittman")
                .fname("Sherri")
                .city("Almaty")
                .gpa(2.33)
                .isHaveScholarship(false)
                .date(LocalDate.parse("2021-09-30"))
                .build();

        Student student10 = Student.studentBuilder()
                .lname("Finch")
                .fname("Conah")
                .city("Almaty")
                .gpa(2.73)
                .isHaveScholarship(true)
                .date(LocalDate.parse("2021-09-25"))
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

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
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
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastSubmissionDate=" + lastSubmissionDate +
                ", gpa=" + gpa +
                ", city='" + city + '\'' +
                ", isHaveScholarship=" + isHaveScholarship +
                '}';
    }

    public String FnLnGpa() {
        return "{" + firstName + ", " + lastName + ", " + gpa + "}";
    }

    public String key () {
        return firstName + lastName;
    }
}
