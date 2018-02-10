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
import static com.sawek.game.Scenes.Hud.indeks;
import static com.sawek.game.Scenes.Hud.score;

/**
 * Created by Sławek on 2017-09-22.
 */

public class WinScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Hud hud;
    int playerscore, highscore, playerindeks, idkscount, lvl;
    BitmapFont polishFont;
    private TextureRegion reg;
    private Image backImg, retryImg, nextImg, winImg;
    private Music music;

    public WinScreen(Game game) {
        reg = new TextureRegion(MyGdxGame.manager.get("img/sky.png", Texture.class), 0, 0, 400, 240);
        this.game = game;
        this.playerscore = score;
        this.playerindeks = indeks;

        Preferences prefs = app.getPreferences("mygdxgame");
        this.lvl = prefs.getInteger("level", 1);

        if (lvl == 1) {
            this.highscore = prefs.getInteger("highscore", 0);
            this.idkscount = prefs.getInteger("indekscount", 0);

            if (playerscore > highscore) {
                prefs.putInteger("highscore", playerscore);
                prefs.flush();
            }

            if (playerindeks > idkscount) {
                prefs.putInteger("indekscount", playerindeks);
                prefs.flush();
            }
        }

        else if (lvl == 2) {
            this.highscore = prefs.getInteger("highscore2", 0);
            this.idkscount = prefs.getInteger("indekscount2", 0);

            if (playerscore > highscore) {
                prefs.putInteger("highscore2", playerscore);
                prefs.flush();
            }

            if (playerindeks > idkscount) {
                prefs.putInteger("indekscount2", playerindeks);
                prefs.flush();
            }
        }

        else if (lvl == 3) {
            this.highscore = prefs.getInteger("highscore2", 0);
            this.idkscount = prefs.getInteger("indekscount3", 0);

            if (playerscore > highscore) {
                prefs.putInteger("highscore3", playerscore);
                prefs.flush();
            }

            if (playerindeks > idkscount) {
                prefs.putInteger("indekscount3", playerindeks);
                prefs.flush();
            }
        }


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


        Texture winTex = MyGdxGame.manager.get("img/win.png", Texture.class);
        winImg = new Image(winTex);
        winImg.setPosition(stage.getWidth() / 2 - winImg.getWidth() / 10, stage.getHeight()  - winImg.getHeight() / 3);
        winImg.addAction(scaleTo(.2f, .2f));
        stage.addActor(winImg);


        Texture backTex = MyGdxGame.manager.get("img/back.png", Texture.class);
        backImg = new Image(backTex);
        backImg.setPosition(stage.getWidth() / 16 + backImg.getWidth() / 80, stage.getHeight() / 4 - backImg.getHeight() / 8);
        backImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new LevelSelectScreen((MyGdxGame) game));
                music.stop();
                music.play();
                dispose();
            }
        });
        backImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(backImg);


        Texture retryTex = MyGdxGame.manager.get("img/retry.png", Texture.class);
        retryImg = new Image(retryTex);
        retryImg.setPosition(stage.getWidth() / 3 + retryImg.getWidth() / 48, stage.getHeight() / 4 - retryImg.getHeight() / 8);
        retryImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new PlayScreen((MyGdxGame) game));
                music.stop();
                music.play();
                dispose();
            }
        });
        retryImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(retryImg);



        Texture nextTex = MyGdxGame.manager.get("img/next.png", Texture.class);
        nextImg = new Image(nextTex);
        nextImg.setPosition(stage.getWidth() / 2 + nextImg.getWidth() / 8, stage.getHeight() / 4 - nextImg.getHeight() / 6);
        nextImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Preferences prefs = app.getPreferences("mygdxgame");
                int lvla = prefs.getInteger("level", 1);
                if (lvla >= 3){
                    game.setScreen(new LevelSelectScreen((MyGdxGame) game));
                    music.stop();
                    music.play();
                    dispose();
                } else {
                    int lvlb = lvla + 1;
                    prefs.putInteger("level", lvlb);
                    prefs.flush();
                    game.setScreen(new PlayScreen((MyGdxGame) game));
                    music.stop();
                    music.play();
                    dispose();
                }
            }
        });
        nextImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(nextImg);



        stage.getBatch().begin();
        stage.getBatch().draw(reg, 0, 0);
        GlyphLayout highScoreLayout = new GlyphLayout(polishFont, "Najleszy Wynik: " + highscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout scoreLayout = new GlyphLayout(polishFont, "\nZdobyte punkty: " + playerscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout indeksLayout = new GlyphLayout(polishFont, "\nZdobyte indeksy: " + playerindeks + "/3", Color.WHITE, 0, Align.left, false);
        polishFont.draw(stage.getBatch(), highScoreLayout, stage.getWidth() / 2 - highScoreLayout.width / 2, 2 * stage.getHeight() / 3 - highScoreLayout.height / 2);
        polishFont.draw(stage.getBatch(), scoreLayout, stage.getWidth() / 2 - scoreLayout.width / 2, 2 * stage.getHeight() / 3 - highScoreLayout.height);
        polishFont.draw(stage.getBatch(), indeksLayout, stage.getWidth() / 2 - indeksLayout.width / 2, 2 * stage.getHeight() / 3 - scoreLayout.height - highScoreLayout.height / 2);
        polishFont.getData().setScale(0.3f,0.3f);
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
