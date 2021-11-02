package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements Mapper<Order>{

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException, FieldDontPresent {
        return Order.createOrder(userDao.findById(rs.getInt("user_ID")).orElseThrow(FieldDontPresent::new),
                                 productDao.findById(rs.getInt("product_ID")).orElseThrow(FieldDontPresent::new),
                                 rs.getInt("quantity"),
                                 OrderStatus.valueOf(rs.getString("status")));
    }
}
