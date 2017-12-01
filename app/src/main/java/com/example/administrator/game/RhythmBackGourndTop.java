package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

//노트가 내려오는 부분의 배경을 나타내는 객체
public class RhythmBackGourndTop extends Graphic {
    public RhythmBackGourndTop() {
        super((AppManager.getInstance().getBitmap(R.drawable.background_top)));
        this.InitSpriteData(208, 125, 4);
    }
}
