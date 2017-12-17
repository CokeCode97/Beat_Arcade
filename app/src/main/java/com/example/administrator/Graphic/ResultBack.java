package com.example.administrator.Graphic;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class ResultBack extends Graphic {
    public ResultBack() {
        super(AppManager.getInstance().getBitmap(R.drawable.main_back));
        this.InitSpriteData(330, 587, 3.27f);
    }
}
