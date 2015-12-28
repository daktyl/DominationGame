package com.domination.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.entities.Bacteria;
import com.domination.game.entities.Cell;
import com.domination.game.states.GameState;
import com.domination.game.states.GameplayState;

import java.io.Console;
import java.util.Stack;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	private Stack<GameState> gameStatesStack = new Stack<GameState>();

	Bacteria bacteria;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		pushGameState(new GameplayState(this));
		batch = new SpriteBatch();

		Cell cell1 = new Cell(10,10);
		Cell cell2 = new Cell(510,110);
		bacteria = new Bacteria(null, batch, cell2, cell1, 100);
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

		if (bacteria.isBroken == false){
			bacteria.update();
			bacteria.draw();
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
