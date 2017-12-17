package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-11-29.
 */

//3번 버튼을 그려주는 객체
public class RhythmBackGroundBottom3 extends Graphic {
    public RhythmBackGroundBottom3 () {
        super((AppManager.getInstance().getBitmap(R.drawable.rhythmbutton3)));
        this.InitSpriteData(250, 164, 1);
        setPosition(336, 830);
    }
}
