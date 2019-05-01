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
import java.util.List;

@WebServlet(value = "/")
public class HomeServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDao.getUsers();

        req.setAttribute("users", users);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/allUsers.jsp");
        dispatcher.forward(req, resp);
    }
}
