package com.sawek.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private Image playImg, exitImg, logoImg;
    private TextureRegion reg;
    BitmapFont polishFont;
    private TextButton exitButton;
    private TextButton.TextButtonStyle exitButtonStyle;

    public MainMenuScreen(final MyGdxGame app) {

        reg = new TextureRegion(MyGdxGame.manager.get("img/bgs.png", Texture.class), 0, 0, 400, 240);
        this.app = app;
        this.stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, app.camera));
        polishFont = new BitmapFont(Gdx.files.internal("fonts/polishfont.fnt"));
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

        Texture logoTex = MyGdxGame.manager.get("img/logo.png", Texture.class);
        logoImg = new Image(logoTex);
        logoImg.setPosition(stage.getWidth() / 2 - logoImg.getWidth() / 7, stage.getHeight()  - logoImg.getHeight() / 3);
        logoImg.addAction(scaleTo(.3f, .3f));
        stage.addActor(logoImg);

        Texture playTex = app.manager.get("img/play.png", Texture.class);
        playImg = new Image(playTex);
        playImg.setPosition(stage.getWidth() / 2 - playImg.getWidth() / 17, stage.getHeight() / 2 - playImg.getHeight() / 5);
        playImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.levelSelectScreen);
            }
        });
        playImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(playImg);

        Texture exitTex = app.manager.get("img/exit.png", Texture.class);
        exitImg = new Image(exitTex);
        exitImg.setPosition(stage.getWidth() / 2 - exitImg.getWidth() / 17, stage.getHeight() / 2 - exitImg.getHeight() / 3);
        exitImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.exitScreen);
            }
        });
        exitImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(exitImg);

        /*exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.font = polishFont;
        exitButton = new TextButton("Wyjście", exitButtonStyle);
        exitButton.setPosition(stage.getWidth() / 2 - exitButton.getWidth() / 16, stage.getHeight() / 2 - exitButton.getHeight() / 3);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.exitScreen);
            }
        });
        exitButton.setTransform(true);
        exitButton.setScale(0.6f,0.6f);
        stage.addActor(exitButton);*/

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
