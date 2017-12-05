package com.example.administrator.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ResourceBundle;

/**
 * Created by Administrator on 2017-11-27.
 */

public class AppManager {

    private GameView gameView;
    private Resources resources;

    //비트맵을 반환하는 메소드 여기서 r이 먼지는 잘 모르겠음 아마 'R.drawable.파일이름' 이게 인트형인듯?
    public Bitmap getBitmap(int r) {
        return BitmapFactory.decodeResource(resources, r);
    }

    //게임뷰 세팅
    void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    //리소스 세팅
    void setResources(Resources resources) {
        this.resources = resources;
    }

    //게임뷰 리턴
    public GameView getGameView() {
        return this.gameView;
    }

    //리소스 리턴
    public Resources getResources() {
        return this.resources;
    }

    //언제 어디서든 접근가능 하도록 스태틱으로 생성
    private static AppManager s_instance;

    //앱매니저를 리턴하는 메소드
    public static AppManager getInstance() {
        if(s_instance == null) {
            s_instance = new AppManager();
        }
        return s_instance;
    }
}
