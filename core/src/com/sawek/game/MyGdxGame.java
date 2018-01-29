package com.sawek.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sawek.game.Screens.ExitScreen;
import com.sawek.game.Screens.LevelSelectScreen;
import com.sawek.game.Screens.MainMenuScreen;
import com.sawek.game.Screens.PlayScreen;
import com.sawek.game.Screens.SplashScreen;
import com.sawek.game.Screens.SplashScreen2;
import com.sawek.game.Screens.StartScreen;

public class MyGdxGame extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BOX_BIT = 8;
    public static final short COIN_BIT = 16;
    public static final short INDEKS_BOX_BIT = 32;
    public static final short INDEKS_BIT = 64;
    public static final short DESTROYED_BIT = 128;
    public static final short OBJECT_BIT = 256;
    public static final short ENEMY_BIT = 512;
    public static final short ENEMY_HEAD_BIT = 1024;
    public static final short PLAYER_HEAD_BIT = 2048;
    public static final short WIN_WALL_BIT = 4096;

    public static AssetManager manager;
    public SpriteBatch batch;
    public OrthographicCamera camera;

    public SplashScreen splashScreen;
    public SplashScreen2 splashScreen2;
    public MainMenuScreen mainMenuScreen;
    public LevelSelectScreen levelSelectScreen;
    public PlayScreen playScreen;
    public ExitScreen exitScreen;
    public StartScreen startScreen;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/sounds/hit.wav", Sound.class);
        manager.load("audio/sounds/indeks.wav", Sound.class);
        manager.load("audio/sounds/playerlose.wav", Sound.class);
        manager.load("audio/sounds/playerwin.wav", Sound.class);
        manager.load("audio/sounds/point.wav", Sound.class);
        manager.load("img/libgdx.png", Texture.class);
        manager.load("img/pwsz.png", Texture.class);
        manager.load("img/level1.png", Texture.class);
        manager.load("img/level2.png", Texture.class);
        manager.load("img/level3.png", Texture.class);
        manager.load("img/yes.png", Texture.class);
        manager.load("img/no.png", Texture.class);
        manager.load("img/retry.png", Texture.class);
        manager.load("img/next.png", Texture.class);
        manager.load("img/back.png", Texture.class);
        manager.load("img/play.png", Texture.class);
        manager.load("img/exit.png", Texture.class);
        manager.load("img/bgs.png", Texture.class);
        manager.load("img/about.png", Texture.class);
        manager.load("img/continue.png", Texture.class);
        manager.load("img/win.png", Texture.class);
        manager.load("img/lose.png", Texture.class);
        manager.load("img/logo.png", Texture.class);
        manager.load("img/left.png", Texture.class);
        manager.load("img/right.png", Texture.class);
        manager.load("img/up.png", Texture.class);
        manager.load("img/down.png", Texture.class);
        manager.load("img/bgs2.png", Texture.class);
        manager.finishLoading();


        splashScreen = new SplashScreen(this);
        splashScreen2 = new SplashScreen2(this);
        mainMenuScreen = new MainMenuScreen(this);
        playScreen = new PlayScreen(this);
        levelSelectScreen = new LevelSelectScreen(this);
        exitScreen = new ExitScreen(this);
        startScreen = new StartScreen(this);
        //setScreen(new PlayScreen(this));
        this.setScreen(splashScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
        splashScreen.dispose();
        splashScreen2.dispose();
        mainMenuScreen.dispose();
        exitScreen.dispose();
        levelSelectScreen.dispose();
        playScreen.dispose();
        startScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
        manager.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
