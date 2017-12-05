package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-30.
 */

public class RhythmComboNumber extends SpriteAnimation {
    public RhythmComboNumber () {
        super((AppManager.getInstance().getBitmap(R.drawable.number_sprite)));
        this.InitSpriteData(80, 67, 1, 1);
        setPosition(132, 300);
    }

    //콤보넘버들의 위치와 숫자를 조절함
    //cx = combo글자의 위치, combo = 이 콤보넘버 객체가 나타낼 숫자, num = 콤보의 자릿수
    public void setcombo(int cx, int combo, int num) {
        this.setRect(combo*67, 67);
        int x = cx+236+67*num;
        setPosition(x, 300);
    }
}