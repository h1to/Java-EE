package com.example.lab4jee;

import java.io.*;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "servlet1", value = "/servlet1")
public class servlet1 extends HttpServlet {
    private String message;

    public void init() {
        message = "Laboratory work 4!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
        out.close();
    }

    public void destroy() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        String fName = request.getParameter("fName");
        String lName = request.getParameter("lName");
        String group = request.getParameter("group");
        String email = request.getParameter("emailId");

        PrintWriter writer = response.getWriter();

        String htmlRespone = "<html>";
        htmlRespone += "<h1>Your first name is: " + fName + "</h1>";
        htmlRespone += "<h1>Your last name is: " + lName + "</h1>";
        htmlRespone += "<h1>Your group is: " + group + "</h1>";
        htmlRespone += "<h1>Your email is: " + email + "</h1>";
        htmlRespone += "<a href = " + request.getContextPath() + "/servlet2>" + " Servlet 2</a>";
        htmlRespone += "</html>";

        writer.println(htmlRespone);
        writer.close();

        HttpSession session = request.getSession();
        session.setAttribute("fName", fName);
        session.setAttribute("lName", lName);
        session.setAttribute("group", group);
        session.setAttribute("email", email);

        getServletContext().getRequestDispatcher("/servlet2").forward(request, response);
    }
}