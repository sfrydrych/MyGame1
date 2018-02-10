package com.sawek.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Hud;

import static com.badlogic.gdx.Gdx.app;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.sawek.game.Scenes.Hud.timer;

/**
 * Created by Sławek on 2018-01-25.
 */

public class StartScreen3 implements Screen {
    public static Integer level3 = 3;
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Hud hud;
    int playertimer, highscore, idkscount;
    BitmapFont polishFont;
    private TextureRegion reg;
    public Image backImg, nextImg, lvl3Img;
    private Music music;

    public StartScreen3(Game game) {
        reg = new TextureRegion(MyGdxGame.manager.get("img/sky.png", Texture.class), 0, 0, 400, 240);
        this.game = game;
        this.playertimer = timer;
        //this.playerindeks = indeks;

        Preferences prefs = app.getPreferences("mygdxgame");
        prefs.putInteger("level", level3);
        prefs.flush();
        this.highscore = prefs.getInteger("highscore3", 0);
        this.idkscount = prefs.getInteger("indekscount3", 0);

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);

        polishFont = new BitmapFont(Gdx.files.internal("fonts/polishfont.fnt"));
        music = MyGdxGame.manager.get("audio/music/theme.mp3", Music.class);
        music.setLooping(true);
    }

    @Override
    public void show() {
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        Texture lvl1Tex = MyGdxGame.manager.get("img/stage3.png", Texture.class);
        lvl3Img = new Image(lvl1Tex);
        lvl3Img.setPosition(stage.getWidth() / 2 - lvl3Img.getWidth() / 10, stage.getHeight()  - lvl3Img.getHeight() / 3);
        lvl3Img.addAction(scaleTo(.2f, .2f));
        stage.addActor(lvl3Img);


        Texture backTex = MyGdxGame.manager.get("img/back.png", Texture.class);
        backImg = new Image(backTex);
        backImg.setPosition(stage.getWidth() / 8 + backImg.getWidth() / 32, stage.getHeight() / 4 - backImg.getHeight() / 8);
        backImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new LevelSelectScreen((MyGdxGame) game));
                dispose();
            }
        });
        backImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(backImg);


        Texture nextTex = MyGdxGame.manager.get("img/continue.png", Texture.class);
        nextImg = new Image(nextTex);
        nextImg.setPosition(stage.getWidth() / 2 + nextImg.getWidth() / 12, stage.getHeight() / 4 - nextImg.getHeight() / 6);
        nextImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new PlayScreen((MyGdxGame) game));
                music.stop();
                music.play();
                dispose();
            }
        });
        nextImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(nextImg);


        stage.getBatch().begin();
        stage.getBatch().draw(reg, 0, 0);
        GlyphLayout highScoreLayout = new GlyphLayout(polishFont, "Najleszy Wynik: " + highscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout indeksLayout = new GlyphLayout(polishFont, "\nZdobyte indeksy: " + idkscount + "/3", Color.WHITE, 0, Align.left, false);
        GlyphLayout timerLayout = new GlyphLayout(polishFont, "\nCzas poziomu: " + playertimer, Color.WHITE, 0, Align.left, false);
        polishFont.draw(stage.getBatch(), highScoreLayout, stage.getWidth() / 2 - highScoreLayout.width / 2, 2 * stage.getHeight() / 3 - highScoreLayout.height / 2);
        polishFont.draw(stage.getBatch(), indeksLayout, stage.getWidth() / 2 - indeksLayout.width / 2, 2 * stage.getHeight() / 3 - highScoreLayout.height);
        polishFont.draw(stage.getBatch(), timerLayout, stage.getWidth() / 2 - timerLayout.width / 2, 2 * stage.getHeight() / 3 - indeksLayout.height - highScoreLayout.height / 2);
        polishFont.getData().setScale(0.32f,0.32f);
        stage.getBatch().end();

        update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
