package ru.jadae.view;

import ru.jadae.model.Game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private final SettingsMenuPanel menu;

    void setSettingsSizeWindow() {
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    void setGameSizeWindow() {
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    public GameFrame() {
        setTitle("Балда");
        setSettingsSizeWindow();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        menu = new SettingsMenuPanel(this);
        add(menu);

        setVisible(true);
    }

    public void runGame(Game game) {

        gamePanel = new GamePanel(this);
        setGameSizeWindow();

        gamePanel.setGameModel(game);
        add(gamePanel);

        gamePanel.setVisible(true);
    }

    public void toStartMenu() {
        gamePanel.setVisible(false);
        setSettingsSizeWindow();

        menu.setVisible(true);
    }
}
