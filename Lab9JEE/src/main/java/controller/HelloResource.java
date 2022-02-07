package controller;

import repository.Student;
import repository.StudentRepository;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello-world")
public class HelloResource {
    @EJB
    StudentRepository studentRepository;

    @GET
    @Produces("application/json")
    public List<Student> hello() {
        return Student.getStudents();
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student post (Student student) {
        studentRepository.getAll().add(student);
        return student;
    }
}
