package com.nataliia.servlet;

import com.nataliia.dao.GoodDao;
import com.nataliia.dao.impl.GoodDaoHibImpl;
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
    private static final Logger LOGGER = Logger.getLogger(AdminServlet.class);
    private static final GoodDao GOOD_DAO_HIB = new GoodDaoHibImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> goods = GOOD_DAO_HIB.getAll(Good.class);
        LOGGER.debug("Loading list of goods" + goods);
        request.setAttribute("goods", goods);
        LOGGER.debug("Loading list of goods" + goods);
        request.getRequestDispatcher("allGoodsPage.jsp").forward(request,response);
    }
}

