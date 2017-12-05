package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Mob extends ShootingObject {
    int mob_x = 1500, mob_y = 500, t2_x, t2_y, dpadCircle_x, dpadCircle_y;  //각종변수
    static int width = 98, height = 85;
    static float speed = 10f;
    double angle, dx, dy;

     public Mob() {
         //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.mob_sprite)), speed, width, height);
        this.InitSpriteData(height, width, 8, 4);

        //몹 위치 세팅
        this.setPosition(mob_x, mob_y);
     }


    @Override
     public void Update(long GameTime) {
         super.Update(GameTime);
         //dpad로 조작중이면 무브가 동작
        if((t2_x > dpadCircle_x-100 && t2_y > dpadCircle_y - 100) && (Math.sqrt(dx*dx + dy*dy)) > 50) {
            Move(angle);
        }
     }

     //각도를 세팅
     public void setAngle(double angle) {
         this.angle = angle;
     }


     //터치좌표를 받아옴
     //t2_x = x좌표, t2_y = y좌표
     public void setTouch(int t2_x, int t2_y) {
        this.t2_x = t2_x;
        this.t2_y = t2_y;
     }

     //d-pad큰 원의 좌표를 가져옴
     //dapadCircle_x = x좌표, dapadCircle_y = y축 좌표
     public void setdpadCircle(int dpadCircle_x, int dpadCircle_y) {
        this.dpadCircle_x = dpadCircle_x;
        this.dpadCircle_y = dpadCircle_y;
     }

     //터치와 D-pad사이의 거리를 받아옴
    //dx = x축거리, dy = y축거리
     public void setDis(double dx, double dy) {
         this.dx = dx;
         this.dy = dy;
     }
}
