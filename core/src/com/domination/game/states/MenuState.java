package com.domination.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.domination.game.Game;
import com.domination.game.engine.EntityManager;
import com.domination.game.engine.ResourceManager;
import com.domination.game.entities.GraphicalEntity;
import com.domination.game.entities.TextEntity;

import java.util.ArrayList;
import java.util.List;

public class MenuState extends GameState {
    public MenuState(Game game, SpriteBatch batch) {
        super(game, batch);
    }
    public List<TextEntity> textList = new ArrayList<TextEntity>();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int menuset = 2;

    @Override
    public void init() {
        BitmapFont font50 = ResourceManager.getInstance().get("Font50");
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);
        ResourceManager.getInstance().add("font50", font50);
        ResourceManager.getInstance().add("Logo", new Texture("menu.png"));
        GraphicalEntity test = new GraphicalEntity((Texture)ResourceManager.getInstance().get("Logo"), this.batch);
        entityManager.add(test);
        TextEntity test1 = new TextEntity("Play", font50, this.batch);
        test1.label.setPosition(Gdx.graphics.getWidth()/2 - test1.label.getWidth()/2, Gdx.graphics.getHeight()/2 - test1.label.getHeight()/2);
        textList.add(test1);
        entityManager.add(test1);
        TextEntity test2 = new TextEntity("Exit", font50, this.batch);
        test2.label.setPosition(Gdx.graphics.getWidth()/2 - test2.label.getWidth()/2, Gdx.graphics.getHeight()/2 - test2.label.getHeight()/2 - 100);
        textList.add(test2);
        entityManager.add(test2);
        shapeRenderer.setColor(Color.BLACK);
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
                    menuset = 0;
                    Gdx.app.debug("KeyDown", "Up");
                    break;
                case Input.Keys.DOWN:
                    menuset = 1;
                    Gdx.app.debug("KeyDown", "Down");
                    break;
                case Input.Keys.ENTER:
                    switch(menuset){
                        case 0:
                            game.pushGameState(new GameplayState(game ,batch));
                            break;
                        case 1:
                            game.popGameState();
                            return true;
                    }
                    Gdx.app.debug("KeyDown", "Up");
                    break;
            }
            return false;

        }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if ((x>textList.get(0).label.getX() - 10) && (x<textList.get(0).label.getX() - 10 + textList.get(0).label.getWidth() + 20) && (y>textList.get(0).label.getY()) && (y<textList.get(0).label.getY() + textList.get(0).label.getHeight())) {
                game.pushGameState(new GameplayState(game, batch));
            }
            if ((x>textList.get(1).label.getX() - 10) && (x<textList.get(1).label.getX() - 10 + textList.get(1).label.getWidth() + 20) && (y>textList.get(1).label.getY() + 200) && (y<textList.get(1).label.getY() + textList.get(1).label.getHeight() + 200)){
                game.popGameState();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if ((screenX>textList.get(0).label.getX() - 10) && (screenX<textList.get(0).label.getX() - 10 + textList.get(0).label.getWidth() + 20) && (screenY>textList.get(0).label.getY()) && (screenY<textList.get(0).label.getY() + textList.get(0).label.getHeight())){
            menuset = 0;
        }
        if ((screenX>textList.get(1).label.getX() - 10) && (screenX<textList.get(1).label.getX() - 10 + textList.get(1).label.getWidth() + 20) && (screenY>textList.get(1).label.getY() + 200) && (screenY<textList.get(1).label.getY() + textList.get(1).label.getHeight() + 200)){
            menuset = 1;
        }
        return false;
    }

    @Override
    public void draw() {
        super.draw();
        if(!(menuset == 2)) {
            shapeRenderer.begin();
            shapeRenderer.rect(textList.get(menuset).label.getX() - 10, textList.get(menuset).label.getY(), textList.get(menuset).label.getWidth() + 20, textList.get(menuset).label.getHeight());
            shapeRenderer.end();
        }
    }
}
