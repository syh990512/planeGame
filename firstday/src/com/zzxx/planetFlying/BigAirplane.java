package com.zzxx.planetFlying;

public class BigAirplane extends FlyingObject implements Award,Enemy{
    private int blood;
    private int speed;
    private int AwardType;
    int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBlood() {
        return blood;
    }
    public void setBlood(int blood) {
        this.blood = blood;
    }
    public BigAirplane(){
        score=40;
        life=5;
        image=Main.bigplane0;
        width=image.getWidth();
        height=image.getHeight();
        x=(int)(Math.random()*(Main.WIDTH-getWidth()));
        y=-height;
        speed=2;
        AwardType=(int)(Math.random()*2);
    }
    @Override
    public void move() {
        y+=speed;
    }

    @Override
    public int getAwardType() {
        return AwardType;
    }
}
