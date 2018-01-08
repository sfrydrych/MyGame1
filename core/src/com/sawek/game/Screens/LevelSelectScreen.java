package com.sawek.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sawek.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

/**
 * Created by Sławek on 2018-01-08.
 */

public class LevelSelectScreen implements Screen {

    private final MyGdxGame app;
    private Stage stage;
    private Image playImg;
    private TextureRegion reg;

    public LevelSelectScreen(final MyGdxGame app) {

        reg = new TextureRegion(MyGdxGame.manager.get("img/bgs.png", Texture.class), 0, 0, 400, 240);
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
        playImg.setPosition(stage.getWidth() / 2 - playImg.getWidth() / 16, stage.getHeight() / 2 + playImg.getHeight() / 4);
        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.playScreen);
            }
        });
        playImg.addAction(scaleTo(.125f, .125f));
        stage.addActor(playImg);


        Texture playTex2 = app.manager.get("img/play_button_inactive.png", Texture.class);
        playImg = new Image(playTex2);
        playImg.setPosition(stage.getWidth() / 2 - playImg.getWidth() / 16, stage.getHeight() / 2 - playImg.getHeight() / 16);
        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.playScreen);
            }
        });
        playImg.addAction(scaleTo(.125f, .125f));
        stage.addActor(playImg);


        Texture playTex3 = app.manager.get("img/play_button_inactive.png", Texture.class);
        playImg = new Image(playTex3);
        playImg.setPosition(stage.getWidth() / 2 - playImg.getWidth() / 16, stage.getHeight() / 2 - playImg.getHeight() / 4);
        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.playScreen);
            }
        });
        playImg.addAction(scaleTo(.125f, .125f));
        stage.addActor(playImg);


        app.batch.setProjectionMatrix(stage.getCamera().combined);
        app.batch.begin();
        app.batch.draw(reg, 0, 0);
        app.batch.end();

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