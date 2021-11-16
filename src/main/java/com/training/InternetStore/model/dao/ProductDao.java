package com.training.InternetStore.model.dao;

import com.training.InternetStore.model.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product>{
    List<Product> findProductWithSortAndLimit(String sortBy, String order, int from, int to);
    int findAmountOfProducts();
}
