package com.domination.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.domination.game.engine.ResourceManager;
import com.domination.game.states.GameState;
import com.domination.game.states.GameplayState;
import com.domination.game.states.MenuState;
import com.domination.game.states.Pause;
import sun.reflect.generics.tree.FormalTypeParameter;

import java.util.Stack;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	private Stack<GameState> gameStatesStack = new Stack<GameState>();
	@Override
	public void create () {
		generateFonts();
		ResourceManager.getInstance().add("CellTexture",new Texture("cellhd.png"));
		ResourceManager.getInstance().add("BacteriaTexture",new Texture("bacteria.png"));
		ResourceManager.getInstance().add("Background",new Texture("background.png"));
		ResourceManager.getInstance().add("CellGlow",new Texture("cellglow.png"));
		ResourceManager.getInstance().add("Logo", new Texture("menu.png"));
		batch = new SpriteBatch();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		pushGameState(new MenuState(this,batch));


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!gameStatesStack.empty()) {
			gameStatesStack.peek().update();
			gameStatesStack.peek().draw();
		}

	}

	public void pushGameState(GameState state){
		state.init();
		Gdx.input.setInputProcessor(state);
		gameStatesStack.push(state);
	}

	public  void popGameState(){
		gameStatesStack.peek().cleanUp();
		gameStatesStack.pop();
		if(gameStatesStack.empty()) Gdx.app.exit();
		else Gdx.input.setInputProcessor(gameStatesStack.peek());
	}
	private void generateFonts(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ChunkfiveEx.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter25 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter25.size=25;
		parameter25.color = Color.BLACK;
		BitmapFont font25 = generator.generateFont(parameter25);

		FreeTypeFontGenerator.FreeTypeFontParameter parameter50 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter50.color=Color.BLACK;
		parameter50.size=50;
		BitmapFont font50 = generator.generateFont(parameter50);

		FreeTypeFontGenerator.FreeTypeFontParameter parameterCellFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameterCellFont.borderColor=Color.BLACK;
		parameterCellFont.color=Color.WHITE;
		parameterCellFont.borderWidth=2;
		parameterCellFont.size=30;
		BitmapFont cellFont = generator.generateFont(parameterCellFont);

		FreeTypeFontGenerator.FreeTypeFontParameter parameterBacteriaFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameterBacteriaFont.borderColor=Color.BLACK;
		parameterBacteriaFont.color=Color.WHITE;
		parameterBacteriaFont.borderWidth=2;
		parameterBacteriaFont.size=18;
		BitmapFont bacteriaFont = generator.generateFont(parameterBacteriaFont);
		generator.dispose();

		ResourceManager.getInstance().add("Font",font25);
		ResourceManager.getInstance().add("Font50",font50);
		ResourceManager.getInstance().add("CellFont",cellFont);
		ResourceManager.getInstance().add("BacteriaFont",bacteriaFont);
	}
}
