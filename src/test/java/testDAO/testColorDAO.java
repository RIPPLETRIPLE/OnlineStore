package testDAO;

import com.training.InternetStore.model.dao.CategoryDao;
import com.training.InternetStore.model.dao.ColorDao;
import com.training.InternetStore.model.dao.DaoFactory;
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

public class testColorDAO {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "testDB");
    ColorDao colorDao = daoFactory.createColorDao();
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
        Product.Color color = Product.Color.createColor(0, "test");
        colorDao.create(color);
        Assertions.assertTrue(colorDao.findById((int) color.getId()).isPresent());
    }
    @Test
    void testFindAll() {
        Product.Color color = Product.Color.createColor(0, "test");
        colorDao.create(color);
        Product.Color color1 = Product.Color.createColor(0, "test1");
        colorDao.create(color1);
        Assertions.assertEquals(colorDao.findAll().size(), 2);
    }
    @Test
    void testDelete() {
        Product.Color color = Product.Color.createColor(0, "test");
        colorDao.create(color);
        colorDao.delete(color);
        Assertions.assertFalse(colorDao.findById((int) color.getId()).isPresent());
    }
    @Test
    void testUpdate() {
        Product.Color color = Product.Color.createColor(0, "test");
        colorDao.create(color);
        color.setColor("test1");
        colorDao.update(color);
        Assertions.assertEquals(color.getColor(), colorDao.findById((int) color.getId()).get().getColor());
    }
}
