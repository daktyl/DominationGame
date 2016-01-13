package com.domination.game.players;

import com.badlogic.gdx.graphics.Color;

public class HumanPlayer extends Player {
    public HumanPlayer(Color color) {
        super(color);
    }

    @Override
    public String getPlayerName() {
        return "Human Player";
    }
}
