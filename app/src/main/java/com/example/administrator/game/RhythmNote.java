package com.example.administrator.game;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.R;
import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

//리듬게임의 노트를 그려주는 객체
public class RhythmNote extends SpriteAnimation {
    GameState gs;
    int note_x = 16, note_y = 0, noteNum;
    float note_speed = 20f;


    //생성시 노트넘버롸 GameState를 받아옴
    //noteNum = 노트라인번호
    public RhythmNote(int noteNum, GameState gs) {
        //애니메이션 정보설정
        super((AppManager.getInstance().getBitmap(R.drawable.note)));
        this.InitSpriteData(22, 150, 1, 1);
        //위치 세팅
        this.noteNum = noteNum;
        setNote();
        //GameState세팅
        this.gs = gs;
    }

    //노트번호에 맞게 위치를 지정
    public void setNote() {
        note_x += this.noteNum*155;
        setPosition(note_x, note_y);
    }

    //노트를 움직임 930밑으로 내려가게 될 경우 miss판정을 띄우고 노트를 제거함
    //x, y 이것이 움직일 좌표들
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

    //노트넘버를 반환
    public int getNoteNum(){
        return noteNum;
    }
}
