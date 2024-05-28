package ru.jadae.complayer;

import lombok.Getter;
import ru.jadae.model.Cell;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class CompPlayerEntity {

    private ComputerPlayer computerPlayer;
    private List<Cell> sequence;
    private Cell cellToChose;
    private Character letterToEnter;

    public CompPlayerEntity(ComputerPlayer computerPlayer) {
        this.computerPlayer = computerPlayer;
    }

    // Получить все сущности, необходимые для выполнения хода
    public void findStepEntities() {
        Map<String, List<Cell>> wordToUnfinishedCellSubSequence = getWordsToSequencesMap();

        Map.Entry<String, List<Cell>> entry = wordToUnfinishedCellSubSequence.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getKey().length()))
                .orElse(null);

        if (entry == null) return;

        String biggestLengthOfWordToForm = entry.getKey();
        letterToEnter = biggestLengthOfWordToForm.charAt(0);

        Cell neighbourCell = entry.getValue().get(0).getNeighbours().stream()
                .filter(e -> e.getCellValue() == null)
                .findFirst()
                .orElse(null);

        if (neighbourCell == null) return;

        cellToChose = neighbourCell;
        sequence = entry.getValue();
    }

    // Получить все возможные слова для вставки
    private Map<String, List<Cell>> getWordsToSequencesMap() {
        List<Cell> flattenedList = Arrays.stream(computerPlayer.getField().getCells())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        // Нахождение всех последовательностей заполненных ячеек, у первой из которых есть пустые соседи
        List<List<Cell>> allSequences = findAllSequences(flattenedList);

        HashMap<String, List<Cell>> wordToUnfinishedCellSubSequence = new HashMap<>();

        // Создаём карту: подобранное слово - неполная последовательность ячеек
        allSequences.forEach(sequence -> {
            String word = computerPlayer.getWordFormer().getDictionary()
                    .getClosestWordToSubSequence(mapCellSequenceToString(sequence));
            if (word != null) {
                wordToUnfinishedCellSubSequence.put(word, sequence);
            }
        });

        return wordToUnfinishedCellSubSequence;
    }

    // Преобразовать последовательность ячеек в строку
    private String mapCellSequenceToString(List<Cell> sequence) {
        return sequence.stream()
                .map(Cell::getCellValue)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // Найти все непустые последовательности, в которых у первой ячейки будут пустые соседи
    private List<List<Cell>> findAllSequences(List<Cell> cells) {
        List<List<Cell>> allSequences = new ArrayList<>();

        cells.stream()
                .filter(cell -> cell.getCellValue() != null)
                .forEach(cell -> findSequencesFromCell(cell, new ArrayList<>(), new HashSet<>(), allSequences));

        return allSequences.stream()
                .filter(e -> e.get(0).hasEmptyNeighbours())
                .collect(Collectors.toList());
    }

    // Найти все последовательности, начинающиеся с ячейки
    private void findSequencesFromCell(Cell current, List<Cell> currentSequence, Set<Cell> visited, List<List<Cell>> allSequences) {
        visited.add(current);
        currentSequence.add(current);
        allSequences.add(new ArrayList<>(currentSequence));

        current.getNeighbours().stream()
                .filter(neighbour -> neighbour.getCellValue() != null && !visited.contains(neighbour))
                .forEach(neighbour -> findSequencesFromCell(neighbour, currentSequence, visited, allSequences));

        visited.remove(current);
        currentSequence.remove(currentSequence.size() - 1);
    }
}
