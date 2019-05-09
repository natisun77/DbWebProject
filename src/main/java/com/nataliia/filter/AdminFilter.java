package com.nataliia.filter;

import com.nataliia.model.User;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/adminPage", "/adminGoods", "/user", "/good"})

public class AdminFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        logger.debug("Start of filter for admin rights");
        if (user != null && "admin".equals(user.getRole())) {
            logger.debug("Admin rights for user with ID=" + user.getId() + " were confirmed.");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.debug("Access was denied.");
            request.setAttribute("message", "Ошибка доступа. Войдите в систему снова.");
            request.getRequestDispatcher("index.jsp").forward(request, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
