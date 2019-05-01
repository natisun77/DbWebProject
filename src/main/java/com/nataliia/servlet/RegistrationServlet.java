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

@WebServlet(value = "/register")
public class RegistrationServlet extends HttpServlet {
    private UserDao userDao = new UserDao();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User user = new User(name, password);
        String urlToRedirect = null;
        String message = null;
        if (userDao.addUser(user)){
            urlToRedirect = "/index.jsp";
            message = "Вы успешно зарегистрировались. Войдите в систему.";
        } else {
            urlToRedirect = "/registration.jsp";
            message = "Ошибка при регистрации. Попробуйте снова.";
        }

        req.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(req, resp);

    }
}
