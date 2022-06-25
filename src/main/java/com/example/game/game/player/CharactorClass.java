package com.example.game.game.player;

public enum CharactorClass {
    INVOKER(2,0,1),
    ENCHANTER(2,0,1),
    WAROCK(2,0,1),
    HEALER(2,0,0),
    FARSEER(4,2,0),
    BLOODMAGE(2,0,0);

    private final int cardDraw;
    private final int cardSelect;
    private final int additionalCardDraw;

    CharactorClass(int cardDraw, int cardSelect, int additionalCardDraw) {
        this.cardDraw = cardDraw;
        this.cardSelect = cardSelect;
        this.additionalCardDraw = additionalCardDraw;
    }
}

