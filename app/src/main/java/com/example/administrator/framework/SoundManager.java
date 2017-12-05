package com.example.administrator.framework;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by Administrator on 2017-11-27.
 */

public class SoundManager {

    private SoundPool soundPool;
    private HashMap soundPoolMap;
    private AudioManager audioManager;
    private Context activty;

    //생성자
    public void Init(Context context) {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap();
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        activty = context;
    }

    //해시맵에 사운드 추가
    public void addSound(int index, int soundID) {
        int id = soundPool.load(activty, soundID, 1);
        soundPoolMap.put(index, id);
    }

    //사운드 재생
    public void Play(int index) {

    }

    //사운드 반복재생
    public void PlayLoop(int index) {

    }

    //싱글턴 패턴 적용
    private  static SoundManager s_instance;

    public static  SoundManager getInstance() {
        if(s_instance == null) {
            s_instance = new SoundManager();
        }
        return  s_instance;
    }
}
