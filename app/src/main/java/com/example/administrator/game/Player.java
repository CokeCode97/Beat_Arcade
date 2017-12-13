package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.networks.ClientWork;

import java.util.Vector;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Player extends ShootingObject {
    static int width = 98, height = 86; //플레이어의 크기
    float speed_Ori = 12f;              //플레이어의 속도
    float speed_Pre = 12f;

    //터치좌표, 디패드의 중심좌표
    int t2_x, t2_y, dpadCircle_x, dpadCircle_y;

    //체력
    double HP_Max = 50;
    double HP_Pre = 50;

    //각도, 중심과 터치좌표의 거리
    double angle, dx, dy;

    //무브체크
    boolean move_Check = false;
    boolean slow_Check = false;

    //레이저를 담는 벡터
    Vector<Laser> laser_Vector = new Vector<>();

    //충돌박스
    Rect rect_collision;

    //슬로우 체크시간
    long slow_Time;


    //플레이어의 생성자
    //gamestate = 게임스테이트를 가져옴
    public Player() {
        //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.mob_sprite)), width, height);
        this.InitSpriteData(height, width, 8, 4);
        //게임스테이트 세팅
        //몹 위치 세팅
        this.setPosition(710, 540);
        //충돌박스
        rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
    }



    //TODO 매 프레임마다 할일들
    @Override
    public void Update(long GameTime) {
        //상위객체인 스프라이트 이미지의 업데이트를 작동시킴
        super.Update(GameTime);


        //dpad로 조작중이면 무브가 동작
        if(move_Check) {
            Move(angle, speed_Pre);
            rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
        }

        //슬로우가 걸려있다면 작동
        if(slow_Check) {
            //슬로우를 건시간으로 부터 2초가 지났는지 체크
            if(GameTime - slow_Time > 2000) {
                //속도를 원래 속도로 변경하고 슬로우를 해제
                speed_Pre = speed_Ori;
                slow_Check = false;
            }
        }

        //이 플레이어 객체가 플레이어 본인의 객체라면 충돌판정을 실행
        if(GameState.getPlayer_Num() == GameState.player_Vector.indexOf(this))
            collision();
    }







    ///////////////////////////////////////////////////////////////////
    //*******************플레이어가 맞았을때 작동하는 함수들********************
    ///////////////////////////////////////////////////////////////////

    //TODO 공격을 맞았을때 그에맞게 체력을 조절하고 체력상태바를 변경함
    //combo = 해당 레이저가 발생될떄 가지고 있던 콤보
    public void hit(int combo) {
        //콤보만큼 체력을 감소시킴
        HP_Pre -= combo;
        //체력이 0이되면 제거함
        if(HP_Pre <= 0) {
            GameState.player_Vector.remove(this);
        }
        //체력상황에 따라 체력바를 조절
        ObjectManager.hp_Red_Vector.get(GameState.player_Vector.indexOf(this)).hp_Update(HP_Pre/HP_Max);
        Log.d("check", "hp_Update" + " " +GameState.player_Vector.indexOf(this));
    }


    //TODO 슬로우가 되었다고 설정함
    //slow_Ratio = 슬로우 비율, slow_Time = 슬로우 시간
    public void setSlow( float slow_Ratio, long slow_Time) {
        //슬로우 상태가 아닐때에만 작동
        if(!slow_Check) {
            this.slow_Check = true;
            this.speed_Pre = this.speed_Pre*slow_Ratio;
            this.slow_Time = slow_Time;
        }
    }


    //TODO 충돌 요청을 하고 충돌시 충돌했다고 서버에 알림
    public void collision() {
        for(int i = 0; i < ObjectManager.bullet_Vector.size(); i++) {
            if(CollisionManager.collision_Check(ObjectManager.bullet_Vector.get(i).getRect_collision(), this.rect_collision)) {
                ClientWork.write("Collision " + GameState.player_Vector.indexOf(this) + " " + "Bullet " + i);
            }
        }

    }






    ///////////////////////////////////////////////////////////////////
    //***************여러가지 값들을 가져오고 반환하는 set, get****************
    ///////////////////////////////////////////////////////////////////

    //각도를 세팅
    //angle 캐릭터가 움직일 각도
    public void setAngle(double angle) {
        this.angle = angle;
    }

    //각도를 리턴
    public double getAngle() { return angle; }

    //무브를 세팅
    //move_Check = 캐릭터가 움직이는지 안움직이는지
    public void setMove_Check(boolean move_Check) { this.move_Check = move_Check; }

    //무브를 리턴
    public boolean getMove_Check() { return move_Check; }



    //충돌박스를 리턴
    public Rect getRect_collision() { return rect_collision; }


    //터치좌표를 받아옴
    //t2_x = x좌표, t2_y = y좌표
    public void setTouch(int t2_x, int t2_y) {
        this.t2_x = t2_x;
        this.t2_y = t2_y;
    }


    //d-pad큰 원의 좌표를 가져옴
    //dapadCircle_x = x좌표, dapadCircle_y = y축 좌표
    public void setDpadCircle(int dpadCircle_x, int dpadCircle_y) {
        this.dpadCircle_x = dpadCircle_x;
        this.dpadCircle_y = dpadCircle_y;
    }


    //터치와 D-pad사이의 거리를 받아옴
    //dx = x축거리, dy = y축거리
    public void setDis(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }






    ///////////////////////////////////////////////////////////////////
    //**************************Laser를 관리함***************************
    ///////////////////////////////////////////////////////////////////

    //TODO 레이저를 생성함
    //bitmap = 이미지, time_now = 객체가 생성된 시간
    public void make_Laser(Bitmap bitmap, long time_Make, int combo) {
        laser_Vector.add(new Laser(bitmap,this, time_Make, combo));
    }

    //TODO 레이저를 지움
    //laser = 지울 레이저 객체
    public void remove_Laser(Laser laser) {
        laser_Vector.remove(laser);
    }

}
