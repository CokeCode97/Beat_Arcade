package com.example.administrator.framework;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by Administrator on 2017-11-27.
 */

public class SoundManager {

    private SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private HashMap soundPoolMap;
    private AudioManager audioManager;
    private Context activty;


    //생성자
    public void Init(Context context, String songName) {
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap();
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        activty = context;

        switch (songName) {
            case "함정카드 - 유희왕" : {
                mediaPlayer = MediaPlayer.create(activty, R.raw.trap);
                break;
            }
            case "Blue - 볼빨간사춘기" : {
                mediaPlayer = MediaPlayer.create(activty, R.raw.blue);
                break;
            }
            case "루피 - 원피스" : {
                mediaPlayer = MediaPlayer.create(activty, R.raw.luffy);
                break;
            }
            case "테란 - 스타크래프트" : {
                mediaPlayer = MediaPlayer.create(activty, R.raw.terran);
                break;
            }
        }
    }

    //해시맵에 사운드 추가
    public void addSound(int index, int soundID) {
        int id = soundPool.load(activty, soundID, 1);
        soundPoolMap.put(index, id);
    }

    //사운드 재생
    public void Play(int index) {
        float StreamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        StreamVolume = StreamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundPool.play((Integer)soundPoolMap.get(1), 10, 10, 1, 0, 1f);
            }
        });
    }

    public void MediaPlay() {
        mediaPlayer.start();
    }

    public void MediaStop() {
        mediaPlayer.stop();
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
