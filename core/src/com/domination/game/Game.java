package com.domination.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.domination.game.engine.ResourceManager;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        ResourceManager resManager = new ResourceManager();
        resManager.add("logo", new Texture("badlogic.jpg"));
        resManager.add("logo", new Texture("badlogic.jpg"), true);
        img = (Texture) resManager.get("logo");

        //resManager.add("cell", new Texture("cell.jpg"));       - add a Texture with "cell" as a key, will throw an exception when the key already exists
		//resManager.add("cell", new Texture("cell.jpg"), true); - add a Texture with "cell" as a key, will override when the key already exists
        //Texture cell = (Texture) resManager.get("cell");       - get a Texture with "cell" as a key, casting is necessary
        //Texture cell = (Texture) resManager.add("cell, new Texture("cell.jpg")) - add a Texture with "cell" as a key and save its reference

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
