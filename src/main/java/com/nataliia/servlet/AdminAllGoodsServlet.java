package com.nataliia.servlet;

import com.nataliia.dao.GoodDao;
import com.nataliia.dao.UserDao;
import com.nataliia.model.Good;
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

@WebServlet(value = "/adminGoods")
public class AdminAllGoodsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminAllGoodsServlet.class);
    private GoodDao goodDao = new GoodDao();

    public void setGoodDao(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String urlToRedirect;
        logger.debug("Start of filter for adminGoods page");

        if ("admin".equals(role)) {
            List<Good> goods = goodDao.getGoods();
            request.setAttribute("goods", goods);
            urlToRedirect = "/allGoodsAdminPage.jsp";
            logger.debug("Filter has approved admin access for " + session.getAttribute("userId"));
        } else {
            request.setAttribute("message", "Ошибка. Войдите в систему снова.");
            urlToRedirect = "/index.jsp";
            logger.debug("Filter has denied access.");
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(request, response);
    }


}