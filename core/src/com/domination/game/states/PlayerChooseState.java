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
    private int menuset = 3;
    private int AISet;
    private int AISetLeft;
    private boolean isfinished = false;
    private boolean isfinishedLeft = false;
    private boolean Humanselected = true;
    private boolean AIselected = false;
    private boolean AI1selected = false;

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
        TextEntity Start = new TextEntity("Press Enter to start", font50, this.batch);
        TextEntity PlayerChoose = new TextEntity("Choose player", font50, this.batch);
        TextEntity Vs = new TextEntity("vs", font50, this.batch);
        TextEntity Com = new TextEntity("  AI  ", font50, this.batch);
        TextEntity Com1 = new TextEntity("  AI  ", font50, this.batch);
        TextEntity Human = new TextEntity("Human", font50, this.batch);
        PlayerChoose.label.setPosition(Gdx.graphics.getWidth()/2 - PlayerChoose.label.getWidth()/2, Gdx.graphics.getHeight()/2 + 2*PlayerChoose.label.getHeight()/2);
        Vs.label.setPosition(Gdx.graphics.getWidth()/2 - Vs.label.getWidth()/2, Gdx.graphics.getHeight()/2 - Vs.label.getHeight()/2 - 50);
        Com.label.setPosition(Gdx.graphics.getWidth()/2 + 2*Com.label.getWidth()/2 + 110, Gdx.graphics.getHeight()/2 - Com.label.getHeight()/2 - 50);
        Human.label.setPosition(Gdx.graphics.getWidth()/2 - 2*Human.label.getWidth(), Gdx.graphics.getHeight()/2 - Human.label.getHeight()/2);
        Com1.label.setPosition(Gdx.graphics.getWidth()/2 - 2*Com1.label.getWidth() - 100, Gdx.graphics.getHeight()/2 - 5*Com1.label.getHeight()/2);
        Start.label.setPosition(Gdx.graphics.getWidth()/2 - 2*Com1.label.getWidth(), Gdx.graphics.getHeight()/2 - 5*Com1.label.getHeight()/2 - 100);
        textList1.add(Com1);
        textList1.add(Human);
        textList1.add(Com);
        textList1.add(Start);
        textList1.add(PlayerChoose);
        textList1.add(Vs);


        entityManager.add(background);
        entityManager.add(PlayerChoose);
        entityManager.add(Com);
        entityManager.add(Com1);
        entityManager.add(Vs);
        entityManager.add(Human);
        entityManager.add(Start);
        shapeRenderer.setColor(Color.BLACK);
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if ((x > textList1.get(0).label.getX() - 10) && (x < textList1.get(0).label.getX() - 10 + textList1.get(0).label.getWidth() + 20) && (y > textList1.get(0).label.getY() + 245) && (y < textList1.get(0).label.getY() + textList1.get(0).label.getHeight() + 245)) {
                menuset = 0;
                Humanselected = false;
                AIselected = true;
                if (isfinished) {
                    AISet = 0;
                    isfinished = false;
                }
                AISet++;
                if (AISet == 1){
                    textList1.get(0).label.setText("AI");
                } else if (AISet == 2) {
                    textList1.get(0).label.setText("FN");
                } else if (AISet == 3) {
                    textList1.get(0).label.setText("MM");
                } else if (AISet == 4) {
                    textList1.get(0).label.setText("MP");
                } else if (AISet == 5) {
                    textList1.get(0).label.setText("KW");
                } else if (AISet == 6) {
                    textList1.get(0).label.setText("AS");
                    isfinished = true;
                }
            }
            if ((x>textList1.get(1).label.getX() - 10) && (x<textList1.get(1).label.getX() - 10 + textList1.get(1).label.getWidth() + 20) && (y>textList1.get(1).label.getY()) && (y<textList1.get(1).label.getY() + textList1.get(1).label.getHeight())){
                Humanselected = true;
                AIselected = false;
                menuset = 1;
                return true;
            }

            if ((x > textList1.get(2).label.getX() - 10) && (x < textList1.get(2).label.getX() - 10 + textList1.get(2).label.getWidth() + 20) && (y > textList1.get(2).label.getY() + 100) && (y < textList1.get(2).label.getY() + textList1.get(2).label.getHeight() + 100)) {
                menuset = 2;
                AI1selected = true;
                if (isfinishedLeft) {
                    AISetLeft = 0;
                    isfinishedLeft = false;
                }
                AISetLeft++;
                if (AISetLeft == 1){
                    textList1.get(2).label.setText("AI");
                } else if (AISetLeft == 2) {
                    textList1.get(2).label.setText("FN");
                } else if (AISetLeft == 3) {
                    textList1.get(2).label.setText("MM");
                } else if (AISetLeft == 4) {
                    textList1.get(2).label.setText("MP");
                } else if (AISetLeft == 5) {
                    textList1.get(2).label.setText("KW");
                } else if (AISetLeft == 6) {
                    textList1.get(2).label.setText("AS");
                    isfinishedLeft = true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyDown ( int keycode){
        Gdx.app.debug("KeyDown", Integer.valueOf(keycode).toString());
        switch (keycode) {
            case Input.Keys.ESCAPE:
                game.popGameState();
                Gdx.app.debug("KeyDown", "Esc");
                return true;
            case Input.Keys.UP:
                menuset = 1;
                Gdx.app.debug("KeyDown", "Up");
                break;
            case Input.Keys.DOWN:
                menuset = 0;
                Gdx.app.debug("KeyDown", "Down");
                break;
            case Input.Keys.ENTER:
                if (Humanselected){
                    game.popGameState();
                    game.pushGameState(new HumanGameplayState(game, batch, AISetLeft));
                }
                else {
                    game.popGameState();
                    game.pushGameState(new GameplayState(game, batch, AISet, AISetLeft));
                }
        }
        return false;

    }

    @Override
    public void draw() {
        super.draw();
        if(!(menuset == 3)) {
            shapeRenderer.begin();
            shapeRenderer.rect(textList1.get(menuset).label.getX() - 10, textList1.get(menuset).label.getY(), textList1.get(menuset).label.getWidth() + 20, textList1.get(menuset).label.getHeight());
            shapeRenderer.end();
        }
        if(Humanselected){
            shapeRenderer.begin();
            shapeRenderer.rect(textList1.get(1).label.getX() - 10, textList1.get(1).label.getY(), textList1.get(1).label.getWidth() + 20, textList1.get(1).label.getHeight());
            shapeRenderer.end();
        }
        if(AIselected){
            shapeRenderer.begin();
            shapeRenderer.rect(textList1.get(0).label.getX() - 10, textList1.get(0).label.getY(), textList1.get(0).label.getWidth() + 20, textList1.get(0).label.getHeight());
            shapeRenderer.end();
        }
        if(AI1selected){
            shapeRenderer.begin();
            shapeRenderer.rect(textList1.get(2).label.getX() - 10, textList1.get(2).label.getY(), textList1.get(2).label.getWidth() + 20, textList1.get(2).label.getHeight());
            shapeRenderer.end();
        }


    }

}

