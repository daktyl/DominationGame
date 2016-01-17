package com.domination.game.ai_types;

import java.util.List;

public class Situation {
    public final List<FakeCell> cellList;
    public final List<FakeBacteria> bacteriaList;

    public Situation(List<FakeCell> cellList, List<FakeBacteria> bacteriaList) {
        this.cellList = cellList;
        this.bacteriaList = bacteriaList;
    }
}
