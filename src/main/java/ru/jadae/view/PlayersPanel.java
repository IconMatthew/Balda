package ru.jadae.view;


import ru.jadae.model.Player;
import ru.jadae.view.custom_panels.FormedWordsContainer;
import ru.jadae.view.utils.Styles;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Locale;

public class PlayersPanel extends FormedWordsContainer {
    private final GamePanel owner;
    private Player observable;
    private final JLabel playerInfoPanel = new JLabel();
    private final JPanel wordsPanel = new JPanel();

    public PlayersPanel(GamePanel owner) {
        super(3);
        this.owner = owner;

        setLayout(new BorderLayout(20, 20));
        setPreferredSize(new Dimension(200, this.owner.getHeight()));

        playerInfoPanel.setFont(Styles.LABEL_FONT);
        playerInfoPanel.setText("");
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        add(playerInfoPanel, BorderLayout.NORTH);

        wordsPanel.setBorder(new MatteBorder(1, 0, 0, 0, Styles.BORDER_COLOR));
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));

        add(wordsPanel, BorderLayout.CENTER);
    }

    public void setObservablePlayer(Player player) {
        observable = player;
        update();
    }

    public void update() {

        String text = observable.getPlayerName() + ": " + observable.getFormedWords()
                .stream()
                .mapToInt(String::length)
                .sum();
        playerInfoPanel.setText(text);

        playerInfoPanel.setForeground(
                owner.getGame().getActivePlayer() == observable ? Styles.ACTIVE_PLAYER_COLOR : Color.black);

        wordsPanel.removeAll();
        wordsPanel.revalidate();
        wordsPanel.repaint();

        for (String word : observable.getFormedWords()) {
            JLabel wordLabel = new JLabel(word.toLowerCase(Locale.ROOT));
            wordLabel.setFont(Styles.TITLE_FONT);
            wordsPanel.add(wordLabel);
        }
    }
}
