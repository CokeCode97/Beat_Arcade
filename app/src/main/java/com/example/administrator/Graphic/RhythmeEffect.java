package com.example.administrator.Graphic;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.Graphic;
import com.example.administrator.framework.R;

/**
 * Created by Administrator on 2017-12-14.
 */

public class RhythmeEffect extends Graphic {
    private int x_ori = -300;
    private int effect_x = 16;
    private boolean print_Check = false;
    private long time_Start;

    public RhythmeEffect (Bitmap bitmap, int line_Num) {
        super(bitmap);

        //라인에 맞게 좌표설정
        effect_x += line_Num*155;
        this.InitSpriteData(173, 37, 4);
        setPosition(x_ori, 0);
    }

    //TODO 라인번호에 맞게 위치를 지정
    public void setNote(long time_Start) {
        setPosition(effect_x, 125);
        this.time_Start = time_Start;
        print_Check = true;
    }

    //TODO 만약 출력중이라면 출력된 시간을 계산하여 일정시간 이후 제자리로 돌림
    public void Update(long time_Now) {
        if(print_Check) {
            if(time_Now - time_Start >= 300) {
                setPosition(x_ori, 0);
            }
        }
    }
}
