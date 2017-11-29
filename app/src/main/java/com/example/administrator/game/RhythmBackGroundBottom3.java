package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

public class RhythmBackGroundBottom3 extends SpriteAnimation {
    public RhythmBackGroundBottom3 () {
        super((AppManager.getInstance().getBitmap(R.drawable.background_bottom_3)));
        this.InitSpriteData(250, 174, 1, 1);
        setPosition(326, 830);
    }
}
