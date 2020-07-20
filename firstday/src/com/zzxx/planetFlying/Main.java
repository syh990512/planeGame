package com.zzxx.planetFlying;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

public class Main extends JPanel {
    public final static int WIDTH=400;
    public final static int HEIGHT=600;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage bee0;
    public static BufferedImage bullet0;
    public static BufferedImage airplane0;
    public static BufferedImage airplane1;
    public static BufferedImage airplane2;
    public static BufferedImage airplane3;
    public static BufferedImage airplane4;
    public static BufferedImage bigplane0;
    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    private ArrayList<FlyingObject> bullets=new ArrayList<>();
    public final int START=0;
    public final int RUNNING=1;
    public final int PAUSE=2;
    public final int GAMEOVER=3;
    private  int state=0;
    private int shootindex=0;

    Hero hero=new Hero();
    Bullet bullet = new Bullet(hero.getX()+(hero.width)/2-2,hero.getY()-10);
    static{
        try {
             hero0= ImageIO.read(Main.class.getResourceAsStream("pic/hero0.png"));
             hero1= ImageIO.read(Main.class.getResourceAsStream("pic/hero1.png"));
             bee0= ImageIO.read(Main.class.getResourceAsStream("pic/bee.png"));
             bullet0= ImageIO.read(Main.class.getResourceAsStream("pic/bullet.png"));
             bigplane0= ImageIO.read(Main.class.getResourceAsStream("pic/bigplane.png"));
             airplane0= ImageIO.read(Main.class.getResourceAsStream("pic/airplane.png"));
             airplane1= ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember0.png"));
             airplane2= ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember1.png"));
             airplane3= ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember2.png"));
             airplane4= ImageIO.read(Main.class.getResourceAsStream("pic/airplane_ember3.png"));
             background= ImageIO.read(Main.class.getResourceAsStream("pic/background.png"));
             start= ImageIO.read(Main.class.getResourceAsStream("pic/start.png"));
             pause= ImageIO.read(Main.class.getResourceAsStream("pic/pause.png"));
             gameover= ImageIO.read(Main.class.getResourceAsStream("pic/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final java.util.Timer timer=new java.util.Timer();
    public void action(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(state==RUNNING){
                    //多个飞行物创建
                    flyingobjectcreate();
                    //多个飞行物移动
                    flyingobjectmove();
                    //底层飞行物越界
                    flyingOutOfbound();
                    //子弹射击
                    shootaction();
                    bulletmove();
                    //英雄机移动
                    hero.move();
                    //子弹射中目标
                    hit();
                    //判断碰撞
                    knock();
                }
                repaint();
                //子弹与敌机碰撞
            }
        },500,50);
        MouseAdapter adapter= new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(state==START){
                    state=RUNNING;
                }else if(state==GAMEOVER){
                    state=START;
                    hero=new Hero();
                    if (flyings.size() > 0) {
                        flyings.subList(0, flyings.size()).clear();
                    }
                    if (bullets.size() > 0) {
                        bullets.subList(0, bullets.size()).clear();
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(state==PAUSE){
                    state=RUNNING;
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(state==RUNNING){
                    state=PAUSE;
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if(state==RUNNING) {
                    int mouse_x = e.getX();
                    int mouse_y = e.getY();
                    hero.setX(mouse_x - (hero.width) / 2);
                    hero.setY(mouse_y - (hero.height) / 2);
                }
            }
        };
        this.addMouseMotionListener(adapter);
        this.addMouseListener(adapter);
    }
    public void knock(){

            for (int i = 0; i <flyings.size(); i++) {
                if(hero.x+hero.width/2>=flyings.get(i).x
                        && hero.x+hero.width/2<=(flyings.get(i).x+flyings.get(i).width)
                        && hero.y<=(flyings.get(i).y+flyings.get(i).height)
                        /*&& hero.y+hero.height<=flyings.get(i).y*/){
                    hero.minusLife();
                    flyings.remove(i);
                    if(hero.life==0){
                        state=GAMEOVER;
                    }
                    break;
                }
            }

    }
    public void hit(){
        for (int i = 0; i <bullets.size(); i++) {
            for (int j = 0; j <flyings.size(); j++) {
                if(bullets.get(i).x>=flyings.get(j).x
                        && bullets.get(i).x<=(flyings.get(j).x+flyings.get(j).width)
                        &&bullets.get(i).y<=(flyings.get(j).y+flyings.get(j).height)
                        && bullets.get(i).y>=flyings.get(j).y)
                {
                    flyings.get(j).setLife(flyings.get(j).getLife()-1);
                    if (flyings.get(j).getLife()==0){
                        if(flyings.get(j) instanceof Enemy){
                            Enemy enemy=(Enemy)(flyings.get(j));
                            hero.addScore(enemy.getScore());
                        }
                        if(flyings.get(j) instanceof Award){
                            Award award=(Award)flyings.get(j);
                            if(award.getAwardType()==Award.ADD_LIFE){
                                hero.addLife();
                            }else{
                                hero.addDoubleFire();
                            }
                        }
                        flyings.get(j).bang();
                        flyings.remove(j);
                    }
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    public void shootaction(){
        shootindex++;
        if(shootindex%1==0) {
            Bullet[] b = hero.shoot();
            bullets.addAll(Arrays.asList(b));
        }
    }

    public void bulletmove(){
        for (int i = 0; i <bullets.size(); i++) {
            bullets.get(i).move();
        }
    }

    public void flyingOutOfbound(){
        for (int i = 0; i <flyings.size(); i++) {
            if(flyings.get(i).getY()>=HEIGHT){
                flyings.remove(i);
                i--;
            }
        }
        for (int i = 0; i <bullets.size(); i++) {
            if(bullets.get(i).getY()==-bullet.height){
                bullets.remove(i);
                i--;
            }
        }
    }

    public void flyingobjectmove(){
        for (int i = 0; i <flyings.size(); i++) {
            flyings.get(i).move();
        }
    }

    public ArrayList<FlyingObject> flyings=new ArrayList<>();
    private int influence=0;
    public void flyingobjectcreate(){
        influence++;
        if(influence%12==0){

            int ran=(int)(Math.random()*50);
            FlyingObject flys;
            if(ran==0){
                flys=new Bee();
            }else if(ran==1 || ran==2){
                flys=new BigAirplane();
            }else{
                flys=new Airplane();
            }
            flyings.add(flys);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //画背景
        g.drawImage(background,0,0,this);
        //画一个英雄机Z
        g.drawImage(hero.getImage(),hero.getX(),hero.getY(),this);
        //画一个子弹Z
        flyingbullet(g);
        //画全部飞行物Z
        flyingpaint(g);
        //画分数
        g.drawString("分数:"+hero.getScore(),20,20);
        //画生命值
        g.drawString("生命值:"+hero.getLife(),20,40);
        //画四个界面
        switch (state){
            case START:
                g.drawImage(start,0,0,this);
                break;
            case PAUSE:
                g.drawImage(pause,0,0,this);
                break;
            case GAMEOVER:
                g.drawImage(gameover,0,0,this);
                break;
        }
    }

    public void flyingbullet(Graphics g) {
        for (int i = 0; i <bullets.size(); i++) {
            g.drawImage(bullets.get(i).getImage(),bullets.get(i)
                    .getX(),bullets.get(i).getY(),this);
        }
    }

    public void flyingpaint(Graphics g){
        for (int i = 0; i <flyings.size(); i++) {
            g.drawImage(flyings.get(i).getImage(),flyings.get(i)
                    .getX(),flyings.get(i).getY(),this);
        }
    }

    public static void main(String[] args) {
        JFrame window=new JFrame();
        window.setTitle("飞机大战");
        window.setVisible(true);
        window.setSize(WIDTH,HEIGHT);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Main p=new Main();
        window.add(p);
        p.action();
    }
}