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
import java.util.Optional;


public class UserServletTest {
    private UserServlet userServlet;
    private UserDao userDaoMock = Mockito.mock(UserDao.class);

    @Before
    public void init() {
        userServlet = new UserServlet();
        userServlet.setUserDao(userDaoMock);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        userServlet = Mockito.spy(userServlet);
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);

        Mockito.when(requestMock.getParameter("id")).thenReturn("1");
        Mockito.when(requestMock.getParameter("name")).thenReturn("nata");
        Mockito.when(requestMock.getParameter("password")).thenReturn("1111");
        Mockito.when(userDaoMock.getUser((long) 1)).thenReturn(Optional.of(new User((long) 1, "nata", "1111")));

        ServletContext servletContextMock = Mockito.mock(ServletContext.class);
        Mockito.doReturn(servletContextMock).when(userServlet).getServletContext();

        RequestDispatcher requestDispatcherMock = Mockito.mock(RequestDispatcher.class);
        Mockito.when(servletContextMock.getRequestDispatcher("/editUser.jsp")).thenReturn(requestDispatcherMock);

        userServlet.doGet(requestMock, responseMock);

        Mockito.verify(requestDispatcherMock, Mockito.times(1)).forward(requestMock, responseMock);

    }

    @Test
    public void testDoPostWithoutAction() throws IOException, ServletException {
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);

        Mockito.when(requestMock.getParameter("action")).thenReturn(null);
        Mockito.when(requestMock.getParameter("name")).thenReturn("nata");
        Mockito.when(requestMock.getParameter("password")).thenReturn("1111");

        userServlet.doPost(requestMock, responseMock);

        Mockito.verify(userDaoMock, Mockito.times(1)).addUser(new User("nata", "1111"));

        Mockito.verify(responseMock, Mockito.times(1)).sendRedirect("/");
    }

    @Test
    public void testDoPostWithActionPut() throws IOException, ServletException {
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);

        Mockito.when(requestMock.getParameter("action")).thenReturn("put");
        Mockito.when(requestMock.getParameter("name")).thenReturn("nata");
        Mockito.when(requestMock.getParameter("password")).thenReturn("1111");
        Mockito.when(requestMock.getParameter("id")).thenReturn("1");
        Mockito.when(userDaoMock.updateUser(new User((long) 1, "nata", "1111"))).thenReturn(true);

        userServlet.doPost(requestMock, responseMock);

        Mockito.verify(responseMock, Mockito.times(1)).sendRedirect("/?updated=true");
    }

    @Test
    public void testDoPostWithActionDelete() throws IOException, ServletException {
        HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responseMock = Mockito.mock(HttpServletResponse.class);

        Mockito.when(requestMock.getParameter("action")).thenReturn("delete");
        Mockito.when(requestMock.getParameter("id")).thenReturn("1");
        Mockito.when(userDaoMock.deleteUser(1)).thenReturn(true);

        userServlet.doPost(requestMock, responseMock);

        Mockito.verify(userDaoMock, Mockito.times(1)).deleteUser(1);
        Mockito.verify(responseMock, Mockito.times(1)).sendRedirect("/?deleted=true");
    }
}