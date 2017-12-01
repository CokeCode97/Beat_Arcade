package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-01.
 */

public class ShootingObject extends SpriteAnimation {
    int x, y, width, height;
    float speed;
    public ShootingObject(Bitmap bitmap, float speed, int width, int height) {
        super(bitmap);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    //바라보고 있는 각도에 따라 움직이도록 해줌
    public void Move(double angle) {
        this.x = getX();
        this.y = getY();

        angle = Math.toRadians(angle);
        this.x += (int)(Math.sin(angle)*this.speed);
        this.y -= (int)(Math.cos(angle)*this.speed);

        if(x < 500) { x = 500; }
        if(x > 1920-width) { x = 1920-width; }
        if(y < 0 ) { y = 0; }
        if(y > 1080-height) { y = 1080-height; }

        setPosition(x, y);
    }
}
