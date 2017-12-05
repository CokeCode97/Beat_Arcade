package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-30.
 */

public class RhythmCombo extends SpriteAnimation{
    public RhythmCombo () {
        super((AppManager.getInstance().getBitmap(R.drawable.combo)));
        this.InitSpriteData(70, 236, 1, 1);
        setPosition(132, 300);
    }


    //combo글자의 위치를 알맞게 조절함
    //num = 콤보의 자릿수
    public void setcombo(int num) {
        int x = 132-33*num;
        setPosition(x, 300);
    }
}
