package ru.jadae.view;

import ru.jadae.view.custom_panels.BorderedPanel;
import ru.jadae.view.GamePanel;
import ru.jadae.view.utils.AppStyles;

import javax.swing.*;
import java.awt.*;

public class CellSequencePanel extends BorderedPanel {
    private final GamePanel _owner;
    private final JLabel _cellSequenceView = new JLabel();

    public CellSequencePanel(GamePanel owner) {
        super(3);
        _owner = owner;

        setBackground(AppStyles.SECONDARY_COLOR);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(_owner.getWidth(), 40));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(1,1,1,1);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        _cellSequenceView.setFont(AppStyles.TITLE_FONT);
        _cellSequenceView.setForeground(Color.BLUE);
        _cellSequenceView.setText("");
        add(_cellSequenceView, constraints);

        setVisible(true);
    }

    public void update() {
//        StringBuilder updatedText = new StringBuilder();
//        String seq = _owner.currentGame().activePlayer().currentSequence().getWordFromCells();
//
//        for (int i = 0; i < seq.length(); i++) {
//            updatedText.append(Character.toUpperCase(seq.charAt(i)));
//
//            if (i != seq.length()-1)
//                updatedText.append(" > ");
//        }
//
//        _cellSequenceView.setText(updatedText.toString());
    }
}
