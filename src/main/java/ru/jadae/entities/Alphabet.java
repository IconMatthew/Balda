package ru.jadae.entities;

import lombok.Getter;
import lombok.Setter;
import ru.jadae.enums.Languages;

@Getter
@Setter
public class Alphabet {
    private final String characters;

    public Alphabet(Languages languages) {
        this.characters = languages.getCharacters();
    }

    public boolean isValidLetter(Character character){
        return characters.contains(character.toString().toLowerCase());
    }
}
