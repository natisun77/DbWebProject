package com.nataliia.servlet;

import com.nataliia.dao.CodeDao;
import com.nataliia.dao.impl.CodeDaoHibImpl;
import com.nataliia.model.Code;
import com.nataliia.model.Good;
import com.nataliia.model.User;
import com.nataliia.service.MailService;

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

@WebServlet(value = "/buy")
public class BuyGoodServlet extends HttpServlet {

    private static final MailService MAIL_SERVICE = new MailService();
    private static final CodeDao codeDao = new CodeDaoHibImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            String code = request.getParameter("code");

            if (codeDao.isValidCode(code, userId)) {
                session.setAttribute("cart", new ArrayList<Good>());
                response.getWriter().print("Оплата прошла");
            } else {
                response.getWriter().print("Оплата отменена");
            }
        } else {
            request.setAttribute("message", "Ошибка. Войдите в систему.");
            String urlToRedirect = "/index.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> cart = (List<Good>) request.getSession().getAttribute("cart");
        String order = cart.toString();

        User user = (User) request.getSession().getAttribute("user");
        String randomCode = MAIL_SERVICE.sendMailWithCode(user.getEmail());
        Code code = new Code(randomCode, user.getId(), order);
        codeDao.add(code);
        request.getRequestDispatcher("buyConfirmation.jsp").forward(request, response);
    }
}
