package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorMapper implements Mapper<Product.Color>{
    @Override
    public Product.Color extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.Color.createColor(rs.getString("color"));
    }
}
