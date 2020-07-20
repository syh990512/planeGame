package com.zzxx.planetFlying;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int life=3;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    protected abstract void move();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    protected BufferedImage image;

    void bang() {

    }


}
