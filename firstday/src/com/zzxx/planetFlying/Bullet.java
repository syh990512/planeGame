package com.zzxx.planetFlying;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject{
    private int speed;
    public Bullet(int x, int y){
        image=Main.bullet0;
        height=image.getHeight();
        width=image.getWidth();
        this.x=x;
        this.y=y;
        speed=8;
    }
    @Override
    public void move() {
        y-=speed;
    }
}
