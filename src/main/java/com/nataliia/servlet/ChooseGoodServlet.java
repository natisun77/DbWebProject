package com.nataliia.servlet;

import com.nataliia.dao.GoodDao;
import com.nataliia.dao.impl.GoodDaoHibImpl;
import com.nataliia.model.Good;
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

@WebServlet(value = "/chooseGood")
public class ChooseGoodServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ChooseGoodServlet.class);
    private static final GoodDao goodDaoHib = new GoodDaoHibImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        String urlToRedirect;

        if (userId != null) {
            Long goodId = Long.parseLong(request.getParameter("id"));
            Good goodFromDB = goodDaoHib.getById(Good.class, goodId);
            List<Good> cart = (List<Good>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }
            cart.add(goodFromDB);

            request.setAttribute("cart", cart);
            request.setAttribute("message", "Товар " + goodFromDB.getName() + " был добавлен в корзину.");
            List<Good> goods = goodDaoHib.getAll(Good.class);
            LOGGER.debug("Loading list of goods" + goods);
            request.setAttribute("goods", goods);
            urlToRedirect = "/allGoodsPage.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("message", "Ошибка. Войдите в систему.");
            urlToRedirect = "/index.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
