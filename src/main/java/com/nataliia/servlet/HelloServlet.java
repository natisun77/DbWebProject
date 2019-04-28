package com.nataliia.servlet;

import com.nataliia.dao.UserDao;
import com.nataliia.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/hello")
public class HelloServlet extends HttpServlet {
    private static final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        userDao.addUser(new User(name, password));

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/continue.jsp");
        dispatcher.forward(req, resp);
    }
}