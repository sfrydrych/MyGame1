package com.sawek.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sawek.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

/**
 * Created by Sławek on 2017-09-30.
 */

public class MainMenuScreen implements Screen {

    private final MyGdxGame app;
    private Stage stage;
    private Image playImg;
    private Image exitImg;

    public MainMenuScreen(final MyGdxGame app) {

        this.app = app;
        this.stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        Texture playTex = app.manager.get("img/play_button_inactive.png", Texture.class);
        playImg = new Image(playTex);
        playImg.setPosition(stage.getWidth() / 2 - playImg.getWidth() / 16, stage.getHeight() / 2 - playImg.getHeight() / 16);
        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.playScreen);
            }
        });
        playImg.addAction(scaleTo(.125f, .125f));
        stage.addActor(playImg);

        Texture exitTex = app.manager.get("img/exit_button_inactive.png", Texture.class);
        exitImg = new Image(exitTex);
        exitImg.setPosition(stage.getWidth() / 2 - playImg.getWidth() / 16, stage.getHeight() / 2 - exitImg.getHeight() / 4);
        exitImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        exitImg.addAction(scaleTo(.160f, .160f));
        stage.addActor(exitImg);

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
