package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.IState;
import com.example.administrator.framework.R;
import com.example.administrator.networks.ClientWork;

import java.util.Random;
import java.util.Vector;

/**
 * Created by Administrator on 2017-11-28.
 */

public class GameState extends Thread implements IState {
    Random random = new Random();


    //게임에서 여러개가 나오는 객체들을 벡터로 관리
    Vector<RhythmNote> note_Vector = new Vector<>();
    Vector<RhythmJudge> judge_Vector = new Vector<>();
    Vector<RhythmComboNumber> comboNumber_Vector = new Vector<>();

    Vector<Player> player_Vector = new Vector<>();

    //게임 배경을 그려주는 객체들
    RhythmBackGourndTop rhythmBackGourndTop = new RhythmBackGourndTop();
    RhythmBackGroundBottom1 rhythmBackGroundBottom1 = new RhythmBackGroundBottom1();
    RhythmBackGroundBottom2 rhythmBackGroundBottom2 = new RhythmBackGroundBottom2();
    RhythmBackGroundBottom3 rhythmBackGroundBottom3 = new RhythmBackGroundBottom3();
    ShootingBackground shootingBackground = new ShootingBackground();

    //Dpad를 구성하는 원들
    Circle dpad_Circle = new Circle();
    MiniCircle dpad_MiniCircle = new MiniCircle();

    //리듬콤보글자를 출력하는 객체
    RhythmCombo rhythmCombo;

    //비트맵 데이터
    Bitmap perfect_bitmap;
    Bitmap good_bitmap;
    Bitmap bad_bitmap;
    Bitmap poor_bitmap;
    Bitmap miss_bitmap;
    Bitmap note_bitmap;
    Bitmap combo_bitmap;
    Bitmap comboNumber_bitmap;


    long Last = System.currentTimeMillis(); //시간을 측정할 기준 Last

    int t2_x = 0, t2_y = 0;     //D-Pad좌표
    double angle;               //각도
    double dx, dy;              //D-Pad의 중심과 사용자가 누른곳 사이의 거리

    int player_Num = 0;
    boolean check = true;

    @Override
    public void run() {
        while(true) {
            if(check) {
                this.Update();
                check = false;
            }
        }
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


    //시작될때 여러 비트맵 데이터나 객체들을 메모리에 올림
    @Override
    public void Init() {
        perfect_bitmap = AppManager.getInstance().getBitmap(R.drawable.perfect);
        good_bitmap = AppManager.getInstance().getBitmap(R.drawable.good);
        bad_bitmap = AppManager.getInstance().getBitmap(R.drawable.bad);
        poor_bitmap = AppManager.getInstance().getBitmap(R.drawable.poor);
        miss_bitmap = AppManager.getInstance().getBitmap(R.drawable.miss);
        note_bitmap = AppManager.getInstance().getBitmap(R.drawable.note);
        combo_bitmap = AppManager.getInstance().getBitmap(R.drawable.combo);
        comboNumber_bitmap = AppManager.getInstance().getBitmap(R.drawable.number_sprite);

       rhythmCombo = new RhythmCombo(combo_bitmap, this);
        player_Vector.add(new Player());
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        //GameTime에 현재 시간을 넣어줌
        long GameTime = System.currentTimeMillis();

        //nv벡터 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < note_Vector.size(); i++) {
            note_Vector.get(i).Update(GameTime);
        }

        //jv벡터 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < judge_Vector.size(); i++) {
            judge_Vector.get(i).Update(GameTime);
        }

        //jv벡터 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < player_Vector.size(); i++) {
            player_Vector.get(i).Update(GameTime);
        }

        MakeNote();
    }


    //객체를 렌더링
    @Override
    public void Render(Canvas canvas) {
        //노트가 내려오는 부분의 배경을 그려줌
        rhythmBackGourndTop.Draw(canvas);
        shootingBackground.Draw(canvas);


        //nv벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < note_Vector.size(); i++) {
            note_Vector.get(i).Draw(canvas);
        }

        //jv벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < judge_Vector.size(); i++) {
            judge_Vector.get(i).Draw(canvas);
        }

        //콤보가 2보다 크면 콤보와 관련된 오브젝트들을 렌더링함
        if(rhythmCombo.combo > 2) {
            rhythmCombo.Draw(canvas);
            for(int i = 0; i < comboNumber_Vector .size(); i++) {
                comboNumber_Vector .get(i).Draw(canvas);
            }
        }

        //버튼들을 그려줌
        rhythmBackGroundBottom1.Draw(canvas);
        rhythmBackGroundBottom2.Draw(canvas);
        rhythmBackGroundBottom3.Draw(canvas);

        for (int i = 0; i < player_Vector.size(); i++) {
            player_Vector.get(i).Draw(canvas);
        }



        dpad_Circle.Draw(canvas);
        dpad_MiniCircle.Draw(canvas);
    }


    //노트를 생성함
    public void MakeNote() {
        //500ms마다 노트를 생성
        if(System.currentTimeMillis() - Last >= 500) {
            Last = System.currentTimeMillis();

            //랜덤으로 노트번호를 0~2까지 정하여 생성한후 노트를 nv 추가
            RhythmNote note = new RhythmNote(note_bitmap, random.nextInt(3), this);
            note_Vector.add(note);
        }
    }


    //판정을 생성함
    //judge_num = 판정점수
    public void makeJudge(int judge_num) {
        RhythmJudge judge;

        rhythmCombo.comboup();
        //판정 숫자에 따라 각기 다른 비트맵을 넣어 판정을 생성하고 jv에 넣어줌
        if(judge_num == 4) {
            judge = new RhythmJudge(perfect_bitmap, this);
        }
        else if(judge_num == 3) {
            judge = new RhythmJudge(good_bitmap, this);
        }
        else if(judge_num == 2) {
            judge = new RhythmJudge(bad_bitmap, this);
        }
        else if(judge_num == 1) {
            judge = new RhythmJudge(poor_bitmap, this);
        }
        else {
            judge = new RhythmJudge(miss_bitmap, this);
            //미스가 발생할 경우 콤보를  0으로 바꾸고 cnv속 객체를 전부 지움
            rhythmCombo.comboReset();
        }
        judge_Vector.add(judge);
    }


    //선택된 노트를 제거함
    //note = 제거할 노트객체
    public void removeNote(RhythmNote note) {
        note_Vector.remove(note);
    }


    //선택된 판정출력을 제거함
    //judge = 제거할 판정표시객체
    public void removeJudge(RhythmJudge judge) {
        judge_Vector.remove(judge);
    }


    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }


    //터치입력에 대한 반응
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();                      //터치액션을 담음
        int t1_x = 0, t1_y = 0;                              //리듬게임 부분의 터치를 저장
        int note_i = 0;                                      //노트의 인덱스
        int note_line = 0;                                   //노트의 라인
        int line = (int)(500 * GameActivity.size);          //리듬게임과 슈팅게임의 경계선

        //터치가능 범위를 지정해줄 렉트
        Rect rt = new Rect();
        Rect rt1 = new Rect();
        Rect rt2 = new Rect();
        Rect rt3 = new Rect();


        //렉트에 D-Pad위치를 넣어줌
        rt.set(dpad_Circle.getX_R(), dpad_Circle.getY_R(), dpad_Circle.getX_R() + dpad_Circle.getSpriteWidth(), dpad_Circle.getY_R() + dpad_Circle.getSpriteHeight());
        //렉트에 1번 버튼을 넣어줌
        rt1.set(rhythmBackGroundBottom1.getX_R(), rhythmBackGroundBottom1.getY_R(), rhythmBackGroundBottom1.getX_R() + rhythmBackGroundBottom1.getSpriteWidth(), rhythmBackGroundBottom1.getY_R() + rhythmBackGroundBottom1.getSpriteHeight());
        //렉트에 2번 버튼을 넣어줌
        rt2.set(rhythmBackGroundBottom2.getX_R(), rhythmBackGroundBottom2.getY_R(), rhythmBackGroundBottom2.getX_R() + rhythmBackGroundBottom2.getSpriteWidth(), rhythmBackGroundBottom2.getY_R() + rhythmBackGroundBottom2.getSpriteHeight());
        //렉트에 3번 버튼을 넣어줌
        rt3.set(rhythmBackGroundBottom3.getX_R(), rhythmBackGroundBottom3.getY_R(), rhythmBackGroundBottom3.getX_R() + rhythmBackGroundBottom3.getSpriteWidth(), rhythmBackGroundBottom3.getY_R() + rhythmBackGroundBottom3.getSpriteHeight());


        //터치받은 액션으로 부터 좌표를 받아옴
        if(event.getPointerCount() > 1) {
            for (int i = 0; i < event.getPointerCount(); i++) {
                if (event.getX(i) < line) {
                    t1_x = (int) event.getX(i);
                    t1_y = (int) event.getY(i);
                } else {
                    t2_x = (int) event.getX(i);
                    t2_y = (int) event.getY(i);
                }
            }
        } else {
            if (event.getX() < line) {
                t1_x = (int) event.getX();
                t1_y = (int) event.getY();
            } else {
                t2_x = (int) event.getX();
                t2_y = (int) event.getY();
            }
        }

        //리듬게임쪽을 터치했다면 리듬노트 검사
        if(t1_x != 0 && t1_y != 0 && note_Vector.size()>0) {
            //1번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            if (rt1.contains(t1_x, t1_y)) {
                note_line = 0;
            }
            //2번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            else if (rt2.contains(t1_x, t1_y)) {
                note_line = 1;
            }
            //3번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            else if (rt3.contains(t1_x, t1_y)) {
                note_line = 2;
            }
            //이후 해당 라인에 맞는 번호에 있는 노트중 가장 먼저 있는 노트를 찾음
            for (int i = 0; i < note_Vector.size(); i++) {
                if (note_Vector.get(i).getNoteNum() == note_line) {
                    note_i = i;
                    break;
                }
            }
            //그 노트를 판정함
            note_Vector.get(note_i).noteJudge(note_i, note_line);
        }


        //D-Pad에 터치좌표가 있을경우
        if(rt.contains(t2_x, t2_y)) {
            //D-Pad의 버튼을 누른곳으로 움직여줌
            dpad_MiniCircle.setPosition((int)((t2_x-75) / GameActivity.size), (int) ((t2_y-75) / GameActivity.size));
            //각도계산기를 호출
            calcAngle();
        }

        //손을 땔때 dpad의 값들을 초기화 시켜줌
        if(action == MotionEvent.ACTION_UP) {
            t2_x = 0;
            t2_y = 0;
            dpad_MiniCircle.setPosition(dpad_MiniCircle.x_ori, dpad_MiniCircle.y_ori);
        }

        //몹에 무빙을 위한 판정요소들을 넣어줌
        player_Vector.get(player_Num).setDis(dx, dy);
        player_Vector.get(player_Num).setTouch(t2_x, t2_y);
        player_Vector.get(player_Num).setdpadCircle(dpad_Circle.getX_R(), dpad_Circle.getY_R());

        return true;
    }


    //D-Pad에서 사용자가 누르고 있는 곳과 D-Pad의 중심의 각을 구하는 메소드
    public void calcAngle() {
        dx = t2_x-(dpad_Circle.getX_R() + 200 * GameActivity.size);
        dy = t2_y-(dpad_Circle.getY_R() + 200 * GameActivity.size);

        angle = 90 + Math.toDegrees(Math.atan2(dy, dx));
        player_Vector.get(player_Num).setAngle(angle);
    }

}