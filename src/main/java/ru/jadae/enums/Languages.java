package ru.jadae.enums;

import lombok.Getter;

@Getter
public enum Languages {

    RUS("абвгдеёжзийклмнопрстуфхцчшщъыьэюя"),
    ENG("abcdefghijklmnopqrstuvwxyz");

    private final String characters;

    Languages(String characters) {
        this.characters = characters;
    }
}
