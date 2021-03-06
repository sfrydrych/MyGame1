package com.sawek.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.sawek.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class ExitScreen implements Screen {
    private Stage stage;
    private final MyGdxGame app;
    BitmapFont polishFont;
    private Image noImg, yesImg;
    private TextureRegion reg;

    public ExitScreen(final MyGdxGame app) {
        reg = new TextureRegion(MyGdxGame.manager.get("img/sky.png", Texture.class), 0, 0, 400, 240);
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

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        Texture yesTex = app.manager.get("img/yes.png", Texture.class);
        yesImg = new Image(yesTex);
        yesImg.setPosition(stage.getWidth() / 4 + yesImg.getWidth()/32, stage.getHeight() / 4 - yesImg.getHeight() / 8);
        yesImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        yesImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(yesImg);


        Texture noTex = app.manager.get("img/no.png", Texture.class);
        noImg = new Image(noTex);
        noImg.setPosition(stage.getWidth() / 2 + noImg.getWidth()/8, stage.getHeight() / 4 - noImg.getHeight() / 8);
        noImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.mainMenuScreen);
            }
        });
        noImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(noImg);


        stage.getBatch().begin();
        stage.getBatch().draw(reg, 0, 0);
        GlyphLayout questionLayout = new GlyphLayout(polishFont, "Czy na pewno chcesz wyjść?", Color.WHITE, 0, Align.left, false);
        polishFont.draw(stage.getBatch(), questionLayout, stage.getWidth() / 2 - questionLayout.width / 2, 2 * stage.getHeight() / 3 - questionLayout.height);
        polishFont.getData().setScale(0.4f,0.4f);
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
