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
import java.util.Optional;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        Optional<User> userOptional = userDao.getUser(name, password);

        HttpSession session = req.getSession();

        if (userOptional.isPresent()) {
            User userFromDb = userOptional.get();
            session.setAttribute("userId", userFromDb.getId());
            session.setAttribute("role", userFromDb.getRole());
            if ("member".equals(userFromDb.getRole())) {
                resp.sendRedirect("/userInformation");
            } else if ("admin".equals(userFromDb.getRole())) {
                resp.sendRedirect("/adminPage");
            }
        } else {
            String message = "Неправильный логин или пароль. Попробуйте снова";
            req.setAttribute("message", message);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
