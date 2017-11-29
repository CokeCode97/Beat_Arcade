package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

import java.util.Random;

/**
 * Created by Administrator on 2017-11-29.
 */

public class RhythmNote extends SpriteAnimation {
    GameState gs;
    int note_x = 16, note_y = 0, noteNum;
    float note_speed = 20f;


    public RhythmNote(int noteNum, GameState gs) {
        //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.note)));
        this.InitSpriteData(22, 150, 1, 1);
        //몹 위치 세팅
        this.noteNum = noteNum;
        setNote();

        this.gs = gs;
    }

    public void setNote() {
        note_x += this.noteNum*155;
        setPosition(note_x, note_y);
    }

    public void Move(float x, float y) {
        this.note_x += x;
        this.note_y += y;
        if(note_y > 930) {
            gs.makeJudge(0);
            gs.removeNote(this);
        }
        this.setPosition(note_x, note_y);
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        Move(0 , note_speed);
    }

    public int getNoteNum(){
        return noteNum;
    }
}
