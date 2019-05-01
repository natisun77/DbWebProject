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
import java.util.Optional;

@WebServlet(value = "/userInformation")
public class UserInformationServlet extends HttpServlet {
    private UserDao userDao = new UserDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long idSession = (Long) req.getSession().getAttribute("userId");


        User user = null;
        if (idSession != null) {
            Optional<User> userOptional = userDao.getUser(idSession);
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
        }

        String urlToRedirect;
        if (user != null) {
            req.setAttribute("user", user);
            urlToRedirect = "/userInformation.jsp";
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            urlToRedirect = "/index.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(req, resp);
    }

}
