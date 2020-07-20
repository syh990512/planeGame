package com.zzxx.planetFlying;

public class Airplane extends FlyingObject implements Enemy{
    private int speed;
    int score;
    int count=0;
    public int getScore() {
        return score;
    }
    public Airplane(){
        score=15;
        life=1;
        image=Main.airplane0;
        width=image.getWidth();
        height=image.getHeight();
        x=(int)(Math.random()*(Main.WIDTH-getWidth()));
        y=-height;
        speed=5;
    }
    @Override
    public void move() {
        y+=speed;
    }

}
