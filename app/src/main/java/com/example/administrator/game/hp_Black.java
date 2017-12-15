package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

import java.util.Random;

/**
 * Created by mac on 2017. 12. 12..
 */

public class HP_Black extends Graphic {
    Random random = new Random();

    long time_ShakeStart;
    private boolean HP_Shake = false;
    private boolean HP_ShakeMove = false;

    int position_x;
    int move_x, move_y;

    public HP_Black(int x) {
        super(AppManager.getInstance().getBitmap(R.drawable.hp_black));
        this.InitSpriteData(70, 400, 1.5f);
        this.position_x = x;
        setPosition(560 + position_x, 30);
    }

    //HP_Shake시 체력바를 흔들어줌
    public void Update(long GameTime) {
        int hp_X = this.getX();
        int hp_Y = this.getY();
        if(HP_Shake) {
            setPosition(600 + position_x, 30);
            setHPbar();
            if(GameTime - time_ShakeStart > 500 && HP_ShakeMove) {
                HP_Shake = false;
            } else {
                if (!HP_ShakeMove) {
                    Double angle = Math.toRadians(random.nextInt(360));
                    move_x = (int) (Math.sin(angle) * 20);
                    move_y = (int) (Math.cos(angle) * 20);
                    setPosition(hp_X + move_x, hp_Y + move_y);
                    setHPbar();
                    HP_ShakeMove = true;
                } else {
                    setPosition(hp_X - move_x, hp_Y - move_y);
                    setHPbar();
                    HP_ShakeMove = false;
                }
            }
        }
    }

    public void setShake(long time_ShakeStart) {
        HP_Shake = true;
        this.time_ShakeStart = time_ShakeStart;
    }

    public void setHPbar() {
        ObjectManager.hp_Red_Vector.get(ObjectManager.hp_Black_Vector.indexOf(this)).setPosition(this.getX()+ 24, this.getY() + 19);
        ObjectManager.hp_Yellow_Vector.get(ObjectManager.hp_Black_Vector.indexOf(this)).setPosition(this.getX()+ 24, this.getY() + 19);
    }
}
