package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.ObjectManager;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by mac on 2017. 12. 17..
 */

public class DamageNumber extends SpriteAnimation {
    long time_Make;
    int damage, num_max, num_this, player_X, player_Y;
    public DamageNumber(Bitmap bitmap, int damage, int num_max, int num_this, int x, int y) {
        super(bitmap);
        this.damage = damage;
        this.num_max = num_max;
        this.num_this = num_this;
        this.player_X = x;
        this.player_Y = y;

        for(int i = num_this+1; i < num_max; i++) {
            damage/=10;
        }

        this.InitSpriteData(70, 93, 1, 1);
        if(damage%10 == 0) {
            this.setRect(9*93, 93);
        } else {
            this.setRect(((damage%10)-1) * 93, 93);
        }

        player_X = x + (75 * num_this);

        this.setPosition(player_X, y);
        this.time_Make = System.currentTimeMillis();
    }

    public void Update(long GameTime) {
        if(GameTime - time_Make > 1000) {
            ObjectManager.removeDamage(this);
        } else {
            setPosition(player_X, getY() - 1);
        }
    }
}
