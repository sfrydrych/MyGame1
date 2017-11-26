package com.sawek.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sawek.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Sławek on 2017-09-30.
 */

public class SplashScreen2 implements Screen {
    private final MyGdxGame app;
    private Stage stage;
    private Image splashImg;

    public SplashScreen2(final MyGdxGame app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.playScreen);
            }
        };

        Texture splashTex = app.manager.get("img/pwsz.png", Texture.class);
        splashImg = new Image(splashTex);
        splashImg.setPosition(stage.getWidth() / 2 - 145, stage.getHeight() / 2 - 37);
        splashImg.addAction(sequence(alpha(0),
                scaleTo(.5f, .5f),
                fadeIn(2f, Interpolation.pow2),
                delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));
        stage.addActor(splashImg);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();

    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
