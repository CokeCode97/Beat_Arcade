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

    Vector<RhythmNote> nv = new Vector<RhythmNote>();
    Vector<RhythmJudge> jv = new Vector<RhythmJudge>();

    RhythmBackGourndTop rbgt = new RhythmBackGourndTop();
    RhythmBackGroundBottom1 rbgb1 = new RhythmBackGroundBottom1();
    RhythmBackGroundBottom2 rbgb2 = new RhythmBackGroundBottom2();
    RhythmBackGroundBottom3 rbgb3 = new RhythmBackGroundBottom3();


    long Last = System.currentTimeMillis();

    @Override
    public void Init() {
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        //if(nv.size() > 0) {
        for (int i = 0; i < nv.size(); i++) {
            nv.get(i).Update(GameTime);
        }

        for (int i = 0; i < jv.size(); i++) {
            jv.get(i).Update(GameTime);
        }
        MakeNote();
    }

    @Override
    public void Render(Canvas canvas) {
        rbgt.Draw(canvas);

        for (int i = 0; i < nv.size(); i++) {
            nv.get(i).Draw(canvas);
        }

        for (int i = 0; i < jv.size(); i++) {
            jv.get(i).Draw(canvas);
        }

        rbgb1.Draw(canvas);
        rbgb2.Draw(canvas);
        rbgb3.Draw(canvas);
    }

    public void MakeNote() {
        if(System.currentTimeMillis() - Last >= 500) {
            Last = System.currentTimeMillis();
            RhythmNote note = new RhythmNote(r.nextInt(3), this);
            nv.add(note);
        }
    }

    public void makeJudge(int judge_num) {
        RhythmJudge judge;
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

    public void removeNote(RhythmNote note) {
        nv.remove(note);
    }

    public void removeJudge(RhythmJudge jedge) {
        jv.remove(jedge);
    }

    public void noteJudge(int note_num) {
        int judge_point = 0;
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
            makeJudge(judge_point);
            nv.remove(note_num);
            return;
        }
    }

    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x, y;
        int note_i = 0;

        x = (int)event.getX();
        y = (int)event.getY();

        Rect rt = new Rect();

        rt.set(rbgb1.getX_R(), rbgb1.getY_R(), rbgb1.getX_R() + rbgb1.getSpriteWidth(), rbgb1.getY_R() + rbgb1.getSpriteHeight());
        if(rt.contains(x, y) && nv.size() > 0) {
            for(int i =0; i < nv.size(); i++) {
                if(nv.get(i).getNoteNum() == 0) {
                    note_i = i;
                    break;
                }
            }
            noteJudge(note_i);
        }

        rt.set(rbgb2.getX_R(), rbgb2.getY_R(), rbgb2.getX_R() + rbgb2.getSpriteWidth(), rbgb2.getY_R() + rbgb2.getSpriteHeight());
        if(rt.contains(x, y) && nv.size() > 0) {
            for(int i =0; i < nv.size(); i++) {
                if(nv.get(i).getNoteNum() == 1) {
                    note_i = i;
                    break;
                }
            }
            noteJudge(note_i);
        }

        rt.set(rbgb3.getX_R(), rbgb3.getY_R(), rbgb3.getX_R() + rbgb3.getSpriteWidth(), rbgb3.getY_R() + rbgb3.getSpriteHeight());
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
