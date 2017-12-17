package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.administrator.framework.CollisionManager;
import com.example.administrator.framework.ObjectManager;
import com.example.administrator.framework.SpriteAnimation;
import com.example.administrator.networks.ClientWork;
import com.example.administrator.state.GameState;

/**
 * Created by Administrator on 2017-12-02.
 */

public class Laser extends SpriteAnimation {
    private int width, height;                      //이것의 크기
    private double damage;
    private long time_Make;                         //이것이 생성된 시간
    private Player player;                           //이것을 생성한 플레이어
    private boolean hit_Check = false;             //공격이 중복되지 않도록 공격을 했는지 안했는지 체크해줄 변수

    private Rect rect_collision;                    //충돌박스

    //Laser의 생성자
    //bitmap = 출력할 이미지, player = 이것을 생성한 플레이어 객체, makeTime = 이것을 만든 시간, combo = 이것을 만들때의 콤보, width, height = 크기
    public Laser(Bitmap bitmap, Player player, long makeTime, double damage, int width, int height) {
        super(bitmap);

        //크기설정
        this.width = width;
        this.height = height;

        //그리는 영역 설정
        this.InitSpriteData(height, width, 8, 5);

        //만들어진 시간 설정
        this.time_Make = makeTime;

        //이것을 생성한 플레이어 설정
        this.player = player;

        //생성될때 콤보수 성정
        this.damage = damage;

        //충돌박스 설정
        rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
    }


    //TODO 매 프레임마다 할일을 진행
    public void Update(long Gametime) {
        //생성된지 0.5초가 지나면 사라지며 그전까지는 플레이어를 따라다니면서 적플레이어와의 충돌검사를 요청
        if(Gametime - time_Make > 500) {
            player.remove_Laser(this);
        } else {
            super.Update(Gametime);
            //생성한 캐릭터를 따라다니도록 설정
            this.setPosition(player.getX() + (int)(player.getWidth()*0.5) - (int)(width*0.5), player.getY() + (int)(player.getHeight()*0.5) - (int)(height*0.5));
            //충돌박스를 업데이트
            rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
            //충돌검사 요청
            if(ObjectManager.player_Vector.get(GameState.getPlayer_Num()) == player) {
                collision();
            }
        }
    }


    //TODO 충돌검사를 요청하고 충돌시 서버에 충돌했다고 요청함
    public void collision() {
        if(!hit_Check && CollisionManager.collision_Check(ObjectManager.player_Vector.get(GameState.getEnemy_Num()).getRect_collision(), rect_collision)) {
            ClientWork.write("Collision " + GameState.getEnemy_Num() + " " + "Laser " + damage);
            hit_Check = true;
        }
    }
}
