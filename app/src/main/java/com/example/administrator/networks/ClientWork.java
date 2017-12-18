package com.example.administrator.networks;

import com.example.administrator.state.GameState;
import com.example.administrator.framework.PacketManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by yylwd on 2017-12-03.
 */

public class ClientWork extends Thread {
    private boolean flag = true;

    private Socket socket;

    private BufferedReader reader;
    private static BufferedWriter writer;

    private String readData = "";
    public String getReadData() {
        return readData;
    }

    private Vector<Player> player_Vector = new Vector<>();

    private GameState gameState;

    class Player {
        int x;
        int y;
    }

    public ClientWork(String serverIP, int gamePort) {
        try {
            this.socket = new Socket(serverIP, gamePort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        player_Vector.add(new Player());
        player_Vector.add(new Player());
        //this.start();
        //TODO GameState생성자에서 시작
    }

    @Override
    public void run() {
        try {
            // TODO 클라작업
            while (flag) {
                try {
                    readData = reader.readLine();
                    PacketManager.check_Message(readData);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String writeData) {
        try {
            writer.write(writeData + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGameState(GameState gs) {
        this.gameState = gs;

    }
}