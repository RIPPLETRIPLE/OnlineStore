package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements Mapper<Product>{
    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.createProduct(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                Product.Sex.valueOf(rs.getString("sex")),
                rs.getString("image"),
                rs.getLong("category_ID"),
                rs.getLong("color_ID"),
                rs.getLong("size_ID"));
    }
}
