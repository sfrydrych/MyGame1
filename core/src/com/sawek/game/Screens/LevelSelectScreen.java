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
    private Image lvl1Img, lvl2Img, lvl3Img, backImg, screen1Img, screen2Img, screen3Img;
    private TextureRegion reg;

    public LevelSelectScreen(final MyGdxGame app) {

        reg = new TextureRegion(MyGdxGame.manager.get("img/sky.png", Texture.class), 0, 0, 400, 240);
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
        lvl1Img.setPosition(stage.getWidth() / 2 - lvl1Img.getWidth() / 6 - 9, stage.getHeight() / 2 - lvl1Img.getHeight() / 8);
        lvl1Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new StartScreen((MyGdxGame) app));
            }
        });
        lvl1Img.addAction(scaleTo(.105f, .105f));
        stage.addActor(lvl1Img);


        Texture lvl2Tex = app.manager.get("img/level2.png", Texture.class);
        lvl2Img = new Image(lvl2Tex);
        lvl2Img.setPosition(stage.getWidth() / 2 - lvl2Img.getWidth() / 16 + 10, stage.getHeight() / 2 - lvl1Img.getHeight() / 8);
        lvl2Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new StartScreen2((MyGdxGame) app));
            }
        });
        lvl2Img.addAction(scaleTo(.105f, .105f));
        stage.addActor(lvl2Img);


        Texture lvl3Tex = app.manager.get("img/level3.png", Texture.class);
        lvl3Img = new Image(lvl3Tex);
        lvl3Img.setPosition(stage.getWidth() / 2 + lvl3Img.getWidth() / 16, stage.getHeight() / 2 - lvl1Img.getHeight() / 8);
        lvl3Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new StartScreen3((MyGdxGame) app));
            }
        });
        lvl3Img.addAction(scaleTo(.105f, .105f));
        stage.addActor(lvl3Img);


        Texture screen1Tex = app.manager.get("img/screen1.png", Texture.class);
        screen1Img = new Image(screen1Tex);
        screen1Img.setPosition(stage.getWidth() / 2 - screen1Img.getWidth() / 2 - 26, stage.getHeight() / 2 + screen1Img.getHeight() / 24);
        screen1Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new StartScreen((MyGdxGame) app));
            }
        });
        screen1Img.addAction(scaleTo(.37f, .37f));
        stage.addActor(screen1Img);


        Texture screen2Tex = app.manager.get("img/screen2.png", Texture.class);
        screen2Img = new Image(screen2Tex);
        screen2Img.setPosition(stage.getWidth() / 2 - screen2Img.getWidth() / 6 - 6, stage.getHeight() / 2 + screen2Img.getHeight() / 24);
        screen2Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new StartScreen2((MyGdxGame) app));
            }
        });
        screen2Img.addAction(scaleTo(.37f, .37f));
        stage.addActor(screen2Img);


        Texture screen3Tex = app.manager.get("img/screen2.png", Texture.class);
        screen3Img = new Image(screen3Tex);
        screen3Img.setPosition(stage.getWidth() / 2 + screen3Img.getWidth() / 5 + 3, stage.getHeight() / 2 + screen3Img.getHeight() / 24);
        screen3Img.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(new StartScreen3((MyGdxGame) app));
            }
        });
        screen3Img.addAction(scaleTo(.37f, .37f));
        stage.addActor(screen3Img);


        Texture backTex = app.manager.get("img/back.png", Texture.class);
        backImg = new Image(backTex);
        backImg.setPosition(stage.getWidth() / 4 + backImg.getWidth() / 14, stage.getHeight() / 4 - backImg.getHeight() / 8);
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