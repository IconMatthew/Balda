package ru.jadae;

import ru.jadae.builder.GameBuilder;
import ru.jadae.view.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        GameBuilder gameBuilder = new GameBuilder();
//        gameBuilder.getGame().gameCycle();
        SwingUtilities.invokeLater(GameFrame::new);

    }
}