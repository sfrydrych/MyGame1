package com.sawek.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sawek.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = MyGdxGame.TITLE;
		//config.width = MyGdxGame.V_WIDTH * 2;
		//config.height = MyGdxGame.V_HEIGHT * 2;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
