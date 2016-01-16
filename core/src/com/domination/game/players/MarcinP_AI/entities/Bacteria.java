package com.domination.game.players.MarcinP_AI.entities;

import com.domination.game.ai_types.FakeBacteria;
import com.domination.game.players.MarcinP_AI.MarcinP_AI;
import com.domination.game.players.Player;

public class Bacteria {

    public FakeBacteria fakeBacteria;
    private MarcinP_AI aiEngine;

    public Bacteria(FakeBacteria fakeBacteria, MarcinP_AI aiEngine) {
        this.fakeBacteria = fakeBacteria;
        this.aiEngine = aiEngine;
    }
    public Bacteria(Player player, int amount, Cell source, Cell destination, MarcinP_AI aiEngine) {
        this.aiEngine = aiEngine;
        fakeBacteria = new FakeBacteria();
        this.fakeBacteria.player = player;
        this.fakeBacteria.amount = amount;
        this.fakeBacteria.source = source.fakeCell;
        this.fakeBacteria.destination = destination.fakeCell;
        this.fakeBacteria.endTime = aiEngine.expectedDeltaTimeBeetweenCells(source,destination);
    }
}
