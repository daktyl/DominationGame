package com.domination.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.domination.game.Game;
import com.domination.game.engine.GameplayWrapper;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.entities.TextEntity;
import com.domination.game.players.Player;
import com.domination.game.players.defaultAI;

import java.util.ArrayList;
import java.util.List;

public class PlayerChooseState extends GameState {
    public PlayerChooseState(Game game, SpriteBatch batch) {
        super(game, batch);
    }
    public List<TextEntity> textList1 = new ArrayList<TextEntity>();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int menuset = 2;

    @Override
    public void init(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ChunkfiveEx.ttf"));
        BitmapFont font50 = generator.generateFont(50);
        font50.setColor(Color.BLUE);
        generator.dispose();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);
        GraphicalEntity background = new GraphicalEntity((Texture)ResourceManager.getInstance().get("Background"), this.batch);
        background.sprite.setScale(Gdx.graphics.getWidth()/background.sprite.getWidth(),Gdx.graphics.getHeight()/background.sprite.getHeight());
        background.sprite.setX(-background.sprite.getWidth()/2+Gdx.graphics.getWidth()/2);
        background.sprite.setY(-background.sprite.getHeight()/2+Gdx.graphics.getHeight()/2);
        ResourceManager.getInstance().get("font50");
        TextEntity PlayerChoose = new TextEntity("Choose player", font50, this.batch);
        TextEntity Vs = new TextEntity("vs", font50, this.batch);
        TextEntity Com = new TextEntity("AI", font50, this.batch);
        TextEntity Com1 = new TextEntity("AI", font50, this.batch);
        TextEntity Human = new TextEntity("Human", font50, this.batch);
        PlayerChoose.label.setPosition(Gdx.graphics.getWidth()/2 - PlayerChoose.label.getWidth()/2, Gdx.graphics.getHeight()/2 + 2*PlayerChoose.label.getHeight()/2);
        Vs.label.setPosition(Gdx.graphics.getWidth()/2 - Vs.label.getWidth()/2, Gdx.graphics.getHeight()/2 - Vs.label.getHeight()/2);
        Com.label.setPosition(Gdx.graphics.getWidth()/2 + 2*Com.label.getWidth()/2, Gdx.graphics.getHeight()/2 - Com.label.getHeight()/2);
        Human.label.setPosition(Gdx.graphics.getWidth()/2 - 2*Human.label.getWidth(), Gdx.graphics.getHeight()/2 - Human.label.getHeight()/2);
        Com1.label.setPosition(Gdx.graphics.getWidth()/2 - 2*Com1.label.getWidth(), Gdx.graphics.getHeight()/2 - Com1.label.getHeight()/2);
        textList1.add(Com1);
        textList1.add(Human);
        textList1.add(PlayerChoose);
        textList1.add(Vs);
        textList1.add(Com);
        entityManager.add(background);
        entityManager.add(PlayerChoose);
        entityManager.add(Com);
        entityManager.add(Com1);
        entityManager.add(Vs);
        entityManager.add(Human);
        shapeRenderer.setColor(Color.BLACK);
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if ((x>textList1.get(0).label.getX() - 10) && (x<textList1.get(0).label.getX() - 10 + textList1.get(0).label.getWidth() + 20) && (y>textList1.get(0).label.getY()) && (y<textList1.get(0).label.getY() + textList1.get(0).label.getHeight())) {
                game.popGameState();
                game.pushGameState(new GameplayState(game, batch));
            }
            if ((x>textList1.get(1).label.getX() - 10) && (x<textList1.get(1).label.getX() - 10 + textList1.get(1).label.getWidth() + 20) && (y>textList1.get(1).label.getY()) && (y<textList1.get(1).label.getY() + textList1.get(1).label.getHeight())){
                game.popGameState();
                game.pushGameState(new HumanGameplayState(game, batch));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if ((screenX>textList1.get(0).label.getX() - 10) && (screenX<textList1.get(0).label.getX() - 10 + textList1.get(0).label.getWidth() + 20) && (screenY>textList1.get(0).label.getY()) && (screenY<textList1.get(0).label.getY() + textList1.get(0).label.getHeight())){
            menuset = 0;
        }
        if ((screenX>textList1.get(1).label.getX() - 10) && (screenX<textList1.get(1).label.getX() - 10 + textList1.get(1).label.getWidth() + 20) && (screenY>textList1.get(1).label.getY()) && (screenY<textList1.get(1).label.getY() + textList1.get(1).label.getHeight())){
            menuset = 1;
        }
        return false;
    }

    @Override
    public void draw() {
        super.draw();
        if(!(menuset == 2)) {
            shapeRenderer.begin();
            shapeRenderer.rect(textList1.get(menuset).label.getX() - 10, textList1.get(menuset).label.getY(), textList1.get(menuset).label.getWidth() + 20, textList1.get(menuset).label.getHeight());
            shapeRenderer.end();
        }
    }

}

