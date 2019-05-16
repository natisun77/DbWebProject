package com.nataliia.servlet;

import com.nataliia.dao.CodeDao;
import com.nataliia.dao.CodeDaoHibImpl;
import com.nataliia.model.Code;
import com.nataliia.model.User;
import com.nataliia.service.MailService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/buy")
public class BuyGoodServlet extends HttpServlet {

    private static final MailService mailService = new MailService();
    private static final CodeDaoHibImpl codeDao = new CodeDaoHibImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");

        if (userId != null) {
            Long goodId = Long.valueOf(request.getParameter("goodId"));
            String code = request.getParameter("code");

            if (codeDao.isValidCode(code, userId, goodId)) {
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
        Long goodId = Long.parseLong(request.getParameter("id"));

        User user = (User) request.getSession().getAttribute("user");
        String randomCode = mailService.sendMailWithCode(user.getEmail());
        Code code = new Code(randomCode, user.getId(), goodId);
        codeDao.addCode(code);
        request.setAttribute("goodId", goodId);

        request.getRequestDispatcher("buyConfirmation.jsp").forward(request, response);
    }
}
