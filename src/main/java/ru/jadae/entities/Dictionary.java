package ru.jadae.entities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private final String filePath;
    private List<String> words;

    public Dictionary(String filePath) {
        this.filePath = filePath;
    }

    public String getWordsFromSource(){

//        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
//            words = new ArrayList<>(bufferedReader.lines())''
//            while (bufferedReader.ready()){
//                bufferedReader.readLine();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }



        return null;
    }
}
