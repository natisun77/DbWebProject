package com.nataliia.servlet;

import com.nataliia.dao.GoodDaoHibImpl;
import com.nataliia.exceptions.GoodNotFoundException;
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

@WebServlet(value = "/good")
public class GoodServlet extends HttpServlet {
    private GoodDaoHibImpl goodDao = new GoodDaoHibImpl();
    private static final Logger logger = Logger.getLogger(GoodServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long goodId = getGoodIdFromRequest(request);
        Good good = goodDao.findGoodById(goodId);
        request.setAttribute("good", good);

        HttpSession session = request.getSession();
        logger.debug("Admin with ID=" + session.getAttribute("userId") + " edits good with ID" + goodId + ".");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editGood.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            doDelete(request, response);
        } else if ("put".equals(action)) {
            doPut(request, response);
        } else {
            HttpSession session = request.getSession();
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            logger.debug("Admin with ID=" + session.getAttribute("userId") + " adds new good" + name + ".");
            goodDao.addGood(new Good(name, description, price));
            response.sendRedirect("/adminGoods");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        long goodId = getGoodIdFromRequest(req);
        goodDao.updateGood(new Good(getGoodIdFromRequest(req), name, description, price));

        HttpSession session = req.getSession();
        logger.debug("Admin with ID=" + session.getAttribute("userId") + " updates good with " + goodId + ".");
        resp.sendRedirect("/adminGoods");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long goodId = getGoodIdFromRequest(req);
        goodDao.deleteGoodById(goodId);

        HttpSession session = req.getSession();
        logger.debug("Admin with ID=" + session.getAttribute("userId") + " deletes good with " + goodId + ".");
        resp.sendRedirect("/adminGoods");
    }

    private long getGoodIdFromRequest(HttpServletRequest req) {
        try {
            return Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new GoodNotFoundException();
        }
    }
}
