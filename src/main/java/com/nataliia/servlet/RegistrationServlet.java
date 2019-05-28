package com.nataliia.servlet;

import com.nataliia.dao.impl.UserDaoHibImpl;
import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegistrationServlet extends HttpServlet {
    private UserDaoHibImpl userDao = new UserDaoHibImpl();
    private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String salt = HashUtil.getRandomSalt();

        User user = new User(name, email, password, salt);
        String urlToRedirect;
        String message;
        LOGGER.debug("Start of new registration");
        if ((userDao.getUserByName(name)).get() != null) {
            urlToRedirect = "/index.jsp";
            message = "Вы успешно зарегистрировались. Войдите в систему.";
            LOGGER.debug("Successful registration of " + name);
        } else {
            urlToRedirect = "/registration.jsp";
            message = "Ошибка при регистрации. Попробуйте снова.";
            LOGGER.debug("Error during registration");
        }

        req.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(req, resp);
    }
}
