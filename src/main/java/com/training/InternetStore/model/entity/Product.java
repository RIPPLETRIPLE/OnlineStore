package com.training.InternetStore.model.entity;

import java.util.Objects;

public class Product {
    private long id;
    private String name;
    private int price;
    private Sex sex;

    private String img;

    private long categoryId;
    private long colorId;
    private long sizeId;

    private Product(long id, String name, int price, Sex sex, String img, long categoryId, long colorId, long sizeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sex = sex;
        this.img = img;
        this.categoryId = categoryId;
        this.colorId = colorId;
        this.sizeId = sizeId;
    }

    public static Product createProduct(long id, String name, int price, Sex sex, String img, long categoryId, long colorId, long sizeId) {
        return new Product(id, name, price, sex, img, categoryId, colorId, sizeId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getColorId() {
        return colorId;
    }

    public void setColorId(long colorId) {
        this.colorId = colorId;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sex=" + sex +
                ", img='" + img + '\'' +
                ", categoryId=" + categoryId +
                ", colorId=" + colorId +
                ", sizeId=" + sizeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Category {
        private long id;
        private String name;

        public Category(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Color {
        private long id;
        private String color;

        public Color(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public static class Size {
        private long id;
        private String size;

        public Size(String size) {
            this.size = size;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }

    public enum Sex {
        Male, Female, Unisex
    }
}
