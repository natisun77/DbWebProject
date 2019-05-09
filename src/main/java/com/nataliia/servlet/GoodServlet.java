package com.nataliia.servlet;

import com.nataliia.dao.GoodDao;
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
    private GoodDao goodDao = new GoodDao();
    private static final Logger logger = Logger.getLogger(GoodServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new GoodNotFoundException();
        }
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if ("admin".equals(role)) {
            Good good = goodDao.getGoodById(id).orElseThrow(GoodNotFoundException::new);
            request.setAttribute("good", good);
            logger.debug("Admin with ID=" + session.getAttribute("userId") + " edits good with ID" + id + ".");
        }
        String urlToRedirect;
        if ("admin".equals(role)) {
            logger.debug("Admin with ID=" + session.getAttribute("userId") + " edits good with ID" + id + ".");
            urlToRedirect = "/editGood.jsp";
        } else {
            request.setAttribute("message", "Ошибка. Войдите в систему снова.");
            logger.debug("Access error");
            urlToRedirect = "/index.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlToRedirect);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            doDelete(request, response);
        } else if ("put".equals(action)) {
            doPut(request, response);
        } else {
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("role");
            if ("admin".equals(role)) {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Double price = Double.parseDouble(request.getParameter("price"));
                logger.debug("Admin with ID=" + session.getAttribute("userId") + " adds new good" + name + ".");
                Good good = new Good(name, description, price);
                goodDao.addGood(good);
                response.sendRedirect("/adminGoods");
            } else {
                request.setAttribute("message", "Ошибка. Войдите в систему снова.");
                logger.debug("Access error");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Double price = Double.parseDouble(req.getParameter("price"));

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        if ("admin".equals(role)) {
            Long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                throw new GoodNotFoundException();
            }
            goodDao.updateGood(new Good(id, name, description, price));
            logger.debug("Admin with ID=" + session.getAttribute("userId") + " deletes good with " + id + ".");

            resp.sendRedirect("/adminGoods");
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            logger.debug("Access error");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        if ("admin".equals(role)) {
            Long id;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                throw new GoodNotFoundException();
            }
            goodDao.deleteGood(id);
            logger.debug("Admin with ID=" + session.getAttribute("userId") + " deletes good with " + id + ".");

            resp.sendRedirect("/adminGoods");
        } else {
            req.setAttribute("message", "Ошибка. Войдите в систему снова.");
            logger.debug("Access error");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
