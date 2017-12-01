package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-12-01.
 */

public class ShootingBackground extends Graphic {
    public ShootingBackground  () {
        super((AppManager.getInstance().getBitmap(R.drawable.shootingback)));
        this.InitSpriteData(270, 355, 4);
        setPosition(500, 0);
    }
}
