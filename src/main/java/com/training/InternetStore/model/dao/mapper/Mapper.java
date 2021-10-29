package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {
    T extractFromResultSet(ResultSet rs) throws SQLException, FieldDontPresent;
}
