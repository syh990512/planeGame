package com.zzxx.planetFlying;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hero extends FlyingObject{
    private int score;
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    public Hero(){
        x=150;
        y=350;
        image=Main.hero0;
        height=image.getHeight();
        width=image.getWidth();
    }
    public void addScore(int scored){
        this.setScore(this.getScore()+scored);
    }
    public void addLife(){
        this.setLife(this.getLife()+1);
    }
    public void minusLife(){
        this.setLife(this.getLife()-1);
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int count=0;
    @Override
    public void move() {
        //切换图片
        count++;
        if(count%2==0){
            setImage(Main.hero0);
        }
        else
        setImage(Main.hero1);
    }
    private int doublefire=0;
    public void addDoubleFire(){
        doublefire+=20;
    }
    public Bullet[] shoot(){
        Bullet[] bullets;
        if(doublefire==0){
            bullets=new Bullet[1];
            bullets[0]=new Bullet(this.getX()+(this.width)/2-2,this.getY()-10);
        }else if(doublefire>=20 &&doublefire<40){
            bullets=new Bullet[2];
            bullets[0]=new Bullet(this.getX()+(this.width)/4-2,this.getY()-10);
            bullets[1]=new Bullet(this.getX()+((this.width)*3)/4-2,this.getY()-10);
        }else if(doublefire>=40 &&doublefire<60){
            bullets=new Bullet[3];
            bullets[0]=new Bullet(this.getX()+(this.width)/2-2,this.getY()-10);
            bullets[1]=new Bullet(this.getX()+(this.width)/4-2,this.getY()-10);
            bullets[2]=new Bullet(this.getX()+((this.width)*3)/4-2,this.getY()-10);
        }else{
            bullets=new Bullet[4];
            bullets[0]=new Bullet(this.getX(),this.getY()-10);
            bullets[1]=new Bullet(this.getX()+(this.width)/3-2,this.getY()-10);
            bullets[2]=new Bullet(this.getX()+((this.width)*2)/3-2,this.getY()-10);
            bullets[3]=new Bullet(this.getX()+(this.width),this.getY()-10);
        }
        return bullets;
    }
}
