package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by mac on 2017. 12. 12..
 */

public class Bullet extends ShootingObject {
    static int width = 50, height = 50;
    float speed_Ore = 10f;
    float speed_Pre= 10f;
    double angle;
    GameState gameState;

    Rect rect_collision;

    public Bullet (Bitmap bitmap, double angle, int x, int y, GameState gameState) {
        super(bitmap, width, height);
        this.InitSpriteData(height, width, 1, 1);
        this.angle = angle;
        this.gameState = gameState;
        this.x = x;
        this.y = y;
        this.setPosition(this.x, this.y);
        rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
    }


    public void Update(long GameTime) {
        //맵을 벗어나면 제거
        if(this.x <= 500 || this.x >= 1920 - width || this.y <= 0 || this.y >= 1080 - height) {
            gameState.removeBullet(this);
        } else { //맵안에 있을때는 충돌박스를 업데이트하며 이동함
            Move(angle, speed_Pre);
            rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
        }
    }
}
