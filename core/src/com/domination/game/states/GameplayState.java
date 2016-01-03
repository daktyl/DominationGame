package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.GameWrapper;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.players.AI;
import com.domination.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameplayState extends GameState{
    List<Player> playerList = new ArrayList<Player>();
    List<Cell> cellList = new ArrayList<Cell>();
    List<Bacteria> bacteriaList = new ArrayList<Bacteria>();
    private final GameWrapper gameSimulation = new GameWrapper(this, cellList, bacteriaList);
    public GameplayState(Game game, SpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void init() {
        ResourceManager.getInstance().add("TestTexture",new Texture("badlogic.jpg"));
        playerList.add(new AI(gameSimulation,Color.FIREBRICK));
        playerList.add(new AI(gameSimulation,Color.CORAL));
        cellList.add(new Cell(playerList.get(0),450,Gdx.graphics.getHeight()-123,this.batch));
        cellList.add(new Cell(null,250,Gdx.graphics.getHeight()-250,this.batch));
        cellList.add(new Cell(playerList.get(1),350,Gdx.graphics.getHeight()-150,this.batch));
        addCellsAndBacteriasToEntityManager();
        for (Player player : playerList)
            if (player instanceof AI) {
                player.start();
            }
    }

    @Override
    public void update() {
        super.update();
        // Remove bacteria that reached the destination cell
        for (Bacteria bacteria : bacteriaList) {
            if (bacteria.isBroken()) {
                bacteriaList.remove(bacteria);
            }
        }
    }

    private void addCellsAndBacteriasToEntityManager() {
        for (Cell cell : cellList)
            entityManager.add(cell);
        for (Bacteria bacteria : bacteriaList)
            entityManager.add(bacteria);
    }

    public synchronized Boolean sendBacterias(Cell from, Cell to, Player player) {
        //TODO sprawdzić poprawność ruchu
        Bacteria bacteria = new Bacteria(player,from,to,from.getBacteriaAmount()/2,batch);
        entityManager.add(bacteria);
        from.getOutgiongBacterias();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.debug("KeyDown", Integer.valueOf(keycode).toString());
        switch (keycode) {
            case Input.Keys.ESCAPE:
                game.popGameState();
                Gdx.app.debug("KeyDown", "Esc");
                return true;
            case Input.Keys.SPACE:
        }
        return false;
    }
}
