package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

public class RhythmBackGroundBottom1 extends SpriteAnimation {
    public RhythmBackGroundBottom1 () {
        super((AppManager.getInstance().getBitmap(R.drawable.background_bottom_1)));
        this.InitSpriteData(250, 167, 1, 1);
        setPosition(0, 830);
    }
}