package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.players.HumanPlayer;
import com.domination.game.players.Player;
import com.badlogic.gdx.graphics.Color;


/**
 * Created by macbook on 07.01.2016.
 */
public class HumanGameplayState extends GameplayState {

    public HumanGameplayState(Game game, SpriteBatch batch) {
        super(game, batch);
    }
    private Cell current_cell = null;
    private HumanPlayer human;
    @Override
    public void init() {
        ResourceManager.getInstance().add("CellTexture",new Texture("cellhd.png"));
        ResourceManager.getInstance().add("BacteriaTexture",new Texture("bacteria.png"));
        ResourceManager.getInstance().add("Background",new Texture("background.png"));
        GraphicalEntity background=new GraphicalEntity((Texture) ResourceManager.getInstance().get("Background"),batch);
        background.sprite.setScale(Gdx.graphics.getWidth()/background.sprite.getWidth(),Gdx.graphics.getHeight()/background.sprite.getHeight());
        background.sprite.setX(-background.sprite.getWidth()/2+Gdx.graphics.getWidth()/2);
        background.sprite.setY(-background.sprite.getHeight()/2+Gdx.graphics.getHeight()/2);
        entityManager.add(background);
        human = new HumanPlayer(Color.YELLOW);
        playerList.add(human);
        playerList.add(new HumanPlayer(Color.GREEN));
        generateMap(10);
        addCellsAndBacteriasToEntityManager();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (Cell cell : cellList) {
            Gdx.app.log("clicl",String.valueOf(cell.getX())+" " +String.valueOf(screenX)+ " " +String.valueOf(screenY));
                if(current_cell == null && cell.getX()-cell.getRadius()<screenX && cell.getX()+cell.getRadius()>screenX && cell.getY()-cell.getRadius()<screenY && cell.getY()+cell.getRadius()>screenY) {
                    Gdx.app.log("clicl",String.valueOf(cell.getX()));
                    current_cell = cell;
                    break;
                }
                else if (current_cell == cell) {
                    current_cell = null;
                    break;
                }
                else
                {
                    sendBacteria(current_cell,cell,human);
                  current_cell = null;
                }
            }
        return true;
    }
}
