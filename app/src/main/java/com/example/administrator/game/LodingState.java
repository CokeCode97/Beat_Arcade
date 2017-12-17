package com.example.administrator.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.IState;
import com.example.administrator.framework.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Administrator on 2017-12-13.
 */

public class LodingState implements IState {
    String songName;
    public void setSongName(String songName) {this.songName = songName;}

    public static Vector<HashMap<String, Integer>> noteDataVector = new Vector<>();
    public static Vector<HashMap<String, Integer>> getNoteDataVector() {
        return noteDataVector;
    }

    LodingImage lodingImage;
    LodingFrame lodingFrame = new LodingFrame();

    @Override
    public void Init(Context context) {
        InputStream iStream;
        ParserThread parserThread;


        switch (songName) {
            case "함정카드 - 유희왕" : {
                lodingImage = new LodingImage(AppManager.getInstance().getBitmap(R.drawable.trap));
                iStream = context.getResources().openRawResource(R.raw.note_trap);
                parserThread = new ParserThread(noteDataVector, iStream);
                parserThread.start();
                break;
            }
            case "Blue - 볼빨간사춘기" : {
                lodingImage = new LodingImage(AppManager.getInstance().getBitmap(R.drawable.blue));
                iStream = context.getResources().openRawResource(R.raw.note_blue);
                parserThread = new ParserThread(noteDataVector, iStream);
                parserThread.start();
                break;
            }
            case "루피 - 원피스" : {
                lodingImage = new LodingImage(AppManager.getInstance().getBitmap(R.drawable.onepiece));
                iStream = context.getResources().openRawResource(R.raw.note_luffy);
                parserThread = new ParserThread(noteDataVector, iStream);
                parserThread.start();
                break;
            }
            case "테란 - 스타크래프트" : {
                lodingImage = new LodingImage(AppManager.getInstance().getBitmap(R.drawable.terran));
                iStream = context.getResources().openRawResource(R.raw.note_terran);
                parserThread = new ParserThread(noteDataVector, iStream);
                parserThread.start();
                break;
            }
        }
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        ObjectManager.lodingBarFront.Update();
    }

    @Override
    public void Render(Canvas canvas) {
        ObjectManager.lodingBack.Draw(canvas);
        ObjectManager.lodingBarBack.Draw(canvas);
        ObjectManager.lodingBarFront.Draw(canvas);
        lodingImage.Draw(canvas);
        lodingFrame.Draw(canvas);
    }

    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
