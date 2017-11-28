package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-28.
 */

public class Mob extends SpriteAnimation {
    public Mob(Bitmap bitmap) {
        super(bitmap);
        //애니메이션 정보설정
        this.InitSpriteData(85, 98, 8, 4);
    }
}
