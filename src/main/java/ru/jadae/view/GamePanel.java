package ru.jadae.view;


import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.jadae.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
public class GamePanel extends JPanel {
    private final GameFrame owner;
    private final FieldPanel fieldView;
    private final ArrayList<PlayersPanel> playersPanels = new ArrayList<>();
    private final ControlsPanel controlsPanel;
    private final CellSubSequencePanel cellSubSequencePanel;

    private Game game;

    public GamePanel(@NotNull GameFrame gameFrame) {
        owner = gameFrame;

        setLayout(new BorderLayout(30, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        fieldView = new FieldPanel(this);
        cellSubSequencePanel = new CellSubSequencePanel(this);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));

        centerPanel.add(fieldView, BorderLayout.CENTER);
        centerPanel.add(cellSubSequencePanel, BorderLayout.NORTH);

        add(centerPanel, BorderLayout.CENTER);

        var panelPlayer1 = new PlayersPanel(this);
        playersPanels.add(panelPlayer1);
        add(panelPlayer1, BorderLayout.WEST);

        var panelPlayer2 = new PlayersPanel(this);
        playersPanels.add(panelPlayer2);
        add(panelPlayer2, BorderLayout.EAST);

        controlsPanel = new ControlsPanel(this);
        add(controlsPanel, BorderLayout.SOUTH);

        addKeyListener(new KeyController());
        setVisible(true);
        setFocusable(true);
        requestFocus();
    }

    public void setGameModel(@NotNull Game game) {
        this.game = game;
        fieldView.initField();
        playersPanels.get(0).setObservablePlayer(this.game.getPlayers().get(0));
        playersPanels.get(1).setObservablePlayer(this.game.getPlayers().get(1));
    }

    public void suggestAddingWordToDictionary() {
        int option = JOptionPane.showConfirmDialog(null, "Хотите добавить слово в словарь?",
                "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String word = this.game.getActivePlayer().getWordFormer().getQueueOfCells().stream()
                    .map(cell -> String.valueOf(cell.getCellValue()))
                    .collect(Collectors.joining());

            this.game.additionalStep3AddWordToDictionary(word);
            this.game.step4FinishMove();
        }
    }

    public void update() {

        fieldView.update();
        cellSubSequencePanel.update();

        if (this.game.isGameOver()) {
            JOptionPane.showMessageDialog(owner, this.game.getGameResultMessage());
            owner.toStartMenu();
        }

        playersPanels.forEach(PlayersPanel::update);
    }

    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                game.additionalStep1CancelMove();
                update();
                return;
            }

            boolean isTimeSetSymbol = game.getActivePlayer().isFirstStepIsDone();
            isTimeSetSymbol &= !game.getActivePlayer().isSecondStepIsDone();
            try {
                if (isTimeSetSymbol) {
                    Character lowerCaseCharacter = String.valueOf(e.getKeyChar()).toLowerCase().charAt(0);
                    game.step2InsertLetter(lowerCaseCharacter);
                    update();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(owner, exception.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
