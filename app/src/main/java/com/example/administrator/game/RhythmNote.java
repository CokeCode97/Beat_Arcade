package com.example.administrator.game;

import android.graphics.Bitmap;

import com.example.administrator.framework.SpriteAnimation;

/**
 * Created by Administrator on 2017-11-29.
 */

//리듬게임의 노트를 그려주는 객체
public class RhythmNote extends SpriteAnimation {
    GameState gs;
    int note_x = 16, note_y = 0, noteNum;
    float note_speed = 30f;


    //생성시 노트넘버롸 GameState를 받아옴
    //noteNum = 노트라인번호
    public RhythmNote(Bitmap bitmap, int noteNum, GameState gs) {
        //애니메이션 정보설정
        super(bitmap);
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

    //노트를 판정하는 메소드
    //note_num = 판정할 노트의 nv인덱스넘버
    public void noteJudge(int note_num, int note_line) {
        int judge_point = 0; //판정을 담당할 숫자
        if(note_line == this.getNoteNum()) {
            //시작시 판정변수가 0으로 시작하며 범위를 줄여주면서 해당범위에 들어있다면 1을 더해줌
            if (this.getY() < 930 && this.getY() > 690) {
                judge_point++;
                if (this.getY() < 910 && this.getY() > 710) {
                    judge_point++;
                    if (this.getY() < 890 && this.getY() > 730) {
                        judge_point++;
                        if (this.getY() < 870 && this.getY() > 750) {
                            judge_point++;
                        }
                    }
                }

                //최종적으로 합산된 판정값을 가지고 판정출력객체를 생성
                gs.makeJudge(judge_point);

                //콤보매니저 호출
                gs.rhythmCombo.comboManager();

                //이후 노트를 삭제
                gs.note_Vector.remove(note_num);
                return;
            }
        }
    }

    //노트넘버를 반환
    public int getNoteNum(){
        return noteNum;
    }
}
