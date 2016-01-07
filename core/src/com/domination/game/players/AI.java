package com.domination.game.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.ai_types.FakeCell;
import com.domination.game.ai_types.Situation;
import com.domination.game.engine.GameplayWrapper;

import java.util.List;
import java.util.Random;

public abstract class AI extends Player {
    GameplayWrapper gameplayWrapper;
    public AI(GameplayWrapper gameplayWrapper, Color color) {
        super(color);
        this.gameplayWrapper = gameplayWrapper;
    }

    List<FakeBacteria> bacteriaList;
    List<FakeCell> cellList;

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            implementation();
        }
    }
    abstract protected void implementation();
}
