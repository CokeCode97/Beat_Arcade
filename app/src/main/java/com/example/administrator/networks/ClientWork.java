package com.example.administrator.networks;

import com.example.administrator.game.GameState;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by yylwd on 2017-12-03.
 */

public class ClientWork extends Thread {
    private boolean flag = true;

    private Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    private String readData = "";
    public String getReadData() {
        return readData;
    }


    private GameState gameState;

    public ClientWork(String serverIP, int gamePort) {
        try {
            this.socket = new Socket(serverIP, gamePort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO GameState생성자에서 시작
    }

    @Override
    public void run() {
        try {
            // TODO 클라작업
            while (flag) {
                try {
                    readData = reader.readLine();
                    gameState.check_Message(readData);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String writeData) {
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

//PlayerData 0 100 120 PlayerData 1 1003 120 Bullet 0 20 400 Bullet 1 30 432