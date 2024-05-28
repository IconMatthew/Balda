package ru.jadae.view;

import ru.jadae.complayer.ComputerPlayer;
import ru.jadae.exceptions.DuplicateWord;
import ru.jadae.view.custom_panels.ActionButton;

import javax.swing.*;
import java.awt.*;

public class ControlsPanel extends JPanel {
    GamePanel owner;
    public final static Dimension BUTTON_ACTION_SIZE = new Dimension(240, 35);

    public ControlsPanel(GamePanel owner) {
        this.owner = owner;
        setPreferredSize(new Dimension(this.owner.getWidth(), 50));

        setLayout(new FlowLayout(FlowLayout.CENTER, 45, 5));
        JButton _skipButton = new ActionButton("Пропуск хода");
        _skipButton.setPreferredSize(BUTTON_ACTION_SIZE);
        _skipButton.addActionListener(e -> skipTurnCurrentPlayer());
        add(_skipButton);

        JButton _cancelButton = new ActionButton("Отмена");
        _cancelButton.setPreferredSize(BUTTON_ACTION_SIZE);
        _cancelButton.addActionListener(e -> resetActionsCurrentPlayer());
        add(_cancelButton);

        JButton _confirmButton = new ActionButton("Подтвердить ход");
        _confirmButton.setPreferredSize(BUTTON_ACTION_SIZE);
        _confirmButton.addActionListener(e -> confirmCurrentSubSequence());
        add(_confirmButton);
    }

    private void skipTurnCurrentPlayer() {
        owner.getGame().additionalStep2SkipMove();
        owner.update();
    }

    private void confirmCurrentSubSequence() {
        try{
            if (!owner.getGame().step4FinishMove()) {
                owner.suggestAddingWordToDictionary();
            }

            if (owner.getGame().getActivePlayer() instanceof ComputerPlayer){
                owner.update();
                owner.getGame().step4FinishMove();
            }
        } catch (DuplicateWord e){
            System.out.println(e.getMessage());
        } finally {
            owner.update();
        }

    }

    private void resetActionsCurrentPlayer() {
        owner.getGame().additionalStep1CancelMove();
        owner.update();
    }
}
