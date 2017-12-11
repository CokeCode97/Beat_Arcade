package com.example.administrator.networks;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by LeeSugyun on 2017-12-03.
 */

public class ServerWork extends Thread {
    private boolean flag = true;

    private String myName;
    private String myIP;
    private String opponentName;
    private String opponentIP;
    private int gamePort;

    private ServerSocket serverSocket;



    private HashMap<String, ServerThread> serverMap;
    private Vector<Player> player_Vector = new Vector<>();
    class Player {
        int x;
        int y;
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

        this.start();
    }

    @Override
    public void run() {
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

                String sendData = "PlayerData " + 0 + " " + player_Vector.get(0).x + " " + player_Vector.get(0).y
                        + " " + "PlayerData " + 1 + " " + player_Vector.get(1).x + " " + player_Vector.get(1).y;

                serverMap.get(myName).write(sendData + "\n");
                serverMap.get(opponentName).write(sendData + "\n");


                this.sleep(15);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



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

        public void write(String writeData) {
            try {
                writer.write(writeData);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO
        public void check_Message(String string) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
            String tag = stringTokenizer.nextToken();
            switch (tag) {
                case "PlayerData" : {
                    player_Vector.get(player_Num).x = Integer.parseInt(stringTokenizer.nextToken());
                    player_Vector.get(player_Num).y = Integer.parseInt(stringTokenizer.nextToken());
                    break;
                }
                case "Attack" : {
                    write("Attack " + player_Num);
                    break;
                }
            }
        }
    }
}
