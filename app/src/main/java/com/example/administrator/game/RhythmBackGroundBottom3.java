package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

//3번 버튼을 그려주는 객체
public class RhythmBackGroundBottom3 extends Graphic {
    public RhythmBackGroundBottom3 () {
        super((AppManager.getInstance().getBitmap(R.drawable.background_bottom_3)));
        this.InitSpriteData(250, 174);
        setPosition(326, 830);
    }
}
