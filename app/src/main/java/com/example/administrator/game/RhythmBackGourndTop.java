package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

public class RhythmBackGourndTop extends SpriteAnimation {
    public RhythmBackGourndTop() {
        super((AppManager.getInstance().getBitmap(R.drawable.background_top)));
        this.InitSpriteData(830, 500, 1, 1);
    }
}
