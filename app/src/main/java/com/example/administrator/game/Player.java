package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

import java.util.Vector;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Player extends ShootingObject {
    static int width = 98, height = 85; //플레이어의 크기
    static float speed = 12f;           //플레이어의 속도

    //터치좌표, 디패드의 중심좌표
    int t2_x, t2_y, dpadCircle_x, dpadCircle_y;

    //각도, 중심과 터치좌표의 거리
    double angle, dx, dy;

    //무브체크
    boolean move_Check = false;

    //레이저를 담는 벡터
    Vector<Laser> laser_Vector = new Vector<>();

    public Player() {
         //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.mob_sprite)), speed, width, height);
        this.InitSpriteData(height, width, 8, 4);
        //몹 위치 세팅
        this.setPosition(710, 540);
     }


    @Override
     public void Update(long GameTime) {
        super.Update(GameTime);
         //dpad로 조작중이면 무브가 동작
        if(move_Check) {
            Move(angle);
        }
     }


     //각도를 세팅
     public void setAngle(double angle) {
         this.angle = angle;
     }

     //각도를 리턴
     public double getAngle() { return angle; }

     //무브를 세팅
     public void setMove_Check(boolean move_Check) { this.move_Check = move_Check; }

     //무브를 리턴
     public boolean getMove_Check() { return move_Check; }


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

     //레이저를 생성함
     //bitmap = 이미지, time_now = 객체가 생성된 시간
     public void make_Laser(Bitmap bitmap, long time_Make) {
        laser_Vector.add(new Laser(bitmap,this, time_Make));
     }

     //레이저를 지움
    //laser = 지울 레이저 객체
     public void remove_Laser(Laser laser) {
        laser_Vector.remove(laser);
     }
}
