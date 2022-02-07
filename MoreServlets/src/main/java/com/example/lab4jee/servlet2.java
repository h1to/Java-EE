package com.example.lab4jee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servlet2", value = "/servlet2")
public class servlet2 extends HttpServlet {
    private String message;

    public void init() {
        message = "Second servlet page!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");

        HttpSession session = request.getSession();

        String fName = (String) session.getAttribute("fName");
        String lName = (String) session.getAttribute("lName");
        String group = (String) session.getAttribute("group");
        String email = (String) session.getAttribute("email");

        String htmlRespone = "<h1>" + fName + "</h1>";
        htmlRespone += "<h1>" + lName + "</h1>";
        htmlRespone += "<h1>" + group + "</h1>";
        htmlRespone += "<h1>" + email + "</h1>";
        htmlRespone += "</body></html>";
        out.println(htmlRespone);
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {

    }
}
