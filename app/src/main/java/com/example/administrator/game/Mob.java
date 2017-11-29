package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-28.
 */

public class Mob extends SpriteAnimation {
    int mob_x = 50, mob_y = 0;
    float mob_speed = 18f;

    public Mob() {
        //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.note_sprite)));
        this.InitSpriteData(138, 119, 5, 5);
        //몹 위치 세팅
        this.setPosition(mob_x, mob_y);
    }

    public void Move(float x, float y) {
        this.mob_x += x;
        this.mob_y += y;
        this.setPosition(mob_x, mob_y);
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        Move(0 , mob_speed);
    }
}
