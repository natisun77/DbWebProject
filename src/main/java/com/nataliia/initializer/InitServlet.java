package com.nataliia.initializer;

import com.nataliia.dao.GoodDao;
import com.nataliia.dao.OrderDao;
import com.nataliia.dao.RoleDao;
import com.nataliia.dao.UserDao;
import com.nataliia.dao.impl.GoodDaoHibImpl;
import com.nataliia.dao.impl.OrderDaoHibImpl;
import com.nataliia.dao.impl.RoleDaoHibImpl;
import com.nataliia.dao.impl.UserDaoHibImpl;
import com.nataliia.model.Good;
import com.nataliia.model.Order;
import com.nataliia.model.Role;
import com.nataliia.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/init", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDaoHibImpl();
        GoodDao goodDao = new GoodDaoHibImpl();
        RoleDao roleDao = new RoleDaoHibImpl();
        OrderDao orderDao = new OrderDaoHibImpl();

        Role admin = new Role("admin");
        Role member = new Role("member");
        roleDao.add(admin);
        roleDao.add(member);

        User nata = new User("nata", "khmelovska.ng@gmail.com", "1111", admin );
        User nata2 = new User("nata2", "khmelovska.ng@gmail.com", "1111", member);
        userDao.add(nata);
        userDao.add(nata2);

        Good phone = new Good("phone", "blue", 500);
        Good computer = new Good("computer", "red", 1500);
        goodDao.add(phone);
        goodDao.add(computer);

        List<Good> goods = new ArrayList<>();
        goods.add(phone);
        goods.add(phone);
        goods.add(computer);

        Order order = new Order(goods, nata2);
        orderDao.add(order);
        System.out.println(orderDao.getAll(Order.class).toString());
    }
}
