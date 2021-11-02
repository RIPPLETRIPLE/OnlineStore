package com.training.InternetStore.model.entity;

import java.util.Objects;

public class Product {
    private long id;
    private String name;
    private int price;
    private Sex sex;

    private String img;

    private Category category;
    private Color color;
    private Size size;

    public Product(long id, String name, int price, Sex sex, String img, Category category, Color color, Size size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sex = sex;
        this.img = img;
        this.category = category;
        this.color = color;
        this.size = size;
    }

    public static Product createProduct(long id, String name, int price, Sex sex, String img, Category category, Color color, Size size) {
        return new Product(id, name, price, sex, img, category, color, size);
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sex=" + sex +
                ", img='" + img + '\'' +
                ", category=" + category +
                ", color=" + color +
                ", size=" + size +
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

        private Category(String name) {
            this.name = name;
        }

        public static Category createCategory(String name) {
            return new Category(name);
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

        @Override
        public String toString() {
            return "Category{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static class Color {
        private long id;
        private String color;

        private Color(String color) {
            this.color = color;
        }

        public static Color createColor(String color) {
            return new Color(color);
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

        @Override
        public String toString() {
            return "Color{" +
                    "id=" + id +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    public static class Size {
        private long id;
        private String size;

        private Size(String size) {
            this.size = size;
        }

        public static Size createSize(String size) {
            return new Size(size);
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

        @Override
        public String toString() {
            return "Size{" +
                    "id=" + id +
                    ", size='" + size + '\'' +
                    '}';
        }
    }

    public enum Sex {
        Male, Female, Unisex
    }
}
