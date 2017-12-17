package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class LodingBarBack extends Graphic{
    public LodingBarBack() {
        super(AppManager.getInstance().getBitmap(R.drawable.lodingbar_back));
        this.InitSpriteData(36, 360, 3f);
        this.setPosition(420, 802);
    }
}
