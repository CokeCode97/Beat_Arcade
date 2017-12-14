package com.example.administrator.game;

import java.util.Random;
import java.util.Vector;

/**
 * Created by mac on 2017. 12. 13..
 */

public class ObjectManager {
    private static Random random = new Random();
    private static long Last = 0;


    //게임에서 여러개가 나오는 객체들을 벡터로 관리
    public static Vector<RhythmNote> note_Vector = new Vector<>();
    public static Vector<RhythmJudge> judge_Vector = new Vector<>();
    public static Vector<RhythmComboNumber> comboNumber_Vector = new Vector<>();
    public static Vector<Bullet> bullet_Vector = new Vector<>();
    public static Vector<HP_Black> hp_Black_Vector = new Vector<>();
    public static Vector<HP_Red> hp_Red_Vector = new Vector<>();

    //노트를 생성함
    public static void MakeNote() {
        //500ms마다 노트를 생성
        if(System.currentTimeMillis() - Last >= 500) {
            Last = System.currentTimeMillis();
            int rhythme_num = random.nextInt(3);
            //랜덤으로 노트번호를 0~2까지 정하여 생성한후 노트를 nv 추가
            RhythmNote note = new RhythmNote(GameState.note_Bitmap[rhythme_num], rhythme_num);
            note_Vector.add(note);
        }
    }


    //판정을 생성함
    //judge_num = 판정점수
    public static void makeJudge(int judge_num) {
        RhythmJudge judge;
        if(judge_num != 0) {
            GameState.rhythmCombo.comboup();
        }
        judge = new RhythmJudge(GameState.judge_Bitmap[judge_num]);

        //판정 숫자에 따라 각기 다른 비트맵을 넣어 판정을 생성하고 jv에 넣어줌
        if(judge_num == 0)
            GameState.rhythmCombo.comboReset();


        judge_Vector.add(judge);
    }

    //총알을 만듦
    //angle = 총알이 날아가는 각도, x,y = 총알이 생성되는 좌표
    public static void makeBullet(double angle, int x, int y){
        bullet_Vector.add(new Bullet(GameState.bullet_Bitmap, angle, x, y));
    }


    //선택된 노트를 제거함
    //note = 제거할 노트객체
    public static void removeNote(RhythmNote note) {
        note_Vector.remove(note);
    }


    //선택된 판정출력을 제거함
    //judge = 제거할 판정표시객체
    public static void removeJudge(RhythmJudge judge) {
        judge_Vector.remove(judge);
    }

    //선택된 Bullet을 제거함
    //bullet = 제거할 판정표시객체
    public static void removeBullet(Bullet bullet) {
        bullet_Vector.remove(bullet);
    }
}
