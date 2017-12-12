package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-02.
 */

public class Laser extends SpriteAnimation {
    int width= 510, height = 86, combo = 0;
    long time_Make;
    Player player;
    boolean hit_Check = false;

    Rect rect_collision;

    public Laser(Bitmap bitmap, Player player, long makeTime, int combo) {
        super(bitmap);
        this.InitSpriteData(height, width, 1, 1);
        this.time_Make = makeTime;
        this.player = player;
        this.combo = combo;
        rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
    }

    public void Update(long Gametime) {
        if(Gametime - time_Make > 500) {
            player.remove_Laser(this);
        } else {
            this.setPosition(player.getX()+49-255, player.getY());
            rect_collision = new Rect(getX(), getY(), getX()+width, getY()+height);
            if(player.gameState.player_Vector.get(player.gameState.getPlayer_Num()) == player) {
                collision();
            }
        }
    }

    public void collision() {
        if(!hit_Check && player.gameState.collision_Check(player.gameState.player_Vector.get(player.gameState.getEnemy_Num()).getRect_collision(), rect_collision)) {
            player.gameState.clientWork.write("Collision " + player.gameState.getEnemy_Num() + " " + "Laser " + combo);
            hit_Check = true;
        }
    }
}
