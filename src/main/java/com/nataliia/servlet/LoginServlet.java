package com.nataliia.servlet;

import com.nataliia.dao.UserDao;
import com.nataliia.dao.UserDaoHibImpl;
import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private UserDaoHibImpl userDao = new UserDaoHibImpl();
    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameFromForm = req.getParameter("name");
        String passwordFromForm = req.getParameter("password");
        User userFromDb = userDao.findUserByName(nameFromForm);

        HttpSession session = req.getSession();
        logger.debug("Start of login");

        if (userFromDb != null) {
            String hashPasswordFromForm = HashUtil.getSHA512SecurePassword(passwordFromForm, userFromDb.getSalt());
            if (userFromDb.getPassword().equals(hashPasswordFromForm)) {
                session.setAttribute("user", userFromDb);
                session.setAttribute("userId", userFromDb.getId());
                session.setAttribute("role", userFromDb.getRole());
            }

            if ("member".equals(userFromDb.getRole())) {
                logger.debug(nameFromForm + " entered system as member");
                resp.sendRedirect("/goods");
            } else if ("admin".equals(userFromDb.getRole())) {
                logger.debug(nameFromForm + " entered system as admin");
                resp.sendRedirect("/adminPage");
            }
        } else {
            String message = "Неправильный логин или пароль. Попробуйте снова";
            logger.debug(nameFromForm + " did not enter system. Name or password error.");
            req.setAttribute("message", message);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
