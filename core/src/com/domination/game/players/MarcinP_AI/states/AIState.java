package com.domination.game.players.MarcinP_AI.states;

import com.domination.game.players.MarcinP_AI.MarcinP_AI;

public abstract class AIState {

    protected MarcinP_AI aiEngine;

    public abstract void enter(MarcinP_AI aiEngine);
    public abstract void execute(MarcinP_AI aiEngine);
    public abstract void exit(MarcinP_AI aiEngine);

}
