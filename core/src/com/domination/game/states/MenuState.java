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
    private int menuset;
    private int isset;

    @Override
    public void init() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ChunkfiveEx.ttf"));
        BitmapFont font50 = generator.generateFont(50);
        font50.setColor(Color.BLUE);
        generator.dispose();
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
                    if (isset == 1)
                    menuset --;
                    isset = 0;
                    Gdx.app.debug("KeyDown", "Up");
                    break;
                case Input.Keys.DOWN:
                    if (isset == 0)
                    menuset ++;
                    isset = 1;
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
    public void draw() {
        super.draw();
            shapeRenderer.begin();
            shapeRenderer.rect(textList.get(menuset).label.getX() - 10, textList.get(menuset).label.getY(),textList.get(menuset).label.getWidth() + 20, textList.get(menuset).label.getHeight());
            shapeRenderer.end();
    }
}
