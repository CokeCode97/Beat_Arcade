package com.example.administrator.framework;

import android.util.Log;

import com.example.administrator.networks.ClientWork;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by LeeSugyun on 2017. 12. 11..
 */

public class ParserThread extends Thread {

    private Vector<HashMap<String, Integer>> noteDataVector;
    private String songName = "";
    private static int noteSum = 0;
    private int notetime_sum = 0;
    public static int getNoteSum() { return noteSum; }

    private HashMap<String, Integer> dataMap;

    private InputStreamReader isReader;

    public ParserThread(Vector<HashMap<String, Integer>> noteDataVector, InputStream isReader) {
        try {
            this.isReader = new InputStreamReader(isReader);
            this.noteDataVector = noteDataVector;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader bfReader = new BufferedReader(isReader);
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlParser = parserFactory.newPullParser();

            xmlParser.setInput(bfReader);
            xmlParser.next();
            int parserEvent = xmlParser.getEventType();

            ArrayList<String> flagList = new ArrayList<>();
            flagList.add("delay"); flagList.add("num");
            String flag = "";

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                Log.d("check", "test");
                switch (parserEvent) {

                    case XmlPullParser.START_TAG : {
                        flag = xmlParser.getName();
                        if (flag.equals("note")) {
                            dataMap = new HashMap<>();
                        }
                        break;
                    }

                    case XmlPullParser.TEXT : {
                        switch (flag) {
                            case "notesum" : {
                                noteSum = Integer.parseInt(xmlParser.getText());
                                flag = "";
                                break;
                            }
                            case "name" : {
                                songName = xmlParser.getText();
                                flag = "";
                                break;
                            }
                            case "num" : {
                                dataMap.put(flag, Integer.parseInt(xmlParser.getText()));
                                flag = "";
                                break;
                            }


                            case "delay" : {
                                dataMap.put(flag, Integer.parseInt(xmlParser.getText()));
                                notetime_sum += Integer.parseInt(xmlParser.getText());
                                flag = "";
                                break;
                            }
                        }
                        break;
                    }

                    case XmlPullParser.END_TAG : {
                        if (xmlParser.getName().equals("note")) {
                            noteDataVector.add(dataMap);
                        }
                        break;
                    }
                }
                parserEvent = xmlParser.next();
            }
            bfReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isDone();
    }

    public void isDone() {
        if (noteSum == noteDataVector.size()) {
            // TODO : 서버로 로딩완료 전송
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ClientWork.write("Ready");
        }
    }
}
