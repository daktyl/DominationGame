package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.players.AI;
import com.domination.game.players.Player;
import com.domination.game.players.defaultAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameplayState extends GameState{
    List<Player> playerList = new ArrayList<Player>();
    public List<Cell> cellList = new ArrayList<Cell>();
    public List<Bacteria> bacteriaList = new ArrayList<Bacteria>();
    public GameplayState(Game game, SpriteBatch batch) {
        super(game, batch);
    }

    @Override
    public void init() {
        ResourceManager.getInstance().add("CellTexture",new Texture("cellhd.png"));
        ResourceManager.getInstance().add("BacteriaTexture",new Texture("bacteria.png"));
        ResourceManager.getInstance().add("Background",new Texture("background.png"));
        ResourceManager.getInstance().add("CellGlow",new Texture("cellglow.png"));
        GraphicalEntity background=new GraphicalEntity((Texture) ResourceManager.getInstance().get("Background"),batch);
        background.sprite.setScale(Gdx.graphics.getWidth()/background.sprite.getWidth(),Gdx.graphics.getHeight()/background.sprite.getHeight());
        background.sprite.setX(-background.sprite.getWidth()/2+Gdx.graphics.getWidth()/2);
        background.sprite.setY(-background.sprite.getHeight()/2+Gdx.graphics.getHeight()/2);
        playerList.add(new defaultAI(new GameplayWrapper(this),Color.FIREBRICK));
        playerList.add(new defaultAI(new GameplayWrapper(this),Color.GREEN));
        entityManager.add(background);
        generateMap(10);
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
        for (Bacteria bacteria : bacteriaList)
            entityManager.add(bacteria);
        for (Cell cell : cellList)
            entityManager.add(cell);
    }
    protected void generateMap(int cellNumber){
        cellNumber/=2;
        Random random = new Random();
        if(playerList.size()==2) {
            int x=random.nextInt(Gdx.graphics.getWidth()/2-Cell.radius*2)+Cell.radius,
                y=random.nextInt(Gdx.graphics.getHeight()-Cell.radius*2)+Cell.radius;
            float middleX=Gdx.graphics.getWidth()/2.f;
            cellList.add(new Cell(playerList.get(0), middleX+x, y, this.batch));
            cellList.add(new Cell(playerList.get(1), middleX-x, y, this.batch));

            for (int i = 0; i < cellNumber-1; i++) {
                x=random.nextInt(Gdx.graphics.getWidth()/2-Cell.radius*2)+Cell.radius;
                y=random.nextInt(Gdx.graphics.getHeight()-Cell.radius*2)+Cell.radius;
                cellList.add(new Cell(null, middleX+x, y, this.batch));
                cellList.add(new Cell(null, middleX-x, y, this.batch));
            }
        }
    }
    public synchronized Boolean sendBacteria(Cell source, Cell destination, Player player) {
        if (source != null && destination != null && source.getPlayer() == player && source.getBacteriaAmount() > 1 && !source.isBroken() && !destination.isBroken()) {
            int sentBacteriaAmmount = source.handleOutgoingBacteria();
            Bacteria bacteria = new Bacteria(player, source, destination, sentBacteriaAmmount, batch);
            entityManager.add(bacteria);
            return true;
        }
        else {
            return false;
        }
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

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for(Cell c : cellList)
            if(c.isOnCell(screenX,Gdx.graphics.getHeight()-screenY))
                c.glow();
            else
                c.dim();
        return true;
    }
}
