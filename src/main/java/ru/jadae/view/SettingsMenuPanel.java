package ru.jadae.view;


import org.jetbrains.annotations.NotNull;
import ru.jadae.builder.GameBuilder;
import ru.jadae.enums.Languages;
import ru.jadae.model.Game;
import ru.jadae.view.custom_panels.ActionButton;
import ru.jadae.view.custom_panels.MenuTextField;
import ru.jadae.view.utils.Styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class SettingsMenuPanel extends JPanel {
    private final GameFrame owner;
    private final JTextField firstPlayerNameField = new MenuTextField("Игрок 1");
    private final JTextField secondPlayerNameField = new MenuTextField("Игрок 2");
    private final JComboBox<String> fieldSizeSelect;
    private final JLabel secondPlayerNameLabel = new JLabel("Второй игрок");
    private final JCheckBox isComputerPlayer = new JCheckBox("Против компьютера");
    private final JLabel difficultyLevelLabel = new JLabel("Уровень сложности");
    private final JComboBox<String> difficultiesSelect;
    private final JComboBox<String> alphabetSelect;

    public SettingsMenuPanel(@NotNull GameFrame owner) {
        this.owner = owner;

        String[] fieldSizes = GameBuilder.getInstance().getValidFieldSizes()
                .stream().map(e -> e.toString() + "x" + e.toString()).toArray(String[]::new);

        fieldSizeSelect = new JComboBox<>(fieldSizes);

        String[] alphabets = GameBuilder.getInstance().getValidLanguages()
                .stream().map(Enum::toString).toArray(String[]::new);

        alphabetSelect = new JComboBox<>(alphabets);

        String[] availableDifficulties = GameBuilder.getInstance().getValidDifficulties().toArray(String[]::new);

        difficultiesSelect = new JComboBox<>(availableDifficulties);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 5, 15, 5);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0f;
        constraints.gridwidth = 4;

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel title = new JLabel("НОВАЯ ИГРА", SwingConstants.CENTER);
        title.setFont(Styles.TITLE_FONT);
        add(title, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        var firstPlayerLabel = new JLabel("Первый игрок");
        firstPlayerLabel.setFont(Styles.LABEL_FONT);
        add(firstPlayerLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 1;
        add(firstPlayerNameField, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 2;
        constraints.gridx = 0;

        secondPlayerNameLabel.setFont(Styles.LABEL_FONT);
        add(secondPlayerNameLabel, constraints);

        difficultyLevelLabel.setFont(Styles.LABEL_FONT);
        difficultyLevelLabel.setVisible(false);
        add(difficultyLevelLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 2;
        add(secondPlayerNameField, constraints);

        difficultiesSelect.setVisible(false);
        add(difficultiesSelect, constraints);

        constraints.gridwidth = 3;
        constraints.gridy = 3;
        constraints.gridx = 0;
        var fieldSizeLabel = new JLabel("Размер поля");
        fieldSizeLabel.setFont(Styles.LABEL_FONT);
        add(fieldSizeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        add(fieldSizeSelect, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        var alphabetLabel = new JLabel("Алфавит");
        alphabetLabel.setFont(Styles.LABEL_FONT);
        add(alphabetLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        add(alphabetSelect, constraints);

        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.gridwidth = 1;

        add(isComputerPlayer, constraints);
        isComputerPlayer.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                difficultiesSelect.setVisible(true);
                secondPlayerNameField.setVisible(false);
                difficultyLevelLabel.setVisible(true);
                secondPlayerNameLabel.setVisible(false);
            } else {
                difficultiesSelect.setVisible(false);
                secondPlayerNameField.setVisible(true);
                difficultyLevelLabel.setVisible(false);
                secondPlayerNameLabel.setVisible(true);
            }
        });

        constraints.gridwidth = 2;
        constraints.gridx = 1;
        var _startButton = new ActionButton("СОЗДАТЬ");
        _startButton.addActionListener(e -> this.onClickStart());
        add(_startButton, constraints);
    }


    private void onClickStart() {
        String first = firstPlayerNameField.getText();
        String second = secondPlayerNameField.getText();

        GameBuilder.getInstance().setDictionary(Languages.valueOf((String) alphabetSelect.getSelectedItem()));
        GameBuilder.getInstance().setField(
                GameBuilder.getInstance().getValidFieldSizes().get(fieldSizeSelect.getSelectedIndex()));

        Game game;
        if (isComputerPlayer.isSelected()) {
            GameBuilder.getInstance().setHumanAndComputerPlayers(first, (String) difficultiesSelect.getSelectedItem());
        } else {
            GameBuilder.getInstance().setHumanPlayers(first, second);
        }
        game = GameBuilder.getInstance().initGame();
        owner.runGame(game);
        setVisible(false);
    }
}
