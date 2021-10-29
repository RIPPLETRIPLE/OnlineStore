package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SizeMapper implements Mapper<Product.Size> {
    @Override
    public Product.Size extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.Size.createSize(rs.getString("color"));
    }
}
