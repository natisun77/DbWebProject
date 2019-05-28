package com.nataliia.servlet;

import com.nataliia.dao.GoodDao;
import com.nataliia.dao.OrderDao;
import com.nataliia.dao.UserDao;
import com.nataliia.dao.impl.GoodDaoHibImpl;
import com.nataliia.dao.impl.OrderDaoHibImpl;
import com.nataliia.dao.impl.UserDaoHibImpl;
import com.nataliia.model.Good;
import com.nataliia.model.Order;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/chooseGood")
public class ChooseGoodServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ChooseGoodServlet.class);
    private static final UserDao userDao = new UserDaoHibImpl();
    private static final GoodDao goodDao = new GoodDaoHibImpl();
    private static final OrderDao orderDao = new OrderDaoHibImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        User userFromDb = userDao.getById(User.class, userId);
        String urlToRedirect;

        if (userFromDb != null) {
            Long goodId = Long.parseLong(request.getParameter("id"));
            Good goodFromDB = goodDao.getById(Good.class, goodId);
            Order currentOrder = userFromDb.getOrder();
            if (currentOrder == null) {
                Order order = new Order(new ArrayList<>(List.of(goodFromDB)),userFromDb);
                orderDao.add(order);
            } else{
                currentOrder.getCart().add(goodFromDB);
                orderDao.update(userFromDb.getOrder());
            }
            List<Good> cart = currentOrder.getCart();
            request.setAttribute("cart", cart);
            request.setAttribute("message", "Товар " + goodFromDB.getName() + " был добавлен в заказ.");
            List<Good> goods = goodDao.getAll(Good.class);
            logger.debug("Loading list of goods" + goods);
            request.setAttribute("goods", goods);
            urlToRedirect = "/allGoodsPage.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
            dispatcher.forward(request, response);
            session.setAttribute("cart", cart);
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
