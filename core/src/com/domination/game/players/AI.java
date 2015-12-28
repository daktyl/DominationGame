package com.domination.game.players;

import com.badlogic.gdx.graphics.Color;
import com.domination.game.engine.GameWrapper;

public class AI extends Player{
    GameWrapper gameWrapper;
    public AI(GameWrapper gameWrapper, Color color){
        super(color);
        this.gameWrapper = gameWrapper;
    }
}
