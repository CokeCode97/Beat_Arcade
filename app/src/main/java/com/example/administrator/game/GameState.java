package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.IState;
import com.example.administrator.framework.R;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Administrator on 2017-11-28.
 */

public class GameState implements IState {
    Random r = new Random();

    //리듬 게임에서 여러개가 나오는 객체들을 벡터로 관리
    Vector<RhythmNote> nv = new Vector<RhythmNote>();
    Vector<RhythmJudge> jv = new Vector<RhythmJudge>();

    //게임 배경을 그려주는 객체들
    RhythmBackGourndTop rbgt = new RhythmBackGourndTop();
    RhythmBackGroundBottom1 rbgb1 = new RhythmBackGroundBottom1();
    RhythmBackGroundBottom2 rbgb2 = new RhythmBackGroundBottom2();
    RhythmBackGroundBottom3 rbgb3 = new RhythmBackGroundBottom3();

    //시간을 측정할 기준 Last
    long Last = System.currentTimeMillis();

    @Override
    public void Init() {
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        //GameTime에 현재 시간을 넣어줌
        long GameTime = System.currentTimeMillis();

        //nv벡터 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < nv.size(); i++) {
            nv.get(i).Update(GameTime);
        }

        //jv벡터 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < jv.size(); i++) {
            jv.get(i).Update(GameTime);
        }

        MakeNote();
    }


    //객체를 렌더링
    @Override
    public void Render(Canvas canvas) {
        //노트가 내려오는 부분의 배경을 그려줌
        rbgt.Draw(canvas);

        //nv벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < nv.size(); i++) {
            nv.get(i).Draw(canvas);
        }

        //jv벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < jv.size(); i++) {
            jv.get(i).Draw(canvas);
        }

        //버튼들을 그려줌
        rbgb1.Draw(canvas);
        rbgb2.Draw(canvas);
        rbgb3.Draw(canvas);
    }


    //노트를 생성함
    public void MakeNote() {
        //500ms마다 노트를 생성
        if(System.currentTimeMillis() - Last >= 500) {
            Last = System.currentTimeMillis();

            //랜덤으로 노트번호를 0~2까지 정하여 생성한후 노트를 nv 추가
            RhythmNote note = new RhythmNote(r.nextInt(3), this);
            nv.add(note);
        }
    }


    //판정을 생성함
    public void makeJudge(int judge_num) {
        RhythmJudge judge;

        //판정 숫자에 따라 각기 다른 비트맵을 넣어 판정을 생성하고 jv에 넣어줌
        if(judge_num == 4) {
            judge = new RhythmJudge(AppManager.getInstance().getBitmap(R.drawable.perfect), this);
        }
        else if(judge_num == 3) {
            judge = new RhythmJudge(AppManager.getInstance().getBitmap(R.drawable.good), this);
        }
        else if(judge_num == 2) {
            judge = new RhythmJudge(AppManager.getInstance().getBitmap(R.drawable.bad), this);
        }
        else if(judge_num == 1) {
            judge = new RhythmJudge(AppManager.getInstance().getBitmap(R.drawable.poor), this);
        }
        else {
            judge = new RhythmJudge(AppManager.getInstance().getBitmap(R.drawable.miss), this);
        }
        jv.add(judge);
    }

    //선택된 노트를 제거함
    public void removeNote(RhythmNote note) {
        nv.remove(note);
    }

    //선택된 판정출력을 제거함
    public void removeJudge(RhythmJudge jedge) {
        jv.remove(jedge);
    }


    //노트를 판정하는 메소드
    public void noteJudge(int note_num) {
        int judge_point = 0;

        //시작시 판정변수가 0으로 시작하며 범위를 줄여주면서 해당범위에 들어있다면 1을 더해줌
        if(nv.get(note_num).getY() < 930 && nv.get(note_num).getY() > 690) {
            judge_point++;
            if(nv.get(note_num).getY() < 910 && nv.get(note_num).getY() > 710) {
                judge_point++;
                if(nv.get(note_num).getY() < 890 && nv.get(note_num).getY() > 730) {
                    judge_point++;
                    if(nv.get(note_num).getY() < 870 && nv.get(note_num).getY() > 750) {
                        judge_point++;
                    }
                }
            }

            //최종적으로 합산된 판정값을 가지고 판정출력객체를 생성 이후 노트를 삭제
            makeJudge(judge_point);
            nv.remove(note_num);
            return;
        }
    }

    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }


    //터치입력에 대한 반응
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x, y;
        int note_i = 0;

        x = (int)event.getX();
        y = (int)event.getY();

        //터치가능 범위를 지정해줄 렉트
        Rect rt = new Rect();

        //렉트에 1번 버튼을 넣어줌
        rt.set(rbgb1.getX_R(), rbgb1.getY_R(), rbgb1.getX_R() + rbgb1.getSpriteWidth(), rbgb1.getY_R() + rbgb1.getSpriteHeight());
        //1번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
        if(rt.contains(x, y) && nv.size() > 0) {
            for(int i =0; i < nv.size(); i++) {
                if(nv.get(i).getNoteNum() == 0) {
                    note_i = i;
                    break;
                }
            }
            noteJudge(note_i);
        }

        //렉트에 2번 버튼을 넣어줌
        rt.set(rbgb2.getX_R(), rbgb2.getY_R(), rbgb2.getX_R() + rbgb2.getSpriteWidth(), rbgb2.getY_R() + rbgb2.getSpriteHeight());
        //2번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
        if(rt.contains(x, y) && nv.size() > 0) {
            for(int i =0; i < nv.size(); i++) {
                if(nv.get(i).getNoteNum() == 1) {
                    note_i = i;
                    break;
                }
            }
            noteJudge(note_i);
        }

        //렉트에 3번 버튼을 넣어줌
        rt.set(rbgb3.getX_R(), rbgb3.getY_R(), rbgb3.getX_R() + rbgb3.getSpriteWidth(), rbgb3.getY_R() + rbgb3.getSpriteHeight());
        //3번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
        if(rt.contains(x, y) && nv.size() > 0) {
            for(int i =0; i < nv.size(); i++) {
                if(nv.get(i).getNoteNum() == 2) {
                    note_i = i;
                    break;
                }
            }
            noteJudge(note_i);
        }
        return false;
    }
}
