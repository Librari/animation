package kr.uno.android.animation.item;

import java.io.Serializable;

public class PagerItem implements Serializable {

    public String name;
    public String image;
    public String color;

    public PagerItem(String name, String image, String color) {
        this.name = name;
        this.image = image;
        this.color = color;
    }
}