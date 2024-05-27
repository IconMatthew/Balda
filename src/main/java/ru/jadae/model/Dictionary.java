package ru.jadae.model;

import ru.jadae.enums.Languages;
import ru.jadae.exceptions.InitException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private final String filePath;
    private List<String> words;
    private List<String> formedWords;
    private final Alphabet alphabet;

    public Dictionary(String filePath, Languages language) {
        this.filePath = filePath;
        this.alphabet = new Alphabet(language);
        loadWordsFromSource();
    }

    public boolean wasFormedBefore(String word) {
        if (formedWords == null) return false;
        return formedWords.contains(word);
    }

    public void addFormedWord(String word) {
        if (formedWords == null) {
            formedWords = new ArrayList<>();
        }

        formedWords.add(word);
    }

    public void loadWordsFromSource() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            words = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String findValidWordForLength(int length) {
        for (String word : words) {
            if (word.length() == length) return word;
        }
        throw new InitException("Invalid field length: no word in dictionary for length = " + length + ". Try recreate field");
    }

    public boolean letterIsPartOfAlphabet(Character letter) {
        return alphabet.containsLetter(letter);
    }

    public boolean containsWord(String word) {
        return words.contains(word);
    }

    public void addWordToDictionary(String word) {
        words.add(word);
    }

    public boolean isCorrectWord(String word) {
        for (char letter : word.toCharArray()) {
            if (!alphabet.containsLetter(letter)) return false;
        }
        return true;
    }

    public void cleanFormedWords(){
        this.formedWords = new ArrayList<>();
    }
}
