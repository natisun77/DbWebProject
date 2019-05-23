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
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/adminGoods")
public class AdminAllGoodsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AdminAllGoodsServlet.class);
    private GoodDao goodDao = new GoodDaoHibImpl();

    public void setGoodDao(GoodDaoHibImpl goodDao) {
        this.goodDao = goodDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> goods = goodDao.getAll(Good.class);
        request.setAttribute("goods", goods);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/allGoodsAdminPage.jsp");
        dispatcher.forward(request, response);
    }
}
