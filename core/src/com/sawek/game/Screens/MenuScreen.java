package com.sawek.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sawek.game.MyGdxGame;

/**
 * Created by Sławek on 2017-09-27.
 */

public class MenuScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Texture background;
    private Texture playBtn;

    public MenuScreen(Game game) {
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        this.game = game;
        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        MyGdxGame.manager.get("bg.png", Texture.class);
        MyGdxGame.manager.get("playbtn.png", Texture.class);

        if (Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((MyGdxGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

       /* .begin();
        stage.draw(background, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        stage.draw(playBtn, (MyGdxGame.V_WIDTH) - (playBtn.getWidth()), (MyGdxGame.V_HEIGHT));
        stage.end();*/
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
