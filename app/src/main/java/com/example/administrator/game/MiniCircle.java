package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-12-01.
 */

public class MiniCircle extends Graphic {
    public int x = 1645, y = 805, x_ori = 1645, y_ori =805;
    public MiniCircle() {
        super((AppManager.getInstance().getBitmap(R.drawable.circle2)));
        this.InitSpriteData(150, 150, 1);
        setPosition(x, y);
    }
}
