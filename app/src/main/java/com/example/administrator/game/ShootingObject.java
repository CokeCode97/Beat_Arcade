package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-01.
 */

public class ShootingObject extends SpriteAnimation {
    int x, y, width, height;
    float speed;
    public ShootingObject(Bitmap bitmap, int width, int height) {
        super(bitmap);

        this.width = width;
        this.height = height;
    }

    //바라보고 있는 각도에 따라 움직이도록 해줌
    public void Move(double angle, float speed) {
        this.speed = speed;
        this.x = getX();
        this.y = getY();

        angle = Math.toRadians(angle);
        this.x += (int)(Math.sin(angle)*this.speed);
        this.y -= (int)(Math.cos(angle)*this.speed);

        if(this.x < 500) { this.x = 500; }
        if(this.x > 1920-width) { this.x = 1920-width; }
        if(this.y < 0 ) { this.y = 0; }
        if(this.y > 1080-height) { this.y = 1080-height; }

        setPosition(this.x, this.y);
    }
}
