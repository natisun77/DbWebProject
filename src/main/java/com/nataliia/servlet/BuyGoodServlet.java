package com.nataliia.servlet;

import com.nataliia.dao.CodeDao;
import com.nataliia.model.Code;
import com.nataliia.model.User;
import com.nataliia.service.MailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/buy")
public class BuyGoodServlet extends HttpServlet {

    private static final MailService mailService = new MailService();
    private static final CodeDao codeDao = new CodeDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Long goodId = Long.valueOf(request.getParameter("goodId"));
        String codeValue = request.getParameter("code");
        User user = (User) request.getSession().getAttribute("user");
        Code code = new Code(codeValue, user.getId(), goodId);
        if (codeDao.checkCode(code)) {
            response.getWriter().print("Оплата прошла");
        } else {
            response.getWriter().print("Оплата отменена");
        }
        ;
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
