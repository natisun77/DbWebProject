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

import static org.mockito.Mockito.*;

public class UserServletTest {
    private UserServlet userServlet;
    private UserDao userDaoMock = mock(UserDao.class);

    @Before
    public void init() {
        userServlet = new UserServlet();
        userServlet.userDao = userDaoMock;
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        userServlet = Mockito.spy(userServlet);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        when(requestMock.getParameter("id")).thenReturn("1");
        when(requestMock.getParameter("name")).thenReturn("nata");
        when(requestMock.getParameter("password")).thenReturn("1111");
        when(userDaoMock.getUser((long) 1)).thenReturn(Optional.of(new User((long) 1, "nata", "1111")));

        ServletContext servletContextMock = mock(ServletContext.class);
        doReturn(servletContextMock).when(userServlet).getServletContext();

        RequestDispatcher requestDispatcherMock = mock(RequestDispatcher.class);
        when(servletContextMock.getRequestDispatcher("/editUser.jsp")).thenReturn(requestDispatcherMock);

        userServlet.doGet(requestMock, responseMock);

        verify(requestDispatcherMock, times(1)).forward(requestMock, responseMock);

    }

    @Test
    public void testDoPostWithoutAction() throws IOException, ServletException {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        when(requestMock.getParameter("action")).thenReturn(null);
        when(requestMock.getParameter("name")).thenReturn("nata");
        when(requestMock.getParameter("password")).thenReturn("1111");

        userServlet.doPost(requestMock, responseMock);

        verify(userDaoMock, times(1)).addUser(new User("nata", "1111"));

        verify(responseMock, times(1)).sendRedirect("/");
    }

    @Test
    public void testDoPostWithActionPut() throws IOException, ServletException {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        when(requestMock.getParameter("action")).thenReturn("put");
        when(requestMock.getParameter("name")).thenReturn("nata");
        when(requestMock.getParameter("password")).thenReturn("1111");
        when(requestMock.getParameter("id")).thenReturn("1");
        when(userDaoMock.updateUser(new User((long) 1, "nata", "1111"))).thenReturn(true);

        userServlet.doPost(requestMock, responseMock);

        verify(responseMock, times(1)).sendRedirect("/?updated=true");
    }

    @Test
    public void testDoPostWithActionDelete() throws IOException, ServletException {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);

        when(requestMock.getParameter("action")).thenReturn("delete");
        when(requestMock.getParameter("id")).thenReturn("1");
        when(userDaoMock.deleteUser(1)).thenReturn(true);

        userServlet.doPost(requestMock, responseMock);

        verify(userDaoMock, times(1)).deleteUser(1);
        verify(responseMock, times(1)).sendRedirect("/?deleted=true");
    }
}