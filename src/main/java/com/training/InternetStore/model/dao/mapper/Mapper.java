package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.dao.*;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {
      DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class, "db");

      CategoryDao categoryDao = daoFactory.createCategoryDao();
      SizeDao sizeDao = daoFactory.createSizeDao();
      ColorDao colorDao = daoFactory.createColorDao();
      UserDao userDao = daoFactory.createUserDao();
      ProductDao productDao = daoFactory.createProductDao();

    T extractFromResultSet(ResultSet rs) throws SQLException, FieldDontPresent;
}
