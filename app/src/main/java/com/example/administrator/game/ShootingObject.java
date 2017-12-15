package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-01.
 */

public class ShootingObject extends SpriteAnimation {
    int width, height;
    double shooting_X, shooting_Y;
    float speed;
    public ShootingObject(Bitmap bitmap, int width, int height) {
        super(bitmap);

        this.width = width;
        this.height = height;
    }

    //바라보고 있는 각도에 따라 움직이도록 해줌
    public void Move(double angle, float speed) {
        this.shooting_X = getX();
        this.shooting_Y = getY();

        this.speed = speed;

        angle = Math.toRadians(angle);
        this.shooting_X += (Math.sin(angle)*this.speed);
        this.shooting_Y -= (Math.cos(angle)*this.speed);

        if(this.shooting_X < 500) { this.shooting_X = 500; }
        if(this.shooting_X > 1920-width) { this.shooting_X = 1920-width; }
        if(this.shooting_Y < 0 ) { this.shooting_Y = 0; }
        if(this.shooting_Y > 1080-height) { this.shooting_Y = 1080-height; }

        setPosition((int)this.shooting_X, (int)this.shooting_Y);
    }
}
