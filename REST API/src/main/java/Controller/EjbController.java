package Controller;

import Service.HelloService;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/controller")
public class EjbController extends HttpServlet {
    @EJB
    private HelloService helloService;

    public EjbController(HelloService helloService) {
        this.helloService = helloService;
    }

    public EjbController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.getWriter().write(helloService.getHello().stream().collect(Collectors.joining(", ")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
