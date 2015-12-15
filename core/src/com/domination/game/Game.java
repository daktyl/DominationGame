package com.domination.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.gamestates.GameState;

import java.util.Stack;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private Stack<GameState> gameStatesStack = new Stack<GameState>();
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
			if(!gameStatesStack.empty())
				gameStatesStack.peek().draw();
		batch.end();
	}
	public void pushGameState(GameState gameState){
		gameState.init();
		gameStatesStack.push(gameState);
	}
	public  void popGameState(){
		gameStatesStack.peek().cleanUp();
		gameStatesStack.pop();
	}
}
