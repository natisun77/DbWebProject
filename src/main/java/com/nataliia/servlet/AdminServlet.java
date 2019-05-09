package com.nataliia.servlet;

import com.nataliia.dao.UserDao;
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
import java.util.List;

@WebServlet(value = "/adminPage")
public class AdminServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AdminServlet.class);
    private UserDao userDao = new UserDao();

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        String urlToRedirect;
        logger.debug("Start of filter for admin page");

        if ("admin".equals(role)) {
            List<User> users = userDao.getUsers();
            req.setAttribute("users", users);
            urlToRedirect = "/allUsers.jsp";
            logger.debug("Filter has approved admin access for " + session.getAttribute("userId"));

        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            urlToRedirect = "/index.jsp";
            logger.debug("Filter has denied access.");
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(req, resp);
    }
}
