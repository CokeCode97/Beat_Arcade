package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 12..
 */

public class hp_Black extends Graphic {

    public hp_Black(int x) {
        super(AppManager.getInstance().getBitmap(R.drawable.hp_black));
        this.InitSpriteData(15, 241, 2.5f);
        setPosition(550 + x, 50);
    }
}
