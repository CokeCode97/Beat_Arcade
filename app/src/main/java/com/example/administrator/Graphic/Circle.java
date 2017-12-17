package com.example.administrator.Graphic;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-12-01.
 */

public class Circle extends Graphic {
    public Circle() {
        super((AppManager.getInstance().getBitmap(R.drawable.circle)));
        this.InitSpriteData(400, 400, 1);
        setPosition(1520, 680);
    }
}
