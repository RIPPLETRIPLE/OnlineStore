package testDAO;

import com.training.InternetStore.model.dao.*;
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

public class testProductDAO {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "testDB");
    ProductDao productDao = daoFactory.createProductDao();
    ColorDao colorDao = daoFactory.createColorDao();
    SizeDao sizeDao = daoFactory.createSizeDao();
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
    void testCreate() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Color color = Product.Color.createColor(0, "test");
        Product.Size size = Product.Size.createSize(0, "test");

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" , 100, Product.Sex.Male, "test",
                category, color, size);

        productDao.create(product);
        Assertions.assertTrue(productDao.findById((int) product.getId()).isPresent());
    }
    @Test
    void testFindAll() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Color color = Product.Color.createColor(0, "test");
        Product.Size size = Product.Size.createSize(0, "test");

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" , 100, Product.Sex.Male, "test",
                category, color, size);

        productDao.create(product);

        Product.Category category1 = Product.Category.createCategory(0, "test1");
        Product.Color color1 = Product.Color.createColor(0, "test1");
        Product.Size size1 = Product.Size.createSize(0, "test1");

        categoryDao.create(category1);
        colorDao.create(color1);
        sizeDao.create(size1);

        Product product1 = Product.createProduct(0, "test1" , 100, Product.Sex.Male, "test",
                category1, color1, size1);

        productDao.create(product1);

        Assertions.assertEquals(productDao.findAll().size(), 2);
    }
    @Test
    void testDelete() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Color color = Product.Color.createColor(0, "test");
        Product.Size size = Product.Size.createSize(0, "test");

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" , 100, Product.Sex.Male, "test",
                category, color, size);

        productDao.create(product);

        productDao.delete(product);
        Assertions.assertFalse(productDao.findById((int) product.getId()).isPresent());
    }
    @Test
    void testUpdate() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Color color = Product.Color.createColor(0, "test");
        Product.Size size = Product.Size.createSize(0, "test");

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" , 100, Product.Sex.Male, "test",
                category, color, size);

        productDao.create(product);
        product.setName("test1");
        productDao.update(product);
        Assertions.assertEquals(productDao.findById((int) product.getId()).get().getName(), product.getName());
    }
    @Test
    void testCountProductsWithFilter() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Color color = Product.Color.createColor(0, "test");
        Product.Size size = Product.Size.createSize(0, "test");

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" , 100, Product.Sex.Male, "test",
                category, color, size);

        productDao.create(product);

        Assertions.assertEquals(productDao.findAmountOfProductsWithFilter(new String[]{"category_ID=" + category.getId()}), 1);
    }
    @Test
    void testFindProductWithSortLimitAndFilter() {
        Product.Category category = Product.Category.createCategory(0, "test");
        Product.Color color = Product.Color.createColor(0, "test");
        Product.Size size = Product.Size.createSize(0, "test");

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" , 100, Product.Sex.Male, "test",
                category, color, size);

        productDao.create(product);
        Assertions.assertEquals(productDao.findProductWithSortLimitAndFilter("date", "asc", new String[]{"category_ID=" + category.getId(), "color_ID=" + color.getId(), "size_ID=" + size.getId(), "sex='Male'"}, 1, 1).size(), 1);
    }
}
