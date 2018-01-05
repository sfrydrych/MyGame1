package com.sawek.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    BitmapFont scoreFont;

    public GameOverScreen(Game game) {
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

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        /*Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("KONIEC GRY", font);
        Label playAgainLabel = new Label("Zacznij od nowa", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);*/
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
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Zdobyte punkty: \n" + playerscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scoreFont, "Najleszy Wynik: \n" + highscore, Color.WHITE, 0, Align.left, false);
        GlyphLayout indeksLayout = new GlyphLayout(scoreFont, "Zdobyte indeksy: \n" + playerindeks + "/3", Color.WHITE, 0, Align.left, false);
        scoreFont.draw(stage.getBatch(), scoreLayout, stage.getWidth() / 2 - scoreLayout.width / 2, stage.getHeight() / 2);
        scoreFont.draw(stage.getBatch(), highScoreLayout, Gdx.graphics.getWidth() / 2 - highScoreLayout.width / 2, Gdx.graphics.getHeight() / 2 - scoreLayout.height);
        scoreFont.draw(stage.getBatch(), indeksLayout, stage.getWidth() / 2 - indeksLayout.width / 2, stage.getHeight() / 2 - scoreLayout.height);
        scoreFont.getData().setScale(0.5f,0.5f);
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
