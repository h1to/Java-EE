package com.example.lab11jee;

import com.example.lab11jee.repository.Student;
import com.example.lab11jee.services.Service;
import com.example.lab11jee.services.StudentService;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

@Path("/hello-world")
public class HelloResource {
    @EJB
    Service service;
    @EJB
    StudentService studentService;
    @EJB
    private static final List<Student> studentList = new ArrayList<>();

    @GET
    @Produces("application/json")
    public List<Student> hello() {
        return Student.getStudents();
    }

    @POST
    @Path("/jms")
    public Response sendMessage(Student student){
        if (studentList.stream().filter(s -> s.getId() == student.getId()).findFirst().isPresent()) {
            return Response.ok().entity("Student with id = " + student.getId() + " already exists!").build();
        }
        else {
            return Response.ok().entity(service.sendJmsMessage(student)).build();
        }
    }

    @GET
    @Path("/jms")
    @Produces("application/json")
    public Response getMessage() {
        //Student student = service.getMessage();
        //if (student.getId() == 0) {
            //return Response.ok().entity("Empty message!").build();
        //}
        //else {
            //studentList.add(student);
            //return Response.ok().entity(student).build();
        //}
        return Response.ok().entity("Received message: " + service.getMessage()).build();
    }

    @GET
    @Path("/getAllMessages")
    @Produces("application/json")
    public Response getAllMessage() throws JMSException {
        //List<String> allMessage = service.getAll();
        return  Response.ok().entity("All messages: ").build(); // + allMessage).build();
    }
}