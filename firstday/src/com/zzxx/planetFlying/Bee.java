package com.zzxx.planetFlying;

import java.awt.image.BufferedImage;

public class Bee extends FlyingObject implements Award{
    private int x_speed;
    private int y_speed;
    private int AwardType;
    public  Bee(){
        life=1;
        image=Main.bee0;
        width=image.getWidth();
        height=image.getHeight();
        x=(int)(Math.random()*(Main.WIDTH-getWidth()-20));
        y=-height;
        y_speed=3;
        x_speed=2;
        AwardType= (int) (Math.random() * 2);
    }
    @Override
    public void move() {
      y+=y_speed;
      x+=x_speed;
      if(x>=Main.WIDTH-getWidth()-16){
          x_speed=-2;
      }else if(x==0){
          x_speed=2;
        }
    }

    @Override
    public int getAwardType() {
        return AwardType;
    }
}
