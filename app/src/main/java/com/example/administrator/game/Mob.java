package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Mob extends SpriteAnimation {
    int mob_x = 1500, mob_y = 500;

     public Mob() {
         //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.mob_sprite)));
        this.InitSpriteData(85, 98, 8, 4);
        //몹 위치 세팅
        this.setPosition(mob_x, mob_y);
     }
}
