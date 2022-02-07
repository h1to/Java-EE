package controller;

import database.Student;
import repository.StudentRepositoryImpl;
import service.StudentService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/hello")
public class EjbController extends HttpServlet {
    @EJB
    private StudentService studentService;
    private StudentRepositoryImpl studentRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Student> optionalStudent = studentService.findByLastName("Ivanov");
        //Student student1 = studentRepository.findByLastName("Lloyd");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if(optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            writer.printf("<h1>First name: %s </h1>", student.getFirstName());
            writer.printf("<h1>Last name: %s </h1>", student.getLastName());
            writer.printf("<h1>City %s </h1>", student.getCity());
            writer.printf("<h1>GPA: %s </h1>", student.getGpa());
        } else {
            System.out.println("There is no student with surname 'Ivanov'");
        }

        //writer.printf("<h1>First name: %s </h1>", student1.getFirstName());
        //writer.printf("<h1>Last name: %s </h1>", student1.getLastName());
        //writer.printf("<h1>City %s </h1>", student1.getCity());
        //writer.printf("<h1>GPA: %s </h1>", student1.getGpa());

        writer.flush();
        writer.close();
    }
}
