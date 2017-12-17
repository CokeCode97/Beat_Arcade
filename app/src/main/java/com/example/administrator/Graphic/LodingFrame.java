package com.example.administrator.Graphic;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class LodingFrame extends Graphic {
    public LodingFrame() {
        super(AppManager.getInstance().getBitmap(R.drawable.frame));
        this.InitSpriteData(362, 361, 1.6f);
        this.setPosition(670, 131);
    }
}
