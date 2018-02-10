package com.sawek.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sawek.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = MyGdxGame.TITLE;
		config.width = MyGdxGame.V_WIDTH * 2;
		config.height = MyGdxGame.V_HEIGHT * 2 + 64;
		config.addIcon("img/ii4.png", Files.FileType.Internal);
		config.addIcon("img/ii8.png", Files.FileType.Internal);
		config.addIcon("img/ii9.png", Files.FileType.Internal);
		config.addIcon("img/ii10.png", Files.FileType.Internal);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
