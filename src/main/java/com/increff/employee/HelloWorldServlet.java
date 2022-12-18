package com.increff.employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static java.lang.System.out;

@WebServlet(name = "HelloWorld", description = "sample servlet", urlPatterns = { "/employee" })
public class HelloWorldServlet extends HttpServlet {
    private static final long serialVersionUID = -2934514256330525834L;
    private static EmployeeHibernateApi api;

    static {
        HibernateUtil.configure();
        try {
            api = new EmployeeHibernateApi();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>Increff Employee</h1>");

        int id = Integer.valueOf(req.getParameter("id"));
        EmployeePojo p = null;
        try {
            p = api.select(id);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving object", e);
        }

        out.println("<br>");
        out.println("Name: " + p.getName());
        out.println("<br>");
        out.println("Age: " + p.getAge());
        out.println("<br>");
        out.println("Id: " + p.getId());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String id = req.getParameter("id");
        String age = req.getParameter("age");

        EmployeePojo p = new EmployeePojo();
        p.setAge(Integer.valueOf(age));
        p.setId(Integer.valueOf(id));
        p.setName(name);

        try {
            api.insert(p);
        } catch (SQLException e) {
            throw new ServletException("Error saving object", e);
        }

        }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String id = req.getParameter("id");
        String age = req.getParameter("age");
        String newid = req.getParameter("newid");

        int getid = Integer.valueOf(id);

        EmployeePojo p = new EmployeePojo();
        p.setAge(Integer.valueOf(age));
        p.setId(Integer.valueOf(newid));
        p.setName(name);
        try {
            api.update(getid, p);
        } catch (SQLException e) {
            throw new ServletException("Error updating object", e);
        }

    }

    // Deleting
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        out.println("<h1>Employee Deleted</h1>");
        int id = Integer.valueOf(req.getParameter("id"));
        try {
            api.delete(id);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving object", e);
        }

    }
}
