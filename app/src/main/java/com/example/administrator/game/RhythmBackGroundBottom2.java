package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

public class RhythmBackGroundBottom2 extends SpriteAnimation {
    public RhythmBackGroundBottom2 () {
        super((AppManager.getInstance().getBitmap(R.drawable.background_bottom_2)));
        this.InitSpriteData(250, 159, 1, 1);
        setPosition(167, 830);
    }
}
