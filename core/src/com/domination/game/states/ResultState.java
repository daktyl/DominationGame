package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.domination.game.Game;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.ButtonEntity;
import com.domination.game.entities.TextEntity;
import com.domination.game.players.Player;

public class ResultState extends GameState {

    public Player winner;
    public Player loser;
    BitmapFont font;

    public ResultState(Player winner, Player loser, Game game, SpriteBatch batch) {
        super(game, batch);
        this.winner = winner;
        this.loser = loser;

    }

    @Override
    public void init() {
        setDefaultBackground();
        font = ResourceManager.getInstance().get("Font50");
        TextEntity loserText = new TextEntity("The loser is "+ loser.getPlayerName(), font, this.batch);
        entityManager.add(loserText);
        loserText.label.setPosition(300,300);
        TextEntity Result = new TextEntity("RESULT", font, this.batch);
        entityManager.add(Result);
        Result.label.setPosition(500,500);
        TextEntity winnerText = new TextEntity("The winner is "+winner.getPlayerName(),font, this.batch);
        entityManager.add(winnerText);
        winnerText.label.setPosition(300,400);
        ButtonEntity button = new ButtonEntity("Back",35,35,batch);
        button.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popGameState();
                game.popGameState();
            }
        });
        entityManager.add(button);
    }


    @Override
    public boolean keyDown(int keycode) {
        game.popGameState();
        game.popGameState();
        return true;
    }
}
