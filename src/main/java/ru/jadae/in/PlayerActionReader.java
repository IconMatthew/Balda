package ru.jadae.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class PlayerActionReader {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String readUserAction() {
        String line;
        try {
            line = reader.readLine().trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    public int[] readHeightAndWidth() {
        int[] params = Arrays.stream(readUserAction().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        if (params.length != 2) {
            throw new IllegalArgumentException("Make sure to enter both: index for height pos and index for width pos");
        }

        return params;
    }

    public Character readLetter() {
        String readStr = readUserAction();
        Character letter = readStr.length() == 1 ? readStr.toCharArray()[0] : ' ';

        if (letter.equals(' ')) {
            throw new IllegalArgumentException("Make sure to enter only one character");
        }

        return letter;
    }

}
