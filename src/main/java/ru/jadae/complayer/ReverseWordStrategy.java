package ru.jadae.complayer;

import ru.jadae.model.Cell;
import ru.jadae.model.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ReverseWordStrategy extends Strategy{

    private Player humanPlayer;

    public ReverseWordStrategy(Player humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    @Override
    public void findStepEntities(ComputerPlayer computerPlayer) {
        if (humanPlayer.getFormedWords().isEmpty()) return;

        String lastFormedWord = humanPlayer.getFormedWords().get(humanPlayer.getFormedWords().size() - 1);
        List<List<Cell>> sequences = super.findAllSequences(Arrays.stream(computerPlayer.getField().getCells())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList()));

        List<String> words = sequences.stream().map(this::mapCellSequenceToString).toList();
        Map<String, List<Cell>> wordToCellSubsequence = new HashMap<>();

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(lastFormedWord)){
                wordToCellSubsequence.put(lastFormedWord, sequences.get(i));
                break;
            }
        }

        List<Cell> reversedSequence = wordToCellSubsequence.get(lastFormedWord);
        Collections.reverse(reversedSequence);

        setCellToChose(reversedSequence.get(1).getNeighbours().stream()
                .filter(e->e.getCellValue() == null)
                .findFirst()
                .orElse(null));

        if (getCellToChose() != null){
            setLetterToEnter(reversedSequence.get(0).getCellValue());
        }

        if (getLetterToEnter() != null){

            List<Cell> finalSeq = new ArrayList<>();

            for (int i = 1; i < reversedSequence.size(); i++) {
                finalSeq.add(reversedSequence.get(i));
            }

            String resWord = getLetterToEnter() + mapCellSequenceToString(finalSeq);
            if (!computerPlayer.getWordFormer().getDictionary().containsWord(resWord) ||
                    computerPlayer.getWordFormer().getDictionary().wasFormedBefore(resWord)){
                setCellToChose(null);
                setLetterToEnter(null);

            }
            else{
                setSequence(finalSeq);
            }
        }


    }
}
