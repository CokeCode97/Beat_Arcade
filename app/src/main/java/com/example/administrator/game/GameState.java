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
    Vector<RhythmComboNumber> cnv = new Vector<>();

    //게임 배경을 그려주는 객체들
    RhythmBackGourndTop rbgt = new RhythmBackGourndTop();
    RhythmBackGroundBottom1 rbgb1 = new RhythmBackGroundBottom1();
    RhythmBackGroundBottom2 rbgb2 = new RhythmBackGroundBottom2();
    RhythmBackGroundBottom3 rbgb3 = new RhythmBackGroundBottom3();

    //Dpad를 구성하는 원들
    Circle dpad_Circle = new Circle();
    MiniCircle dpad_MiniCircle = new MiniCircle();

    //리듬콤보글자를 출력하는 객체
    RhythmCombo rcb = new RhythmCombo();

    //몹객체
    Mob m = new Mob();


    long Last = System.currentTimeMillis(); //시간을 측정할 기준 Last

    int combo = 0;               //콤보
    int t2_x = 0, t2_y = 0;     //D-Pad좌표
    double angle;               //각도
    double dx, dy;              //D-Pad의 중심과 사용자가 누른곳 사이의 거리



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

        //몹 업데이트
        m.Update(GameTime);

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

        //콤보가 2보다 크면 콤보와 관련된 오브젝트들을 렌더링함
        if(combo > 2) {
            rcb.Draw(canvas);
            for(int i = 0; i < cnv.size(); i++) {
                cnv.get(i).Draw(canvas);
            }
        }

        //버튼들을 그려줌
        rbgb1.Draw(canvas);
        rbgb2.Draw(canvas);
        rbgb3.Draw(canvas);

        m.Draw(canvas);

        dpad_Circle.Draw(canvas);
        dpad_MiniCircle.Draw(canvas);
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
    //judge_num = 판정점수
    public void makeJudge(int judge_num) {
        RhythmJudge judge;

        combo++;
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
            //미스가 발생할 경우 콤보를  0으로 바꾸고 cnv속 객체를 전부 지움
            combo = 0;
            cnv.clear();
        }
        jv.add(judge);
    }


    //선택된 노트를 제거함
    //note = 제거할 노트객체
    public void removeNote(RhythmNote note) {
        nv.remove(note);
    }


    //선택된 판정출력을 제거함
    //judge = 제거할 판정표시객체
    public void removeJudge(RhythmJudge judge) {
        jv.remove(judge);
    }


    //노트를 판정하는 메소드
    //note_num = 판정할 노트의 nv인덱스넘버
    public void noteJudge(int note_num, int note_line) {
        int judge_point = 0; //판정을 담당할 숫자
        if(note_line == nv.get(note_num).getNoteNum()) {
            //시작시 판정변수가 0으로 시작하며 범위를 줄여주면서 해당범위에 들어있다면 1을 더해줌
            if (nv.get(note_num).getY() < 930 && nv.get(note_num).getY() > 690) {
                judge_point++;
                if (nv.get(note_num).getY() < 910 && nv.get(note_num).getY() > 710) {
                    judge_point++;
                    if (nv.get(note_num).getY() < 890 && nv.get(note_num).getY() > 730) {
                        judge_point++;
                        if (nv.get(note_num).getY() < 870 && nv.get(note_num).getY() > 750) {
                            judge_point++;
                        }
                    }
                }

                //최종적으로 합산된 판정값을 가지고 판정출력객체를 생성
                makeJudge(judge_point);
                //콤보매니저 호출
                comboManager();

                //이후 노트를 삭제
                nv.remove(note_num);
                return;
            }
        }
    }


    //콤보를 관리하는 메소드
    public void comboManager() {
        int num = 0;        //콤보의 자릿수
        int check = 1;      //콤보의 자릿수를 구하기위해 사용될 체크
        int n = 10;         //콤보 특정자릿수의 숫자를 명확하게 알아내기 위한 변수

        //콤보가 몇자리수인지 체크하는 메소드
        while(combo / check > 0) {
            num++;
            check = check*10;
        }

        //자리수보다 cnv속의 객체수가 적을경우 새로운 콤보넘버객체를 생성하여 그 수를 맞춰줌
        if(cnv.size() < num) {
            cnv.add(new RhythmComboNumber());
        }

        //combo글자의 위치를 알맞게 조절함
        rcb.setcombo(num);

        //cnv에 있는 콤보넘버 객체들을 각각에 알맞는 숫자와 위치에 배치되도록 해줌
        for(int i = 0; i < num; i++) {
            cnv.get(i).setcombo(rcb.getX(), ((combo % n)/(n/10)), num-i-1);
            n*=10;
        }
    }


    //D-Pad에서 사용자가 누르고 있는 곳과 D-Pad의 중심의 각을 구하는 메소드
    public void calcAmgle() {
        dx = t2_x-(dpad_Circle.getX_R() + 200 * GameActivity.size);
        dy = t2_y-(dpad_Circle.getY_R() + 200 * GameActivity.size);

        angle = 90 + Math.toDegrees(Math.atan2(dy, dx));
        m.setAngle(angle);
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
        rt1.set(rbgb1.getX_R(), rbgb1.getY_R(), rbgb1.getX_R() + rbgb1.getSpriteWidth(), rbgb1.getY_R() + rbgb1.getSpriteHeight());
        //렉트에 2번 버튼을 넣어줌
        rt2.set(rbgb2.getX_R(), rbgb2.getY_R(), rbgb2.getX_R() + rbgb2.getSpriteWidth(), rbgb2.getY_R() + rbgb2.getSpriteHeight());
        //렉트에 3번 버튼을 넣어줌
        rt3.set(rbgb3.getX_R(), rbgb3.getY_R(), rbgb3.getX_R() + rbgb3.getSpriteWidth(), rbgb3.getY_R() + rbgb3.getSpriteHeight());


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


        if(t1_x != 0 && t1_y != 0 && nv.size()>0) {
            //1번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            if (rt1.contains(t1_x, t1_y) && nv.size() > 0) {
                note_line = 0;
            }
            //2번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            else if (rt2.contains(t1_x, t1_y) && nv.size() > 0) {
                note_line = 1;
            }
            //3번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            else if (rt3.contains(t1_x, t1_y) && nv.size() > 0) {
                note_line = 2;
            }
            //이후 해당 라인에 맞는 번호에 있는 노트중 가장 먼저 있는 노트를 찾음
            for (int i = 0; i < nv.size(); i++) {
                if (nv.get(i).getNoteNum() == note_line) {
                    note_i = i;
                    break;
                }
            }
            //그 노트를 판정함
            noteJudge(note_i, note_line);
        }


        //D-Pad에 터치좌표가 있을경우

        if(rt.contains(t2_x, t2_y)) {
            //D-Pad의 버튼을 누른곳으로 움직여줌
            dpad_MiniCircle.setPosition((int)((t2_x-75) / GameActivity.size), (int) ((t2_y-75) / GameActivity.size));
            //각도계산기를 호출
            calcAmgle();
        }

        //손을 땔때 dpad의 값들을 초기화 시켜줌
        if(action == MotionEvent.ACTION_UP) {
            t2_x = 0;
            t2_y = 0;
            dpad_MiniCircle.setPosition(dpad_MiniCircle.x_ori, dpad_MiniCircle.y_ori);
        }


        //몹에 무빙을 위한 판정요소들을 넣어줌
        m.setDis(dx, dy);
        m.setTouch(t2_x, t2_y);
        m.setdpadCircle(dpad_Circle.getX_R(), dpad_Circle.getY_R());

        return true;
    }
}