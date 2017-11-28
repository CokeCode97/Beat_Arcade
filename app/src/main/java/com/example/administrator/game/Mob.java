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
    int mob_x = 1500, mob_y = 500;
    float mob_speed = 2f;

    public Mob() {
        //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.mob_sprite)));
        this.InitSpriteData(85, 98, 8, 4);
        //몹 위치 세팅
        this.setPosition((int)(mob_x* GameActivity.size), (int)(mob_y* GameActivity.size));
    }

    public void mobMove(float x, float y) {
        this.mob_x += x;
        this.mob_y += y;
        this.setPosition((int)(mob_x* GameActivity.size), (int)(mob_y* GameActivity.size));
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        mobMove(-mob_speed, 0);
    }
}
