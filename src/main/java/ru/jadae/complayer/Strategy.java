package ru.jadae.complayer;

import lombok.Getter;
import lombok.Setter;
import ru.jadae.model.Cell;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Strategy {
    private List<Cell> sequence;
    private Cell cellToChose;
    private Character letterToEnter;
    private int incVal = 0;
    public abstract void findStepEntities(ComputerPlayer computerPlayer);

    // Получить все возможные слова для вставки
    protected Map<String, List<Cell>> getWordsToSequencesMap(ComputerPlayer player) {
        List<Cell> flattenedList = Arrays.stream(player.getField().getCells())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        // Нахождение всех последовательностей заполненных ячеек, у первой из которых есть пустые соседи
        List<List<Cell>> allSequences = findAllSequences(flattenedList);

        HashMap<String, List<Cell>> wordToUnfinishedCellSubSequence = new HashMap<>();

        // Создаём карту: подобранное слово - неполная последовательность ячеек
        allSequences.forEach(sequence -> {
            String word = player.getWordFormer().getDictionary()
                    .getClosestWordToSubSequence(mapCellSequenceToString(sequence));
            if (word != null) {
                wordToUnfinishedCellSubSequence.put(word, sequence);
            }
        });

        return wordToUnfinishedCellSubSequence;
    }

    // Преобразовать последовательность ячеек в строку
    protected String mapCellSequenceToString(List<Cell> sequence) {
        return sequence.stream()
                .map(Cell::getCellValue)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // Найти все непустые последовательности, в которых у первой ячейки будут пустые соседи
    protected List<List<Cell>> findAllSequences(List<Cell> cells) {
        List<List<Cell>> allSequences = new ArrayList<>();

        cells.stream()
                .filter(cell -> cell.getCellValue() != null)
                .forEach(cell -> findSequencesFromCell(cell, new ArrayList<>(), new HashSet<>(), allSequences));

        return allSequences.stream()
                .filter(e -> e.get(0).hasEmptyNeighbours())
                .collect(Collectors.toList());
    }

    // Найти все последовательности, начинающиеся с ячейки
    protected void findSequencesFromCell(Cell current, List<Cell> currentSequence, Set<Cell> visited, List<List<Cell>> allSequences) {
        visited.add(current);
        currentSequence.add(current);
        allSequences.add(new ArrayList<>(currentSequence));

        current.getNeighbours().stream()
                .filter(neighbour -> neighbour.getCellValue() != null && !visited.contains(neighbour) && ++incVal < 1500)
                .forEach(neighbour -> findSequencesFromCell(neighbour, currentSequence, visited, allSequences));

        visited.remove(current);
        currentSequence.remove(currentSequence.size() - 1);
    }
}
