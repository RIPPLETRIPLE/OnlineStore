package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements Mapper<Product.Category> {
    @Override
    public Product.Category extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.Category.createCategory(rs.getLong("id"), rs.getString("category"));
    }
}
