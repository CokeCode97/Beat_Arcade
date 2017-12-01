package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

//2번 버튼을 그려주는 객체
public class RhythmBackGroundBottom2 extends Graphic {
    public RhythmBackGroundBottom2 () {
        super((AppManager.getInstance().getBitmap(R.drawable.background_bottom_2)));
        this.InitSpriteData(250, 159);
        setPosition(167, 830);
    }
}
