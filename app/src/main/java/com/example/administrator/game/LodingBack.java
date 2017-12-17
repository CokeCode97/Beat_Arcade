package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class LodingBack extends Graphic {
    public LodingBack() {
        super(AppManager.getInstance().getBitmap(R.drawable.loding_back));
        this.InitSpriteData(400, 712, 2.7f);
    }
}
