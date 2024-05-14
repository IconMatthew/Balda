package ru.jadae.view;

import ru.jadae.model.Cell;
import ru.jadae.view.custom_panels.FormedWordsContainer;
import ru.jadae.view.utils.Styles;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CellSubSequencePanel extends FormedWordsContainer {
    private final GamePanel owner;
    private final JLabel cellSequenceView = new JLabel();

    public CellSubSequencePanel(GamePanel owner) {
        super(3);
        this.owner = owner;

        setBackground(Styles.SECONDARY_COLOR);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(this.owner.getWidth(), 40));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        cellSequenceView.setFont(Styles.TITLE_FONT);
        cellSequenceView.setForeground(Color.BLUE);
        cellSequenceView.setText("");
        add(cellSequenceView, constraints);

        setVisible(true);
    }

    public void update() {
        StringBuilder updatedText = new StringBuilder();
        List<Cell> cellList = owner.getGame().getActivePlayer().getWordFormer().getQueueOfCells();

        for (int i = 0; i < cellList.size(); i++) {
            updatedText.append(Character.toUpperCase(cellList.get(i).getCellValue()));

            if (i != cellList.size() - 1)
                updatedText.append(" > ");
        }

        cellSequenceView.setText(updatedText.toString());
    }
}
