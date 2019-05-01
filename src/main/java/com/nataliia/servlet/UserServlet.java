package com.nataliia.servlet;

import com.nataliia.dao.UserDao;
import com.nataliia.exceptions.UserNotFoundException;
import com.nataliia.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/user")
public class UserServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new UserNotFoundException();
        }

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");

        if ("admin".equals(role)) {
            User user = userDao.getUser(id).orElseThrow(UserNotFoundException::new);
            req.setAttribute("user", user);
        }

        String urlToRedirect;
        if ("admin".equals(role)) {
            urlToRedirect = "/editUser.jsp";
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            urlToRedirect = "/index.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            doDelete(req, resp);
        } else if ("put".equals(action)) {
            doPut(req, resp);
        } else {
            resp.sendRedirect("/index.jsp");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");

        if ("admin".equals(role)) {
            Long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                throw new UserNotFoundException();
            }
            userDao.deleteUser(id);

            resp.sendRedirect("/adminPage");
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");

        if ("admin".equals(role)) {
            Long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                throw new UserNotFoundException();
            }

            userDao.updateUser(new User(id, name, password));

            resp.sendRedirect("/adminPage");
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}