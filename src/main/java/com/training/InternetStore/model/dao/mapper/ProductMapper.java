package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.dao.CategoryDao;
import com.training.InternetStore.model.dao.ColorDao;
import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.SizeDao;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements Mapper<Product>{
    private final DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class);
    private final CategoryDao categoryDao = daoFactory.createCategoryDao();
    private final SizeDao sizeDao = daoFactory.createSizeDao();
    private final ColorDao colorDao = daoFactory.createColorDao();

    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException, FieldDontPresent {
        return Product.createProduct(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                Product.Sex.valueOf(rs.getString("sex")),
                rs.getString("image"),
                categoryDao.findById(rs.getInt("categoryId")).orElseThrow(FieldDontPresent::new),
                colorDao.findById(rs.getInt("colorId")).orElseThrow(FieldDontPresent::new),
                sizeDao.findById(rs.getInt("sizeId")).orElseThrow(FieldDontPresent::new));
    }
}
