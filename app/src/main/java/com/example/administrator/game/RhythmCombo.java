package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.SpriteAnimation;
import com.example.administrator.networks.ClientWork;

/**
 * Created by Administrator on 2017-11-30.
 */

public class RhythmCombo extends SpriteAnimation{
    private int combo;
    private int judge_Point = 0;

    public RhythmCombo (Bitmap bitmap) {
        super(bitmap);
        combo = 0;
        this.InitSpriteData(70, 236, 1, 1);
        setPosition(132, 300);
    }


    //TODO combo글자의 위치를 알맞게 조절함
    //num = 콤보의 자릿수
    public void setcombo(int num) {
        int x = 132-33*num;
        setPosition(x, 300);
    }


    //TODO 콤보를 관리하는 메소드
    public void comboManager() {
        int num = 0;        //콤보의 자릿수
        int check = 1;      //콤보의 자릿수를 구하기위해 사용될 체크
        int n = 10;         //콤보 특정자릿수의 숫자를 명확하게 알아내기 위한 변수

        //콤보가 몇자리수인지 체크하는 메소드
        while (combo / check > 0) {
            num++;
            check = check * 10;
        }

        //자리수보다 cnv속의 객체수가 적을경우 새로운 콤보넘버객체를 생성하여 그 수를 맞춰줌
        if (ObjectManager.comboNumber_Vector.size() < num) {
            ObjectManager.comboNumber_Vector.add(new RhythmComboNumber(GameState.comboNumber_Bitmap));
        }

        //combo글자의 위치를 알맞게 조절함
        this.setcombo(num);

        //cnv에 있는 콤보넘버 객체들을 각각에 알맞는 숫자와 위치에 배치되도록 해줌
        for (int i = 0; i < num; i++) {
            ObjectManager.comboNumber_Vector .get(i).setcombo(this.getX(), ((combo % n) / (n / 10)), num - i - 1);
            n *= 10;
        }
    }

    //TODO 콤보를 1추가하고 콤보가 5의 배수일때 마다 공격메세지를 보냄
    public void comboup(int judge_Point) {
        combo++;
        this.judge_Point += judge_Point;
        if(combo % 5 == 0 && combo != 0) {
            //서버에 공격했다고 메시지 전송
            ClientWork.write("Attack " + this.judge_Point * (1 + (combo/5)*0.2));
            this.judge_Point = 0;
        }
    }

    //TODO 콤보를 리셋함
    public void comboReset() {
        combo = 0;
        this.judge_Point = 0;
        ObjectManager.comboNumber_Vector .clear();
    }

    public int getCombo() {return combo; }
}
