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
import java.util.List;

import static com.nataliia.servlet.HomeServlet.userDao;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class HomeServletTest {
    private HomeServlet homeServlet;
    private UserDao userDaoMock = mock(UserDao.class);

    @Before
    public void init() {
        homeServlet = new HomeServlet();
        userDao = userDaoMock;
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        homeServlet = Mockito.spy(homeServlet);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        when(userDao.getUsers()).thenReturn(new ArrayList<User>());
        ServletContext servletContextMock = mock(ServletContext.class);
        doReturn(servletContextMock).when(homeServlet).getServletContext();

        RequestDispatcher requestDispatcherMock = mock(RequestDispatcher.class);
        when(servletContextMock.getRequestDispatcher("/allUsers.jsp")).thenReturn(requestDispatcherMock);

        homeServlet.doGet(requestMock, responseMock);

        verify(requestDispatcherMock, times(1)).forward(requestMock, responseMock);
    }
}
