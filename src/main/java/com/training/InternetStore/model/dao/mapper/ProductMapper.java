package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements Mapper<Product>{

    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException, FieldDontPresent {
        return Product.createProduct(
                rs.getLong("ID"),
                rs.getString("name"),
                rs.getInt("price"),
                Product.Sex.valueOf(rs.getString("sex")),
                rs.getString("image"),
                categoryDao.findById(rs.getInt("category_ID")).orElseThrow(FieldDontPresent::new),
                colorDao.findById(rs.getInt("color_ID")).orElseThrow(FieldDontPresent::new),
                sizeDao.findById(rs.getInt("size_ID")).orElseThrow(FieldDontPresent::new));
    }
}
