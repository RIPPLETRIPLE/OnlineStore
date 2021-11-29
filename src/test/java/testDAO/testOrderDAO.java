package testDAO;

import com.training.InternetStore.model.dao.*;
import com.training.InternetStore.model.dao.impl.ConnectionPoolHolder;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class testOrderDAO {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "testDB");
    OrderDao orderDao = daoFactory.createOrderDao();
    UserDao userDao = daoFactory.createUserDao();
    ProductDao productDao = daoFactory.createProductDao();

    ColorDao colorDao = daoFactory.createColorDao();
    SizeDao sizeDao = daoFactory.createSizeDao();
    CategoryDao categoryDao = daoFactory.createCategoryDao();

    private static final String DROP_TEST_DB_FILE
            = "C:\\Users\\romac\\IdeaProjects\\OnlineStore\\db\\drop-testDB.sql";
    public Order getOrderForTest(OrderStatus status) {
        Product.Category category = Product.Category.createCategory(0, "test" + status.toString());
        Product.Color color = Product.Color.createColor(0, "test" + status.toString());
        Product.Size size = Product.Size.createSize(0, "test" + status.toString());

        categoryDao.create(category);
        colorDao.create(color);
        sizeDao.create(size);

        Product product = Product.createProduct(0, "test" + status.toString() , 100, Product.Sex.Male, "test" + status.toString(),
                category, color, size);

        productDao.create(product);

        User user = User.createUser("test" + status.toString(), "test" + status.toString(), User.Role.User, User.UserStatus.Unblocked);
        userDao.create(user);

        return Order.createOrder(user, product, 1, status);
    }
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
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        Assertions.assertTrue(orderDao.findById((int) order.getId()).isPresent());
    }
    @Test
    void testFindAll() {
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        Order order1 = getOrderForTest(OrderStatus.Paid);
        orderDao.create(order1);

        Assertions.assertEquals(orderDao.findAll().size(), 2);
    }
    @Test
    void testDelete() {
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        orderDao.delete(order);
        Assertions.assertFalse(orderDao.findById((int) order.getId()).isPresent());
    }
    @Test
    void testUpdate() {
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        order.setStatus(OrderStatus.Paid);
        orderDao.update(order);
        Assertions.assertEquals(orderDao.findById((int) order.getId()).get().getStatus(), order.getStatus());
    }
    @Test
    void testFindOrdersForUserByOrderStatus() {
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        Assertions.assertEquals(orderDao.findOrdersForUserByOrderStatus(order.getUser(), order.getStatus()).size(), 1);
    }
    @Test
    void testFindAllOrdersForUser() {
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        Assertions.assertEquals(orderDao.findAllOrdersForUser(order.getUser()).size(), 1);
    }
    @Test
    void testFindOrderForUserByOrderStatusAndProduct() {
        Order order = getOrderForTest(OrderStatus.Unregistered);
        orderDao.create(order);
        Assertions.assertTrue(orderDao.findOrderForUserByOrderStatusAndProduct(order.getUser(), order.getProduct(), order.getStatus()).isPresent());
    }
}
