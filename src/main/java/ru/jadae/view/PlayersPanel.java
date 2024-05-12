package ru.jadae.view;


import ru.jadae.model.Player;
import ru.jadae.view.custom_panels.BorderedPanel;
import ru.jadae.view.utils.AppStyles;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Locale;

public class PlayersPanel extends BorderedPanel {
    private final GamePanel _owner;
    private Player _observable;

    private final JLabel _playerInfoPanel = new JLabel();
    private final JPanel _wordsPanel = new JPanel();

    public PlayersPanel(GamePanel owner) {
        super(3);
        _owner = owner;

        setLayout(new BorderLayout(20, 20));
        setPreferredSize(new Dimension(200, _owner.getHeight()));

        _playerInfoPanel.setFont(AppStyles.LABEL_FONT);
        _playerInfoPanel.setText("");
        _playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        add(_playerInfoPanel, BorderLayout.NORTH);

        _wordsPanel.setBorder(new MatteBorder(1, 0, 0, 0, AppStyles.BORDER_COLOR));
        _wordsPanel.setLayout(new BoxLayout(_wordsPanel, BoxLayout.Y_AXIS));

        add(_wordsPanel, BorderLayout.CENTER);
    }

    public void setObservablePlayer(Player player) {
        _observable = player;
        update();
    }

    public void update() {
//        _playerInfoPanel.setText(
//                _observable.getPlayerName() + ": " + _owner.currentGame().scoreCounter().getScorePlayerByName(_observable.name()));
//
//        _playerInfoPanel.setForeground(
//                _owner.currentGame().activePlayer() == _observable? AppStyles.ACTIVE_PLAYER_COLOR : Color.black);
//
//        _wordsPanel.removeAll();
//        _wordsPanel.revalidate();
//        _wordsPanel.repaint();
//
//        for (String word : _observable.getFormedWords()) {
//            JLabel wordLabel = new JLabel(word.toLowerCase(Locale.ROOT));
//            wordLabel.setFont(AppStyles.TITLE_FONT);
//            _wordsPanel.add(wordLabel);
//        }
    }
}
