package com.example.administrator.Graphic;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by mac on 2017. 12. 17..
 */

public class Victory extends Graphic {
    public Victory() {
        super(AppManager.getInstance().getBitmap(R.drawable.victory));
        this.InitSpriteData(639, 576, 1);
        setPosition(672, 220);
    }
}
