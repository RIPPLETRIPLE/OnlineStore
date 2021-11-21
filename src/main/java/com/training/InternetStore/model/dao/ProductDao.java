package com.training.InternetStore.model.dao;

import com.training.InternetStore.model.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product>{
    List<Product> findProductWithSortLimitAndFilter(String sortBy, String order, String[] filterParams, int from, int to);
    int findAmountOfProductsWithFilter(String[] filterParams);
}
