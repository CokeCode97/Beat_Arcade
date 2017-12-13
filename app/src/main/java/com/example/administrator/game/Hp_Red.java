package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 13..
 */

class HP_Red extends Graphic {
    private int width = 235, height = 11;
    public HP_Red(int x) {
        super(AppManager.getInstance().getBitmap(R.drawable.hp_red));
        this.InitSpriteData(height, width, 2.5f);
        setPosition(556 + x, 54);
    }

    //TODO 체력바 렌더링을 조절함
    //ratio = 최대체력과 현재체력의 비율
    public void hp_Update(double ratio) {
        this.InitSpriteData(height, (int)(width*ratio), 2.5f);
    }
}

