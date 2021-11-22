package com.training.InternetStore.model.dao.exception;


public class FieldDontPresent extends Exception {
    @Override
    public String getMessage() {
        return "Field don`t present";
    }
}
