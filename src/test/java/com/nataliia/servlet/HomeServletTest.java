package com.nataliia.servlet;

import com.nataliia.dao.UserDao;
import com.nataliia.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class HomeServletTest {
    private HomeServlet homeServlet;
    private UserDao userDaoMock = Mockito.mock(UserDao.class);

    @Before
    public void init() {
        homeServlet = new HomeServlet();
        homeServlet.setUserDao(userDaoMock);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        homeServlet = Mockito.spy(homeServlet);
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);

        Mockito.when(userDaoMock.getUsers()).thenReturn(new ArrayList<>());
        ServletContext servletContextMock = Mockito.mock(ServletContext.class);
        Mockito.doReturn(servletContextMock).when(homeServlet).getServletContext();

        RequestDispatcher requestDispatcherMock = Mockito.mock(RequestDispatcher.class);
        Mockito.when(servletContextMock.getRequestDispatcher("/allUsers.jsp")).thenReturn(requestDispatcherMock);

        homeServlet.doGet(requestMock, responseMock);

        Mockito.verify(requestDispatcherMock, Mockito.times(1)).forward(requestMock, responseMock);
    }
}
