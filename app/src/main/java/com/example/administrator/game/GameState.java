package com.example.administrator.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.administrator.framework.AppManager;
import com.example.administrator.framework.GameActivity;
import com.example.administrator.framework.IState;
import com.example.administrator.framework.R;
import com.example.administrator.networks.ClientWork;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by Administrator on 2017-11-28.
 */

public class GameState extends Thread implements IState {
    Random random = new Random();
    GameActivity gameActivity;
    ClientWork clientWork;


    public static Vector<Player> player_Vector = new Vector<>();

    //게임 배경을 그려주는 객체들
    RhythmBackGourndTop rhythmBackGourndTop = new RhythmBackGourndTop();
    RhythmBackGroundBottom1 rhythmBackGroundBottom1 = new RhythmBackGroundBottom1();
    RhythmBackGroundBottom2 rhythmBackGroundBottom2 = new RhythmBackGroundBottom2();
    RhythmBackGroundBottom3 rhythmBackGroundBottom3 = new RhythmBackGroundBottom3();
    ShootingBackground shootingBackground = new ShootingBackground();

    //Dpad를 구성하는 원들
    private Circle dpad_Circle = new Circle();
    private MiniCircle dpad_MiniCircle = new MiniCircle();

    //리듬콤보글자를 출력하는 객체
    public static RhythmCombo rhythmCombo;

    //비트맵 데이터
    public static Bitmap[] judge_Bitmap = new Bitmap[5];
    public static Bitmap note_Bitmap;
    public static Bitmap combo_Bitmap;
    public static Bitmap comboNumber_Bitmap;
    public static Bitmap laser_Bitmap;
    public static Bitmap bullet_Bitmap;


    private int t2_x = 0, t2_y = 0;     //D-Pad좌표
    private double angle;               //각도
    private double dx, dy;              //D-Pad의 중심과 사용자가 누른곳 사이의 거리


    //플레이어번호
    private static int player_Num = -1;

    public static int getPlayer_Num() {
        return player_Num;
    }


    //적플레이어 번호
    private static int enemy_Num = -1;

    public static int getEnemy_Num() {
        return enemy_Num;
    }


    //업데이트할지 안할지를 체크하는 변수
    private boolean update_Check = true;


    @Override
    public void run() {
        int timeCheck_XY = 0;
        while (true) {
            if (update_Check) {
                this.Update();

                clientWork.write("PlayerData " + player_Vector.get(player_Num).getMove_Check() + " " + player_Vector.get(player_Num).getAngle());
                if (timeCheck_XY > 200) {
                    clientWork.write("PlayerDataXY " + player_Vector.get(player_Num).getX() + " " + player_Vector.get(player_Num).getY());

                    timeCheck_XY = 0;
                }
                timeCheck_XY++;
                update_Check = false;
            }
        }
    }

    public void setCheck(boolean update_Check) {
        this.update_Check = update_Check;
    }

    public GameState(Context context) {
        //게임액티비티를 저장
        gameActivity = (GameActivity) context;

        //클라이언트 워크를 생성하고 저장
        clientWork = gameActivity.getClient();
        clientWork.setGameState(this);

        //게임액티비티에 있는 값으로 플레이어 넘버를 정해줌
        player_Num = gameActivity.getMyPlayerNum();

        //나의 플레이어 넘버에 따라 적의 플레이어 넘버도 정해줌
        if (player_Num == 0) {
            enemy_Num = 1;
        } else {
            enemy_Num = 0;
        }

        //각각의 비트맵에 비트맵 데이터를 넣어줌
        judge_Bitmap[0] = AppManager.getInstance().getBitmap(R.drawable.miss);
        judge_Bitmap[1] = AppManager.getInstance().getBitmap(R.drawable.poor);
        judge_Bitmap[2] = AppManager.getInstance().getBitmap(R.drawable.bad);
        judge_Bitmap[3] = AppManager.getInstance().getBitmap(R.drawable.good);
        judge_Bitmap[4] = AppManager.getInstance().getBitmap(R.drawable.perfect);
        note_Bitmap = AppManager.getInstance().getBitmap(R.drawable.note);
        combo_Bitmap = AppManager.getInstance().getBitmap(R.drawable.combo);
        comboNumber_Bitmap = AppManager.getInstance().getBitmap(R.drawable.number_sprite);
        laser_Bitmap = AppManager.getInstance().getBitmap(R.drawable.laser);
        bullet_Bitmap = AppManager.getInstance().getBitmap(R.drawable.bullet);

        //리듬콤보를 관리 출력할 리듬콤보객체를 생성
        rhythmCombo = new RhythmCombo(combo_Bitmap);

        //플레이어 객체를 두개 생성함
        player_Vector.add(new Player());
        player_Vector.add(new Player());

        //체력바 (배경)을 생성함
        ObjectManager.hp_Black_Vector.add(new HP_Black(0));
        ObjectManager.hp_Black_Vector.add(new HP_Black(700));

        //체력바 (체력표시)를 생성함
        ObjectManager.hp_Red_Vector.add(new HP_Red(0));
        ObjectManager.hp_Red_Vector.add(new HP_Red(700));

        //클라이언트 워크쓰레드를 시작
        clientWork.start();
    }


    //시작될때 여러 비트맵 데이터나 객체들을 메모리에 올림
    @Override
    public void Init() {

    }

    @Override
    public void Destroy() {

    }


    //TODO 매 프레임마다 할일들
    @Override
    public void Update() {
        //GameTime에 현재 시간을 넣어줌
        long GameTime = System.currentTimeMillis();

        //note_Vector 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < ObjectManager.note_Vector.size(); i++) {
            ObjectManager.note_Vector.get(i).Update(GameTime);
        }

        //judge_Vector 안에 있는 모든 객체의 Update를 호출
        for (int i = 0; i < ObjectManager.judge_Vector.size(); i++) {
            ObjectManager.judge_Vector.get(i).Update(GameTime);
        }

        //플레이어와 레이저를 업데이트함
        for (int i = 0; i < player_Vector.size(); i++) {
            player_Vector.get(i).Update(GameTime);
            for (int j = 0; j < player_Vector.get(i).laser_Vector.size(); j++) {
                player_Vector.get(i).laser_Vector.get(j).Update(GameTime);
            }
        }

        //모든 Bullet 객체의 Update를 호출
        for (int i = 0; i < ObjectManager.bullet_Vector.size(); i++) {
            ObjectManager.bullet_Vector.get(i).Update(GameTime);
        }

        //노트를 생성
        ObjectManager.MakeNote();
    }


    //TODO 객체를 렌더링
    @Override
    public void Render(Canvas canvas) {
        //슈팅게임의 배경을 그려줌
        shootingBackground.Draw(canvas);

        //플레이어와 레이저를 그림
        for (int i = 0; i < player_Vector.size(); i++) {
            player_Vector.get(i).Draw(canvas);
            for (int j = 0; j < player_Vector.get(i).laser_Vector.size(); j++) {
                player_Vector.get(i).laser_Vector.get(j).Draw(canvas);
            }
        }

        //모든 Bullet 객체를 드로우
        for (int i = 0; i < ObjectManager.bullet_Vector.size(); i++) {
            ObjectManager.bullet_Vector.get(i).Draw(canvas);
        }

        //노트가 내려오는 부분의 배경을 그려줌
        rhythmBackGourndTop.Draw(canvas);

        //nv벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < ObjectManager.note_Vector.size(); i++) {
            ObjectManager.note_Vector.get(i).Draw(canvas);
        }

        //jv벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < ObjectManager.judge_Vector.size(); i++) {
            ObjectManager.judge_Vector.get(i).Draw(canvas);
        }

        //콤보가 2보다 크면 콤보와 관련된 오브젝트들을 렌더링함
        if (rhythmCombo.getCombo() > 2) {
            rhythmCombo.Draw(canvas);
            for (int i = 0; i < ObjectManager.comboNumber_Vector.size(); i++) {
                ObjectManager.comboNumber_Vector.get(i).Draw(canvas);
            }
        }
        //hp벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < ObjectManager.hp_Black_Vector.size(); i++) {
            ObjectManager.hp_Black_Vector.get(i).Draw(canvas);
        }

        //hp벡터 안에 있는 모든 객체를 그려줌
        for (int i = 0; i < ObjectManager.hp_Red_Vector.size(); i++) {
            ObjectManager.hp_Red_Vector.get(i).Draw(canvas);
        }

        //버튼들을 그려줌
        rhythmBackGroundBottom1.Draw(canvas);
        rhythmBackGroundBottom2.Draw(canvas);
        rhythmBackGroundBottom3.Draw(canvas);

        //D-Pad를 그려줌
        dpad_Circle.Draw(canvas);
        dpad_MiniCircle.Draw(canvas);
    }


    @Override
    public boolean onKeyDwon(int Keycode, KeyEvent event) {
        return false;
    }


    //TODO 터치입력에 대한 반응
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();                      //터치액션을 담음
        int t1_x = 0, t1_y = 0;                              //리듬게임 부분의 터치를 저장
        int note_i = 0;                                      //노트의 인덱스
        int note_line = 0;                                   //노트의 라인
        int line = (int) (500 * GameActivity.size);          //리듬게임과 슈팅게임의 경계선

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
        if (event.getPointerCount() > 1) {
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
        if (t1_x != 0 && t1_y != 0 && ObjectManager.note_Vector.size() > 0) {
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
            for (int i = 0; i < ObjectManager.note_Vector.size(); i++) {
                if (ObjectManager.note_Vector.get(i).getNoteNum() == note_line) {
                    note_i = i;
                    break;
                }
            }
            //그 노트를 판정함
            ObjectManager.note_Vector.get(note_i).noteJudge(note_i, note_line);
        }


        //D-Pad에 터치좌표가 있을경우
        if (rt.contains(t2_x, t2_y)) {
            //D-Pad의 버튼을 누른곳으로 움직여줌
            dpad_MiniCircle.setPosition((int) ((t2_x - 75) / GameActivity.size), (int) ((t2_y - 75) / GameActivity.size));
            //각도계산기를 호출
            calcAngle();
            //상태를 무브로 바꿈
            player_Vector.get(player_Num).setMove_Check(true);
        }

        //손을 땔때 dpad의 값들을 초기화 시켜줌
        if (action == MotionEvent.ACTION_UP) {
            t2_x = 0;
            t2_y = 0;
            dpad_MiniCircle.setPosition(dpad_MiniCircle.x_ori, dpad_MiniCircle.y_ori);
            player_Vector.get(player_Num).setMove_Check(false);
        }

        //몹에 무빙을 위한 판정요소들을 넣어줌
        player_Vector.get(player_Num).setDis(dx, dy);
        player_Vector.get(player_Num).setTouch(t2_x, t2_y);
        player_Vector.get(player_Num).setDpadCircle(dpad_Circle.getX_R(), dpad_Circle.getY_R());

        return true;
    }


    //D-Pad에서 사용자가 누르고 있는 곳과 D-Pad의 중심의 각을 구하는 메소드
    public void calcAngle() {
        dx = t2_x - (dpad_Circle.getX_R() + 200 * GameActivity.size);
        dy = t2_y - (dpad_Circle.getY_R() + 200 * GameActivity.size);

        angle = 90 + Math.toDegrees(Math.atan2(dy, dx));
        player_Vector.get(player_Num).setAngle(angle);
    }


    // TODO 클라이언트에서 수신한 메세지를 분석하고 그에맞는 일을 처리함
    public void check_Message(String string) {
        //데이터를 분석하기 위해 스트링 토크나이저를 생성
        StringTokenizer stringTokenizer = new StringTokenizer(string, " ");

        //패킷데이터가 없을때 까지 와일문으로 분석
        while (stringTokenizer.hasMoreTokens()) {
            //이 패킷이 어떤 패킷인지 알아내기 위해 태그를 때어냄
            String tag = stringTokenizer.nextToken();

            //태그에 따라 다른 작동을 함
            switch (tag) {
                //플레이어의 위치정보를 받아와 그에 맞게 위치를 조정
                case "PlayerData": {
                    int player_Num = Integer.parseInt(stringTokenizer.nextToken());

                    //자신의 캐릭터의 데이터가 아닐경우에만 해당 번호의 캐릭터를 움직임
                    if (this.getPlayer_Num() != player_Num) {
                        player_Vector.get(player_Num).setMove_Check(Boolean.parseBoolean(stringTokenizer.nextToken()));
                        player_Vector.get(player_Num).setAngle(Double.parseDouble(stringTokenizer.nextToken()));
                    } else {
                        stringTokenizer.nextToken();
                        stringTokenizer.nextToken();
                    }

                    break;
                }

                //서버에서 받아온 값으로 캐릭터의 좌표를 동기화 시킴
                case "PlayerDataXY": {
                    //플레이어들을 받아온 좌표값으로 이동시킴
                    int player_Num = Integer.parseInt(stringTokenizer.nextToken());
                    player_Vector.get(player_Num).x = Integer.parseInt(stringTokenizer.nextToken());
                    player_Vector.get(player_Num).y = Integer.parseInt(stringTokenizer.nextToken());
                    this.player_Vector.get(player_Num).setPosition(player_Vector.get(player_Num).x, player_Vector.get(player_Num).y);

                    break;
                }

                //서버에서 주기적으로 보내는 공공의적(총알)생성메세지
                case "BulletMake": {
                    double angle = Double.parseDouble(stringTokenizer.nextToken());
                    int x = Integer.parseInt(stringTokenizer.nextToken());
                    int y = Integer.parseInt(stringTokenizer.nextToken());

                    ObjectManager.makeBullet(angle, x, y);
                    break;
                }

                //공격정보를 수신하여 그에 맞게 공격명령
                case "Attack": {
                    int player_Num = Integer.parseInt(stringTokenizer.nextToken());
                    int combo = Integer.parseInt(stringTokenizer.nextToken());
                    player_Vector.get(player_Num).make_Laser(laser_Bitmap, System.currentTimeMillis(), combo);

                    break;
                }

                //충돌 판정
                case "Collsion": {
                    int collider_Player = Integer.parseInt(stringTokenizer.nextToken());
                    String collider_Object = stringTokenizer.nextToken();

                    switch (collider_Object) {
                        //레이저와 충돌했을때
                        case "Laser": {
                            int combo = Integer.parseInt(stringTokenizer.nextToken());
                            //플레이어에게 콤보에 따른 데미지를 줌
                            player_Vector.get(collider_Player).hit(combo);

                            break;
                        }
                        case "Bullet": {
                            int collider_Index = Integer.parseInt(stringTokenizer.nextToken());
                            //플레이어에게 5의 데미지를 주고
                            player_Vector.get(collider_Player).hit(5);
                            //50%의 이동속도 감소효과 디버프를 검
                            player_Vector.get(collider_Player).setSlow(0.5f, System.currentTimeMillis());
                            //만약 불릿벡터 사이즈가 콜리더인덱스보다 크면 콜리더 인덱스의 객체를 파괴함
                            if (ObjectManager.bullet_Vector.size() > collider_Index)
                                ObjectManager.bullet_Vector.remove(collider_Index);

                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}