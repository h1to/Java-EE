package com.example.lab9jee_rest.controller;

import com.example.lab9jee_rest.repository.Student;
import com.example.lab9jee_rest.repository.StudentRepositoryImpl;
import com.example.lab9jee_rest.services.StudentService;

import javax.ejb.EJB;
import javax.management.Notification;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/hello-world")
public class HelloResource {
    @EJB
    StudentService studentService;
    @EJB
    private static final List<Student> studentList = new ArrayList<>();

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    @Context
    HttpHeaders httpHeaders;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNotifications() {
        studentList.addAll(studentService.getAll());
        return Response.ok().entity(studentList).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get-students")
    public Response getAllStudents() {
        if (studentList.size() == 0 || studentList.isEmpty()) {
            return Response.ok().entity("There is no any students!").build();
        }
        else {
            return Response.ok().entity(studentList).build();
        }
    }

    @POST
    @Path("/post-student")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postedStudent (Student student) {
        if (studentService.findById(student.getId(), studentList).isPresent()) {
            return Response.ok().entity("Student with ID:" + student.getId() + " already exists!!!").build();
        }
        else {
            studentList.add(student);
            return Response.ok().entity("New student added: " + student.toString()).build();
        }
    }

    @GET
    @Path("/find-surname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBySurname(@QueryParam("surname") String surname) {
        if (studentService.findBySurname(surname, studentList).size() == 0
                || studentService.findBySurname(surname, studentList).isEmpty()) {
            return Response.ok().entity("There is no students with surname = " + surname + "!").build();
        }
        else {
            return Response.ok().entity(studentService.findBySurname(surname, studentList)).build();
        }
    }

    @PUT
    @Path("/edit-param/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response editStudent (
            @PathParam("id") int id,
            @FormParam("param") String param,
            @FormParam("value") String value) {
        Optional<Student> student = studentService.findById(id, studentList);
        int index;
        if (student.isPresent()) {
            index = studentList.indexOf(student.get());
        }
        else {
            return Response.ok().entity("There is no student with id = " + id).build();
        }

        if (param.equalsIgnoreCase("name") || param.equalsIgnoreCase("surname") || param.
                equalsIgnoreCase("city")) {
            if (param.equalsIgnoreCase("name")) {
                studentList.get(index).setFirstName(value);
            }
            else if (param.equalsIgnoreCase("surname")) {
                studentList.get(index).setLastName(value);
            }
            else {
                studentList.get(index).setCity(value);
            }
        }
        else if (param.equalsIgnoreCase("scholarship")) {
            studentList.get(index).setHaveScholarship(Boolean.parseBoolean(value));
        }
        else if (param.equalsIgnoreCase("gpa")) {
            studentList.get(index).setGpa(new BigDecimal(value).setScale(2));
        }
        else if (param.equalsIgnoreCase("subDate")) {
            studentList.get(index).setLastSubmissionDate(LocalDate.parse(value));
        }
        else {
            return Response.ok().entity("There is no such parameter!!!").build();
        }
        return Response.ok().entity("Changes saved!").build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent (@PathParam("id") int id) {
        Optional<Student> student = studentService.findById(id, studentList);
        if (student.isPresent()) {
            studentList.remove(student.get());
            return Response.ok().entity("Student deleted!").build();
        }
        else {
            return Response.ok().entity("There is no student with id = " + id).build();
        }
    }

    @PUT
    @Path("/edit-name/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response editName (@PathParam("id") int id,
                              @FormParam("name") String name) {
        Student student = studentService.findById(id, studentList).get();
        studentList.get(studentList.indexOf(student)).setFirstName(name);
        return Response.ok().entity("Edited!").build();
    }

    @PUT
    @Path("/edit-surname/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response editSurname (@PathParam("id") int id,
                              @FormParam("name") String surname) {
        Student student = studentService.findById(id, studentList).get();
        studentList.get(studentList.indexOf(student)).setLastName(surname);
        return Response.ok().entity("Edited!").build();
    }

    @DELETE
    @Path("/delete-by-surname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent (@FormParam("name") String surname) {
        List<Student> studentToDelete = studentService.findBySurname(surname, studentList);
        if (!studentToDelete.isEmpty()) {
            studentList.removeAll(studentToDelete);
            return Response.ok().entity("Student deleted!").build();
        }
        else {
            return Response.ok().entity("There are no students with surname = " + surname).build();
        }
    }

    @GET
    @Path("/find-city")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCity(@QueryParam("city") String city) {
        if (studentService.findByCity(city, studentList).size() == 0
                || studentService.findByCity(city, studentList).isEmpty()) {
            return Response.ok().entity("There is no students with city = " + city + "!").build();
        }
        else {
            return Response.ok().entity(studentService.findBySurname(city, studentList)).build();
        }
    }

    @GET
    @Path("/get-moreThan")
    public Response getGpaMoreThan (@QueryParam("gpa") BigDecimal gpa) {
        return Response.ok().entity(studentService.gpaMoreThan(gpa, studentList)).build();
    }

    @GET
    @Path("/get-lessThan")
    public Response getGpaLessThan (@QueryParam("gpa") BigDecimal gpa) {
        return Response.ok().entity(studentService.gpaLessThan(gpa, studentList)).build();
    }

    @GET
    @Path("/get-gpa")
    public Response getGpa (@QueryParam("gpa") BigDecimal gpa) {
        return Response.ok().entity(studentService.gpaEquals(gpa, studentList)).build();
    }
}