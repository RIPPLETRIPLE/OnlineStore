package testDAO;

import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.UserDao;
import com.training.InternetStore.model.dao.impl.ConnectionPoolHolder;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.User;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class testUserDAO {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "testDB");
    UserDao userDao = daoFactory.createUserDao();
    private static final String DROP_TEST_DB_FILE
            = "C:\\Users\\romac\\IdeaProjects\\OnlineStore\\db\\drop-testDB.sql";

    @BeforeEach
    void dropDB() throws SQLException, FileNotFoundException {
        Connection con = ConnectionPoolHolder.getDataSource("timetracktest").getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(con);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DROP_TEST_DB_FILE));
        scriptRunner.runScript(bufferedReader);
        con.close();
    }

    @Test
    void  testCreateUser() {
        User user = User.createUser("test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        Assertions.assertTrue(userDao.findById((int) user.getId()).isPresent());
    }
    @Test
    void testFindByLogin() {
        User user = User.createUser("test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        Assertions.assertEquals(user.getId(), userDao.findByLogin(user.getLogin()).get().getId());
    }
    @Test
    void testFindAll() {
        User user = User.createUser("test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        User user1 = User.createUser("test1", "test1", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user1);
        Assertions.assertEquals(userDao.findAll().size(), 2);
    }
    @Test
    void testUpdateUser() {
        User user = User.createUser("test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        user.setLogin("test2");
        userDao.update(user);
        Assertions.assertEquals(user.getLogin(), userDao.findById((int) user.getId()).get().getLogin());
    }
    @Test
    void testDeleteUser() {
        User user = User.createUser("test", "test", User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);
        userDao.delete(user);
        Assertions.assertFalse(userDao.findById((int) user.getId()).isPresent());
    }
}
