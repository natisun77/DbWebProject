package com.nataliia.initializer;

import com.nataliia.dao.GoodDaoHibImpl;
import com.nataliia.dao.RoleDaoHibImpl;
import com.nataliia.dao.UserDaoHibImpl;
import com.nataliia.model.Good;
import com.nataliia.model.Role;
import com.nataliia.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value = "/init", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        UserDaoHibImpl userDaoHib = new UserDaoHibImpl();
        GoodDaoHibImpl goodDaoHib = new GoodDaoHibImpl();
        RoleDaoHibImpl roleDaoHib = new RoleDaoHibImpl();

        Role admin = new Role("admin");
        Role member = new Role("member");
        roleDaoHib.addRole(admin);
        roleDaoHib.addRole(member);

        User nata = new User("nata", "khmelovska.ng@gmail.com", "1111", admin );
        User nata2 = new User("nata2", "khmelovska.ng@gmail.com", "1111", member);
        userDaoHib.addUser(nata);
        userDaoHib.addUser(nata2);

        Good phone = new Good("phone", "blue", 500);
        Good computer = new Good("computer", "red", 1500);
        goodDaoHib.addGood(phone);
        goodDaoHib.addGood(computer);
    }
}
