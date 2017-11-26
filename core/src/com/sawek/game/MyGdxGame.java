package com.sawek.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.sawek.game.Screens.MainMenuScreen;
import com.sawek.game.Screens.PlayScreen;
import com.sawek.game.Screens.SplashScreen;
import com.sawek.game.Screens.SplashScreen2;

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
    public static final short DESTROYED_BIT = 32;
    public static final short OBJECT_BIT = 64;
    public static final short ENEMY_BIT = 128;
    public static final short ENEMY_HEAD_BIT = 256;
    public static final short PLAYER_HEAD_BIT = 512;
    public static final short WIN_WALL_BIT = 1024;
    public static AssetManager manager;
    public SpriteBatch batch;
    public BitmapFont font24;
    public OrthographicCamera camera;

    //public LoadingScreen loadingScreen;
    public SplashScreen splashScreen;
    public SplashScreen2 splashScreen2;
    public MainMenuScreen mainMenuScreen;
    public PlayScreen playScreen;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        batch = new SpriteBatch();
        initFonts();
        manager = new AssetManager();
        //manager.load("audio/music/mario_music.ogg", Music.class);
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/mariodie.wav", Sound.class);
        manager.load("img/libgdx.png", Texture.class);
        manager.load("img/pwsz.png", Texture.class);
        manager.finishLoading();

        //loadingScreen = new LoadingScreen(this);
        splashScreen = new SplashScreen(this);
        splashScreen2 = new SplashScreen2(this);
        mainMenuScreen = new MainMenuScreen(this);
        playScreen = new PlayScreen(this);
        //setScreen(new PlayScreen(this));
        this.setScreen(splashScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
        font24.dispose();
        //loadingScreen.dispose();
        splashScreen.dispose();
        splashScreen2.dispose();
        mainMenuScreen.dispose();
        playScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
        manager.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 24;
        params.color = Color.BLACK;
        font24 = generator.generateFont(params);
    }
}
