package com.sawek.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Hud;

import static com.sawek.game.Scenes.Hud.indeks;
import static com.sawek.game.Scenes.Hud.score;

/**
 * Created by Sławek on 2017-09-21.
 */

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    //private MyGdxGame app;
    private Hud hud;
    int playerscore, highscore, playerindeks;
    //BitmapFont scoreFont;
    BitmapFont polishFont;
    private TextureRegion reg;

    public GameOverScreen(Game game) {
        reg = new TextureRegion(MyGdxGame.manager.get("img/bgs.png", Texture.class), 0, 0, 400, 240);
        this.game = game;
        this.playerscore = score;
        this.playerindeks = indeks;

        Preferences prefs = Gdx.app.getPreferences("mygdxgame");
        this.highscore = prefs.getInteger("highscore", 0);

        if (playerscore > highscore){
            prefs.putInteger("highscore", playerscore);
            prefs.flush();
        }

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);

        polishFont = new BitmapFont(Gdx.files.internal("fonts/polishfont.fnt"));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((MyGdxGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        stage.getBatch().begin();
        stage.getBatch().draw(reg, 0, 0);
        GlyphLayout highScoreLayout = new GlyphLayout(polishFont, "Najleszy Wynik: " + highscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout scoreLayout = new GlyphLayout(polishFont, "\nZdobyte punkty: " + playerscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout indeksLayout = new GlyphLayout(polishFont, "\nZdobyte indeksy: " + playerindeks + "/3", Color.WHITE, 0, Align.left, false);
        polishFont.draw(stage.getBatch(), highScoreLayout, stage.getWidth() / 2 - highScoreLayout.width / 2, 2 * stage.getHeight() / 3);
        polishFont.draw(stage.getBatch(), scoreLayout, stage.getWidth() / 2 - scoreLayout.width / 2, 2 * stage.getHeight() / 3 - highScoreLayout.height);
        polishFont.draw(stage.getBatch(), indeksLayout, stage.getWidth() / 2 - indeksLayout.width / 2, 2 * stage.getHeight() / 3 - scoreLayout.height - highScoreLayout.height);
        polishFont.getData().setScale(0.3f,0.3f);
        stage.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

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
