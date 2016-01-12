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
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.entities.TextEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerChooseState extends GameState {
    public PlayerChooseState(Game game, SpriteBatch batch) {
        super(game, batch);
    }
    public List<TextEntity> textList1 = new ArrayList<TextEntity>();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int menuset = 4;
    private int AISet;
    private int AISetRight = 1;
    private boolean isfinished = false;
    private boolean isfinishedRight = false;
    private boolean Humanselected = true;
    private boolean AIselected = false;
    private boolean AI1selected = true;

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
        TextEntity Start = new TextEntity("Start", font50, this.batch);
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
        Start.label.setPosition(Gdx.graphics.getWidth()/2 - 2*Com1.label.getWidth() + 160, Gdx.graphics.getHeight()/2 - 5*Com1.label.getHeight()/2 - 100);
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
            if ((x > textList1.get(3).label.getX() - 10) && (x < textList1.get(3).label.getX() - 10 + textList1.get(3).label.getWidth() + 20) && (y > textList1.get(3).label.getY() + 445) && (y < textList1.get(3).label.getY() + textList1.get(0).label.getHeight() + 445)) {
                if (Humanselected) {
                    game.popGameState();
                    game.pushGameState(new HumanGameplayState(game, batch, AISetRight));
                } else {
                    game.popGameState();
                    game.pushGameState(new GameplayState(game, batch, AISet, AISetRight));
                }
            }

                if ((x > textList1.get(0).label.getX() - 10) && (x < textList1.get(0).label.getX() - 10 + textList1.get(0).label.getWidth() + 20) && (y > textList1.get(0).label.getY() + 245) && (y < textList1.get(0).label.getY() + textList1.get(0).label.getHeight() + 245)) {
                    menuset = 0;
                    Humanselected = false;
                    AIselected = true;
                    if (isfinished) {
                        AISet = 0;
                        isfinished = false;
                    }
                    AISet++;
                    if (AISet == 1) {
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
                if ((x > textList1.get(1).label.getX() - 10) && (x < textList1.get(1).label.getX() - 10 + textList1.get(1).label.getWidth() + 20) && (y > textList1.get(1).label.getY()) && (y < textList1.get(1).label.getY() + textList1.get(1).label.getHeight())) {
                    Humanselected = true;
                    AIselected = false;
                    menuset = 1;
                    return true;
                }

                if ((x > textList1.get(2).label.getX() - 10) && (x < textList1.get(2).label.getX() - 10 + textList1.get(2).label.getWidth() + 20) && (y > textList1.get(2).label.getY() + 100) && (y < textList1.get(2).label.getY() + textList1.get(2).label.getHeight() + 100)) {
                    menuset = 2;
                    AI1selected = true;
                    if (isfinishedRight) {
                        AISetRight = 0;
                        isfinishedRight = false;
                    }
                    AISetRight++;
                    if (AISetRight == 1) {
                        textList1.get(2).label.setText("AI");
                    } else if (AISetRight == 2) {
                        textList1.get(2).label.setText("FN");
                    } else if (AISetRight == 3) {
                        textList1.get(2).label.setText("MM");
                    } else if (AISetRight == 4) {
                        textList1.get(2).label.setText("MP");
                    } else if (AISetRight == 5) {
                        textList1.get(2).label.setText("KW");
                    } else if (AISetRight == 6) {
                        textList1.get(2).label.setText("AS");
                        isfinishedRight = true;
                    }
                }
            }
            return false;
        }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if ((screenX>textList1.get(3).label.getX() - 10) && (screenX<textList1.get(3).label.getX() - 10 + textList1.get(3).label.getWidth() + 20) && (screenY>textList1.get(3).label.getY() + 445) && (screenY<textList1.get(3).label.getY() + textList1.get(0).label.getHeight() + 445)){
            menuset = 3;
        }
        else menuset = 4;
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
            case Input.Keys.ENTER:
                if (Humanselected){
                    game.popGameState();
                    game.pushGameState(new HumanGameplayState(game, batch, AISetRight));
                }
                else {
                    game.popGameState();
                    game.pushGameState(new GameplayState(game, batch, AISet, AISetRight));
                }
        }
        return false;

    }

    @Override
    public void draw() {
        super.draw();
        if(!(menuset == 4)) {
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

