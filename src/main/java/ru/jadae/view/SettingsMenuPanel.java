package ru.jadae.view;


import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import ru.jadae.builder.GameBuilder;
import ru.jadae.view.custom_panels.*;
import ru.jadae.view.utils.AppStyles;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Set;

public class SettingsMenuPanel extends JPanel {

    // Todo получать допустимые значения словаря из builder
    private final GameFrame _owner;
    private final JTextField _firstPlayerNameField = new CustomTextField("Игрок 1");
    private final JTextField _secondPlayerNameField = new CustomTextField("Игрок 2");
//    private final JComboBox<String> _dictionarySelect;
    private final JComboBox<String> _fieldSizeSelect;
    private final JLabel _secondPlayerNameLabel = new JLabel("Второй игрок");
    private final JLabel _difficultyLevelLabel = new JLabel("Уровень сложности");
//    private final JComboBox<String> _difficultiesSelect;
    private final JCheckBox _isComputerPlayer = new JCheckBox("Против компьютера");
    private final JComboBox<String> _alphabetSelect = new JComboBox<>(new String[]{"ru"});

    public SettingsMenuPanel(@NotNull GameFrame owner) {
        _owner = owner;

//        // Получить допустимые словари
//        Set<String> dictionaries = GameBuilder.getInstance().getNamesDictionaries();
//        _dictionarySelect = new JComboBox<>(Arrays.stream(dictionaries.toArray()).toArray(String[]::new));

        // Получить допустимые размеры поля
        String[] fieldSizes = GameBuilder.getInstance().getValidFieldSizes()
                .stream().map(e -> e.toString() + "x" + e.toString()).toArray(String[]::new);

        _fieldSizeSelect = new JComboBox<>(fieldSizes);
//
//        // Получить доступные сложности компьютерного игрока
//        String[] availableDifficulties = GameBuilder.getInstance().availableDifficulties.keySet().toArray(new String[0]);

//        _difficultiesSelect = new JComboBox<>(availableDifficulties);

        // Отрисовка
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15,5,15,5);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0f;
        constraints.gridwidth = 4;

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel title = new JLabel("НОВАЯ ИГРА", SwingConstants.CENTER);
        title.setFont(AppStyles.TITLE_FONT);
        add(title, constraints);

        // Имена игроков
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        var firstPlayerLabel = new JLabel("Первый игрок");
        firstPlayerLabel.setFont(AppStyles.LABEL_FONT);
        add(firstPlayerLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 1;
        add(_firstPlayerNameField, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 2;
        constraints.gridx = 0;

        _secondPlayerNameLabel.setFont(AppStyles.LABEL_FONT);
        add(_secondPlayerNameLabel, constraints);

        _difficultyLevelLabel.setFont(AppStyles.LABEL_FONT);
        _difficultyLevelLabel.setVisible(false);
        add(_difficultyLevelLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 2;
        add(_secondPlayerNameField, constraints);

//        _difficultiesSelect.setVisible(false);
//        add(_difficultiesSelect, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        var dictionaryLabel = new JLabel("Словарь слов");
        dictionaryLabel.setFont(AppStyles.LABEL_FONT);
        add(dictionaryLabel, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 2;
//        add(_dictionarySelect, constraints);

        // Размер поля
        constraints.gridy = 4;
        constraints.gridx = 0;
        var fieldSizeLabel = new JLabel("Размер поля");
        fieldSizeLabel.setFont(AppStyles.LABEL_FONT);
        add(fieldSizeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        add(_fieldSizeSelect, constraints);

        // Алфавит
        constraints.gridy = 5;
        constraints.gridx = 0;
        var alphabetLabel = new JLabel("Алфавит");
        alphabetLabel.setFont(AppStyles.LABEL_FONT);
        add(alphabetLabel, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        add(_alphabetSelect, constraints);

        constraints.gridy = 6;
        constraints.gridx = 0;
        constraints.gridwidth = 1;

        add(_isComputerPlayer, constraints);
        _isComputerPlayer.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //_difficultiesSelect.setVisible(true);
                _secondPlayerNameField.setVisible(false);
                _difficultyLevelLabel.setVisible(true);
                _secondPlayerNameLabel.setVisible(false);
            } else {
                //_difficultiesSelect.setVisible(false);
                _secondPlayerNameField.setVisible(true);
                _difficultyLevelLabel.setVisible(false);
                _secondPlayerNameLabel.setVisible(true);
            }
        });


        // Кнопка
//        constraints.gridwidth = 2;
//        constraints.gridx = 1;
//        var _startButton = new CustomActionButton("СОЗДАТЬ");
//        _startButton.addActionListener(e -> this.onClickStart());
//        add(_startButton, constraints);
    }


    private void onClickStart() {
        String first = _firstPlayerNameField.getText();
        String second = _secondPlayerNameField.getText();

//        GameBuilder.getInstance().setPlayerName(0, first);
//        GameBuilder.getInstance().setPlayerName(1, second);
//        GameBuilder.getInstance().setDictionaryByName(_dictionarySelect.getSelectedItem().toString());
//
//        GameBuilder.getInstance().setFieldSide(
//                GameBuilder.getInstance().availableFieldSize.get(_fieldSizeSelect.getSelectedIndex()));
//
//        GameBuilder.getInstance().setDifficult(_difficultiesSelect.getSelectedItem().toString());
//
//        var dict = GameBuilder.getInstance().buildDictionary();
//        var field = GameBuilder.getInstance().buildGameField();
//
//        GameModel currentModel;
//        if (_isComputerPlayer.isSelected()) {
//            currentModel = GameBuilder.getInstance().buildGameModelWithBot(field, dict);
//        } else {
//            currentModel = GameBuilder.getInstance().buildGameModel(field, dict);
//        }
//
//        _owner.runGame(currentModel);
        setVisible(false);
    }
}
