package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.Game;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.entities.TextEntity;
import com.domination.game.players.Player;

/**
 * Created by macbook on 13.01.2016.
 */
public class ResultState extends GameState {

    public Player winner;
    public Player looser;


    public ResultState(Player winner, Player looser, Game game, SpriteBatch batch) {
        super(game, batch);
        this.winner = winner;
        this.looser = looser;

    }

    @Override
    public void init() {
        setDefaultBackground();
        TextEntity looserText = new TextEntity("Looser:"+looser.getName(), batch);
        entityManager.add(looserText);
        looserText.label.setPosition(500,500);

    }
}
