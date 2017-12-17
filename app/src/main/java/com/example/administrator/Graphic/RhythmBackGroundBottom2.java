package com.example.administrator.Graphic;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-11-29.
 */

//2번 버튼을 그려주는 객체
public class RhythmBackGroundBottom2 extends Graphic {
    public RhythmBackGroundBottom2 () {
        super((AppManager.getInstance().getBitmap(R.drawable.rhythmbutton2)));
        this.InitSpriteData(250, 168, 1);
        setPosition(170, 830);
    }
}
