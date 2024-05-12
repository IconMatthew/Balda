package ru.jadae.view;



import ru.jadae.model.Game;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame{
    private GamePanel _gamePanel;
    private final SettingsMenuPanel _menu;  // Окно настроек игры

    void setSettingsSizeWindow() {
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    void setGameSizeWindow() {
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    // Создается при запуске приложения
    public GameFrame() {
        setTitle("Балда");
        setSettingsSizeWindow();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        _menu = new SettingsMenuPanel(this);
        add(_menu);

        setVisible(true);
    }

    // Запуск процесса игры
    public void runGame(Game model) {
        _gamePanel = new GamePanel(this);
        setGameSizeWindow();

        model.gameCycle();
        _gamePanel.setGameModel(model);
        add(_gamePanel);

        _gamePanel.setVisible(true);
    }

    // После окончания игры, снова открывается меню
    public void toStartMenu() {
        _gamePanel.setVisible(false);
        setSettingsSizeWindow();

        _menu.setVisible(true);
    }
}
