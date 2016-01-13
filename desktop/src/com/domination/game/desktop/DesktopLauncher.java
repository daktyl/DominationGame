package com.domination.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.domination.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen=false;
		config.width=1280;
		config.height=720;
		//config.useGL30=true;
		config.x=0;
		config.y=0;
		config.title="Domination Game";
		new LwjglApplication(new Game(), config);
	}
}
