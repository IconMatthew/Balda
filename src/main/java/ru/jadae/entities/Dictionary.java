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

    public boolean wasFormedBefore(String word){
        return words.contains(word);
    }

    public void getWordsFromSource(){

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            words = new ArrayList<>();
            while (bufferedReader.ready()){
                words.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String findValidWordForLength(int length){
        for (String word:words) {
            if (word.length() == length) return word;
        }
        return null;
    }

}
