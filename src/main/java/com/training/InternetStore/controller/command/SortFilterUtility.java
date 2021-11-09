package com.training.InternetStore.controller.command;

import com.training.InternetStore.model.entity.Product;

import java.util.Comparator;
import java.util.List;

public class SortFilterUtility {
    public static void sortListBy(String sortBy, List<Product> list) {
        if (sortBy.equals("name")) {
            list.sort(Comparator.comparing(Product::getName));
            return;
        }
        if (sortBy.equals("priceLow")) {
            list.sort(Comparator.comparing(Product::getPrice));
            return;
        }
        if (sortBy.equals("priceHigh")) {
            list.sort((a, c) -> c.getPrice() - a.getPrice());
            return;
        }
        if (sortBy.equals("dateHigh")) {
            list.sort((a, c) -> (int) (c.getId() - a.getId()));
        }
    }
}
