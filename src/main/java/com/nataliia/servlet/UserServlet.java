package com.nataliia.servlet;

import com.nataliia.dao.UserDao;
import com.nataliia.exceptions.UserNotFoundException;
import com.nataliia.model.User;
import org.apache.log4j.Logger;

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
    private static final Logger logger = Logger.getLogger(AdminServlet.class);

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = getUserByIdFromRequest(req);
        User user = userDao.getUser(id).orElseThrow(UserNotFoundException::new);
        logger.debug(user.getName() + " asked for user information using ID " + "as" + user.getRole());
        req.setAttribute("user", user);

        HttpSession session = req.getSession();
        logger.debug("Admin with ID=" + session.getAttribute("userId") + " edits user");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editUser.jsp");
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
            HttpSession session = req.getSession();
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            logger.debug("Admin with ID=" + session.getAttribute("userId") + " adds new good" + name + ".");
            User user = new User(name, email, password);
            userDao.addUser(user);
            resp.sendRedirect("/adminPage");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = getUserByIdFromRequest(req);
        userDao.deleteUser(id);
        HttpSession session = req.getSession();
        logger.debug("Admin with ID=" + session.getAttribute("userId") + " deletes user.");
        resp.sendRedirect("/adminPage");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");
        long id = getUserByIdFromRequest(req);
        userDao.updateUser(new User(id, name, password));

        HttpSession session = req.getSession();
        logger.debug("Admin with ID=" + session.getAttribute("userId") + " updates user with " + id + ".");
        resp.sendRedirect("/adminPage");
    }

    private long getUserByIdFromRequest(HttpServletRequest request) {
        try {
            return Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new UserNotFoundException();
        }
    }
}
