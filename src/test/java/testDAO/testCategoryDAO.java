package testDAO;

import com.training.InternetStore.model.dao.CategoryDao;
import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.UserDao;
import com.training.InternetStore.model.dao.impl.ConnectionPoolHolder;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.Product;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class testCategoryDAO {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "testDB");
    CategoryDao categoryDao = daoFactory.createCategoryDao();
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
    void testCreateCategory() {
        Product.Category category = Product.Category.createCategory(0, "test");
        categoryDao.create(category);
        Assertions.assertTrue(categoryDao.findById((int) category.getId()).isPresent());
    }
    @Test
    void testFindAll() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Category category1 = Product.Category.createCategory(0, "test1");
        categoryDao.create(category);
        categoryDao.create(category1);
        Assertions.assertEquals(categoryDao.findAll().size(), 2);
    }
    @Test
    void  testDelete() {
        Product.Category category = Product.Category.createCategory(0, "test");
        categoryDao.create(category);
        categoryDao.delete(category);
        Assertions.assertFalse(categoryDao.findById((int) category.getId()).isPresent());
    }
    @Test
    void testUpdate() {
        Product.Category category = Product.Category.createCategory(0, "test");
        categoryDao.create(category);
        category.setName("test1");
        categoryDao.update(category);
        Assertions.assertEquals(categoryDao.findById((int) category.getId()).get().getName(), category.getName());
    }
}
