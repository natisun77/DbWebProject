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
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/userInformation")
public class UserInformationServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private static final Logger logger = Logger.getLogger(AdminServlet.class);

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
            logger.debug(user.getName() + " -member gets user information.");
            urlToRedirect = "/allGoodsPage.jsp";
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            logger.debug("Error during login");
            urlToRedirect = "/index.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(req, resp);
    }
}
