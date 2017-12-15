package com.example.administrator.networks;

import android.util.Log;

import com.example.administrator.game.BulletPatternManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by LeeSugyun on 2017-12-03.
 */

public class ServerWork extends Thread {
    Random random = new Random();
    private boolean flag = true;

    private static String myName;
    private String myIP;
    private static String opponentName;
    private String opponentIP;
    private int gamePort;

    private ServerSocket serverSocket;

    private boolean[] playerReady_Check = new boolean[2];
    private boolean allready_Check = false;
    private boolean start_Check = false;

    private static HashMap<String, ServerThread> serverMap;
    private Vector<Player> player_Vector = new Vector<>();
    class Player {
        int x;
        int y;
        double angle;
        boolean move_Check;
    }



    public ServerWork(String myName, String myIP, String opponentName, String opponentIP, int gamePort) {
        this.myName = myName;
        this.myIP = myIP;
        this.opponentName = opponentName;
        this.opponentIP = opponentIP;
        this.gamePort = gamePort;

        serverMap = new HashMap<>();

        player_Vector.add(new Player());
        player_Vector.add(new Player());

        try {
            serverSocket = new ServerSocket(gamePort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < playerReady_Check.length; i++) {
            playerReady_Check[i] = false;
        }

        this.start();
    }

    @Override
    public void run() {
        int timeCheck_bullet = 0;
        try {
            while (!serverMap.containsKey(myName) || !serverMap.containsKey(opponentName)) {
                Socket socket = serverSocket.accept();
                if (socket.getInetAddress().getHostAddress().equals(myIP)) {
                    serverMap.put(myName, new ServerThread(socket, 0));
                }
                if (socket.getInetAddress().getHostAddress().equals(opponentIP)) {
                    serverMap.put(opponentName, new ServerThread(socket, 1));
                }
            }

            serverSocket.close();

            // TODO 서버작업
            while (flag) {
                if(playerReady_Check[0]&& playerReady_Check[1]) {
                    if(!start_Check) {
                        sleep(500);
                        start_Check = true;
                    }
                    String sendData = "PlayerData " + 0 + " " + player_Vector.get(0).move_Check + " " + player_Vector.get(0).angle + " " + "PlayerData " + 1 + " " + player_Vector.get(1).move_Check + " " + player_Vector.get(1).angle;
                    all_Write(sendData);

                    if (timeCheck_bullet > 300) {
                        int angle = random.nextInt(360);
                        BulletPatternManager.makeBarrage();
                        timeCheck_bullet = 0;
                    }
                    timeCheck_bullet++;

                    this.sleep(15);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //연결된 클라이언트 전체에 메세지를 발송
    public static void all_Write(String sendData) {
        serverMap.get(myName).write(sendData + "\n");
        serverMap.get(opponentName).write(sendData + "\n");
    }


    //각각의 클라이언트와 통신을 하는 쓰레드
    final class ServerThread extends Thread {
        private boolean flag = true;

        private Socket socket;

        private String readData = "PREPARE";

        private BufferedWriter writer;
        private BufferedReader reader;

        private int player_Num = 0;

        public int getPlayer_Num() {
            return player_Num;
        }

        public ServerThread(Socket socket, int player_Num) {
            this.socket = socket;
            this.player_Num = player_Num;

            try {
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.start();
        }


        //쓰레드가 지속적으로 돌면서 데이터를 받아 readData에 저장함
        @Override
        public void run() {
            try {
                while (flag) {
                    readData = reader.readLine();
                    check_Message(readData);
                }
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //연결되어 있는 클라이언트로 메세지를 보냄
        //writeData = 보낼 메세지
        public void write(String writeData) {
            try {
                writer.write(writeData);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO 서버로 오는 메세지를 가져와 분석하고 그에 맞는 일을 처리함
        public void check_Message(String string) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
            String tag = stringTokenizer.nextToken();
            switch (tag) {
                //플레이어가 준비됨
                case "Ready" : {
                    playerReady_Check[player_Num] = true;
                    if (playerReady_Check[0] && playerReady_Check[1] && !allready_Check ) {
                        all_Write("AllReady");
                        allready_Check = true;
                    }

                    break;
                }
                //클라이언트로 부터 지속적으로 받아오는 캐릭터의 무브체크와 무브각도
                case "PlayerData" : {
                    player_Vector.get(player_Num).move_Check = Boolean.parseBoolean(stringTokenizer.nextToken());
                    player_Vector.get(player_Num).angle = Double.parseDouble(stringTokenizer.nextToken());
                    break;
                }

                //클라이언트에서 주기적으로 받아오는 캐릭터의 좌표 이것을 통해 캐릭터의 좌표를 일정주기마다 동기화 시킴
                case "PlayerDataXY" : {
                    player_Vector.get(player_Num).x = Integer.parseInt(stringTokenizer.nextToken());
                    player_Vector.get(player_Num).y = Integer.parseInt(stringTokenizer.nextToken());

                    String sendData = "PlayerDataXY " + player_Num + " " + player_Vector.get(player_Num).x + " " + player_Vector.get(player_Num).y;

                    all_Write(sendData);
                    break;
                }

                //노트를 맞출때 호출되며 공격명령을 전달
                case "Attack" : {
                    Double damage = Double.parseDouble(stringTokenizer.nextToken());
                    all_Write("Attack " + player_Num + " " + damage);
                    break;
                }

                //충돌 판정
                case "Collision" : {
                    int collider_Player = Integer.parseInt(stringTokenizer.nextToken());
                    String collider_Object = stringTokenizer.nextToken();
                    switch (collider_Object) {
                        case "Laser" : {
                            Double damage = Double.parseDouble(stringTokenizer.nextToken());
                            all_Write("Collsion " + collider_Player + " " + collider_Object + " " + damage);
                            break;
                        }
                        case "Bullet" : {
                            int collider_Index = Integer.parseInt(stringTokenizer.nextToken());
                            all_Write("Collsion " + collider_Player + " " + collider_Object + " " + collider_Index);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
