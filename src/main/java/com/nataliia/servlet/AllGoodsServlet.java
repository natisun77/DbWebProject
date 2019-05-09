package com.nataliia.servlet;

import com.nataliia.dao.GoodDao;
import com.nataliia.model.Good;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/goods")
public class AllGoodsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AdminServlet.class);
    private static final GoodDao goodDao = new GoodDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> goods = goodDao.getGoods();
        logger.debug("Loading list of goods" + goods);
        request.setAttribute("goods", goods);
        logger.debug("Loading list of goods" + goods);
        request.getRequestDispatcher("allGoodsPage.jsp").forward(request,response);
    }
}

