package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.administrator.framework.SpriteAnimation;
import com.example.administrator.networks.ClientWork;

/**
 * Created by Administrator on 2017-12-02.
 */

public class Laser extends SpriteAnimation {
    private int width= 510, height = 86, combo = 0; //이것의 크기와 생성될때의 콤보
    private long time_Make;                         //이것이 생성된 시간
    private Player player;                          //이것을 생성한 플레이어
    private boolean hit_Check = false;              //공격이 중복되지 않도록 공격을 했는지 안했는지 체크해줄 변수

    private Rect rect_collision;                    //충돌박스

    //Laser의 생성자
    //bitmap = 출력할 이미지, player = 이것을 생성한 플레이어 객체, makeTime = 이것을 만든 시간, combo = 이것을 만들때의 콤보
    public Laser(Bitmap bitmap, Player player, long makeTime, int combo) {
        super(bitmap);
        this.InitSpriteData(height, width, 1, 1);
        this.time_Make = makeTime;
        this.player = player;
        this.combo = combo;
        rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
    }


    public void Update(long Gametime) {
        //생성된지 0.5초가 지나면 사라지며 그전까지는 플레이어를 따라다니면서 적플레이어와의 충돌검사를 요청
        if(Gametime - time_Make > 500) {
            player.remove_Laser(this);
        } else {
            this.setPosition(player.getX()+49-255, player.getY());
            rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
            if(GameState.player_Vector.get(GameState.getPlayer_Num()) == player) {
                collision();
            }
        }
    }


    //TODO 충돌검사를 요청하고 충돌시 서버에 충돌했다고 요청함
    public void collision() {
        if(!hit_Check && CollisionManager.collision_Check(GameState.player_Vector.get(GameState.getEnemy_Num()).getRect_collision(), rect_collision)) {
            ClientWork.write("Collision " + GameState.getEnemy_Num() + " " + "Laser " + combo);
            hit_Check = true;
        }
    }
}
