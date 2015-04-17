package kr.uno.android.animation.item;

import java.io.Serializable;

public class Product implements Serializable {

    public String name;
    public String description;
    public String image;
    public String url;
    public String color;

    public Product(String name, String description, String image, String url, String color) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.url = url;
        this.color = color;
    }
}