package com.domination.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.domination.game.engine.ResourceManager;
import com.domination.game.states.GameState;
import com.domination.game.states.GameplayState;
import java.util.Stack;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	private Stack<GameState> gameStatesStack = new Stack<GameState>();

	@Override
	public void create () {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ChunkfiveEx.ttf"));
		BitmapFont font25 = generator.generateFont(25);
		font25.setColor(Color.BLACK);
		generator.dispose();
		ResourceManager.getInstance().add("Font",font25);
		batch = new SpriteBatch();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		pushGameState(new GameplayState(this,batch));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (!gameStatesStack.empty()) {
			gameStatesStack.peek().update();
			gameStatesStack.peek().draw();
		}
		batch.end();
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
	}
}
