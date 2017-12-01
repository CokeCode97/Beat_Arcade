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

    RhythmCombo rcb = new RhythmCombo();
    Mob m = new Mob();

    //시간을 측정할 기준 Last
    long Last = System.currentTimeMillis();


    int combo = 0;                                         //콤보

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
    public void noteJudge(int note_num) {
        int judge_point = 0; //판정을 담당할 숫자

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

            //최종적으로 합산된 판정값을 가지고 판정출력객체를 생성
            makeJudge(judge_point);
            //콤보매니저 호출
            comboManager();

            //이후 노트를 삭제
            nv.remove(note_num);
            return;
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

    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }

    //터치입력에 대한 반응

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();                      //터치액션을 담음
        int t1_x = 0, t1_y = 0, t2_x = 0, t2_y = 0;;
        int note_i = 0;                                      //노트의 인덱스
        int line = (int)(500 * GameActivity.size);           //리듬게임과 슈팅게임의 경계선

        t2_x = m.getX_R();
        t2_y = m.getY_R();

        //터치받은 액션으로 부터 좌표를 받아옴
        for (int i = 0; i < event.getPointerCount(); i++) {
            Integer a = event.getPointerCount();
            Log.d("test", a.toString());
            if (event.getX(i) < line) {
                t1_x = (int) event.getX(i);
                t1_y = (int) event.getY(i);
            } else {
                t2_x = (int) event.getX(i);
                t2_y = (int) event.getY(i);
            }
        }
        m.setPosition((int) (t2_x / GameActivity.size), (int) (t2_y / GameActivity.size));

        //터치가능 범위를 지정해줄 렉트
        Rect rt = new Rect();

        if(t1_x != 0 && t1_y != 0) {
            //렉트에 1번 버튼을 넣어줌
            rt.set(rbgb1.getX_R(), rbgb1.getY_R(), rbgb1.getX_R() + rbgb1.getSpriteWidth(), rbgb1.getY_R() + rbgb1.getSpriteHeight());
            //1번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            if (rt.contains(t1_x, t1_y) && nv.size() > 0) {
                for (int i = 0; i < nv.size(); i++) {
                    if (nv.get(i).getNoteNum() == 0) {
                        note_i = i;
                        break;
                    }
                }
                noteJudge(note_i);
            }

            //렉트에 2번 버튼을 넣어줌
            rt.set(rbgb2.getX_R(), rbgb2.getY_R(), rbgb2.getX_R() + rbgb2.getSpriteWidth(), rbgb2.getY_R() + rbgb2.getSpriteHeight());
            //2번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            if (rt.contains(t1_x, t1_y) && nv.size() > 0) {
                for (int i = 0; i < nv.size(); i++) {
                    if (nv.get(i).getNoteNum() == 1) {
                        note_i = i;
                        break;
                    }
                }
                noteJudge(note_i);
            }

            //렉트에 3번 버튼을 넣어줌
            rt.set(rbgb3.getX_R(), rbgb3.getY_R(), rbgb3.getX_R() + rbgb3.getSpriteWidth(), rbgb3.getY_R() + rbgb3.getSpriteHeight());
            //3번버튼이 눌려졌다면 nv벡터에서 노트넘버가 0인것을 찾아 그 인덱스를 note_i에 저장
            if (rt.contains(t1_x, t1_y) && nv.size() > 0) {
                for (int i = 0; i < nv.size(); i++) {
                    if (nv.get(i).getNoteNum() == 2) {
                        note_i = i;
                        break;
                    }
                }
                noteJudge(note_i);
            }
        }
        return false;
    }
}
