package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Circle extends Graphic {
    public Circle() {
        super((AppManager.getInstance().getBitmap(R.drawable.circle)));
        this.InitSpriteData(400, 400);
        setPosition(1520, 680);
    }
}
