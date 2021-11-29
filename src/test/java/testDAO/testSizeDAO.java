package testDAO;

import com.training.InternetStore.model.dao.CategoryDao;
import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.SizeDao;
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

public class testSizeDAO {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "testDB");
    SizeDao sizeDao = daoFactory.createSizeDao();
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
    void testCreate() {
        Product.Size size = Product.Size.createSize(0, "test");
        sizeDao.create(size);
        Assertions.assertTrue(sizeDao.findById((int) size.getId()).isPresent());
    }
    @Test
    void testFindAll() {
        Product.Size size = Product.Size.createSize(0, "test");
        sizeDao.create(size);
        Product.Size size1 = Product.Size.createSize(0, "test1");
        sizeDao.create(size1);
        Assertions.assertEquals(sizeDao.findAll().size(), 2);
    }
    @Test
    void testDelete() {
        Product.Size size = Product.Size.createSize(0, "test");
        sizeDao.create(size);
        sizeDao.delete(size);
        Assertions.assertFalse(sizeDao.findById((int) size.getId()).isPresent());
    }
    @Test
    void testUpdate() {
        Product.Size size = Product.Size.createSize(0, "test");
        sizeDao.create(size);
        size.setSize("test1");
        sizeDao.update(size);
        Assertions.assertEquals(size.getSize(), sizeDao.findById((int) size.getId()).get().getSize());
    }
}
