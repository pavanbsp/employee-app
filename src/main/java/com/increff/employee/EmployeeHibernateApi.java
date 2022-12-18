package com.increff.employee;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.List;
import java.sql.*;

public class EmployeeHibernateApi {

    private static Logger logger = Logger.getLogger(EmployeeHibernateApi.class);

    private HibernateUtil hbu;

    // Lets hope this works !!

    public EmployeeHibernateApi() {
        hbu = new HibernateUtil();
    }

    public void insert(EmployeePojo p) throws SQLException {
        logger.info("inserting employees");
        hbu.createSession();
        hbu.beginTransaction();
        Session s = hbu.getSession();
        s.save(p);
        hbu.commitTransaction();
        hbu.closeSession();

    }

    public EmployeePojo select(int id) throws SQLException {
        logger.info("selecting an employee");
        hbu.createSession();
        hbu.beginTransaction();
        Session s = hbu.getSession();
        EmployeePojo p = s.find(EmployeePojo.class, id);
        hbu.commitTransaction();
        hbu.closeSession();
        return p;
    }

    public List<EmployeePojo> selectAll() throws SQLException {
        logger.info("selecting all employees");
        hbu.createSession();
        hbu.beginTransaction();
        Session s = hbu.getSession();
        Query q = s.createQuery("select o from EmployeePojo o");
        List<EmployeePojo> list = q.getResultList();
        hbu.commitTransaction();
        hbu.closeSession();
        return list;
    }

    public void update(int id, EmployeePojo p) throws SQLException{
        //TODO implement this method
        logger.info("updating employees");
        hbu.createSession();
        hbu.beginTransaction();
        Session s = hbu.getSession();
        EmployeePojo d = s.find(EmployeePojo.class, id);
        s.delete(d);
        s.save(p);
        hbu.commitTransaction();
        hbu.closeSession();
    }

    public void delete(int id) throws SQLException {
        hbu.createSession();
        hbu.beginTransaction();

        Session s = hbu.getSession();
        EmployeePojo p = s.find(EmployeePojo.class, id);
        s.delete(p);

        hbu.commitTransaction();
        hbu.closeSession();

    }

    public void deleteAll() throws SQLException {

        hbu.createSession();
        hbu.beginTransaction();

        Session s = hbu.getSession();
        Query q = s.createQuery("select o from EmployeePojo o");
        List<EmployeePojo> list = q.getResultList();
        for (EmployeePojo p : list) {
            s.delete(p);
        }

        hbu.commitTransaction();
        hbu.closeSession();

    }

    public void printAll() throws SQLException {
        List<EmployeePojo> list = selectAll();
        for (EmployeePojo p : list) {
            logger.warn("Employee id: " + p.getId() + ", name: " + p.getName() + ", age: " + p.getAge());
        }
    }
}


