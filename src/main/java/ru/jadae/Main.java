package ru.jadae;

import ru.jadae.view.GameFrame;

import javax.swing.*;

//Version 1.0
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}