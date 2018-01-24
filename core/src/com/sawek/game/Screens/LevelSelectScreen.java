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
    private Image lvl1Img, lvl2Img, lvl3Img, backImg;
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


        Texture lvl1Tex = app.manager.get("img/level1.png", Texture.class);
        lvl1Img = new Image(lvl1Tex);
        lvl1Img.setPosition(stage.getWidth() / 2 - lvl1Img.getWidth() / 16 - 2, stage.getHeight() / 2);
        lvl1Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new PlayScreen((MyGdxGame) app));
            }
        });
        lvl1Img.addAction(scaleTo(.120f, .120f));
        stage.addActor(lvl1Img);


        Texture lvl2Tex = app.manager.get("img/level2.png", Texture.class);
        lvl2Img = new Image(lvl2Tex);
        lvl2Img.setPosition(stage.getWidth() / 2 - lvl2Img.getWidth() / 16 + 1, stage.getHeight() / 2 - lvl1Img.getHeight() / 8);
        lvl2Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new PlayScreen((MyGdxGame) app));
            }
        });
        lvl2Img.addAction(scaleTo(.120f, .120f));
        stage.addActor(lvl2Img);


        Texture lvl3Tex = app.manager.get("img/level3.png", Texture.class);
        lvl3Img = new Image(lvl3Tex);
        lvl3Img.setPosition(stage.getWidth() / 2 - lvl3Img.getWidth() / 16, stage.getHeight() / 2 - lvl1Img.getHeight() / 4);
        lvl3Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new PlayScreen((MyGdxGame) app));
            }
        });
        lvl3Img.addAction(scaleTo(.120f, .120f));
        stage.addActor(lvl3Img);


        Texture backTex = app.manager.get("img/back.png", Texture.class);
        backImg = new Image(backTex);
        backImg.setPosition(stage.getWidth() / 4 + backImg.getWidth()/16, stage.getHeight() / 4 - backImg.getHeight() / 8);
        backImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.mainMenuScreen);
            }
        });
        backImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(backImg);


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