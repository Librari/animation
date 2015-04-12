package kr.uno.android.animation.item;

import java.io.Serializable;

public class PagerItem implements Serializable {

    public String name;
    public int image;
    public String color;

    public PagerItem(String name, int image, String color) {
        this.name = name;
        this.image = image;
        this.color = color;
    }
}