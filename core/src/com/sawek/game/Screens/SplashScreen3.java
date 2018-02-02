package com.sawek.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sawek.game.MyGdxGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SplashScreen3 implements Screen {
    private final MyGdxGame app;
    private Stage stage;
    private Image splashImg;
    BitmapFont polishFont;

    public SplashScreen3(final MyGdxGame app) {
        this.app = app;
        this.stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, app.camera));
        polishFont = new BitmapFont(Gdx.files.internal("fonts/polishfont.fnt"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.mainMenuScreen);
            }
        };

        Texture splashTex = app.manager.get("img/dot.png", Texture.class);
        splashImg = new Image(splashTex);
        splashImg.setPosition(0, 0);
        splashImg.addAction(sequence(alpha(0),
                scaleTo(stage.getWidth(), stage.getHeight()),
                fadeIn(0f, Interpolation.pow2), fadeOut(1.5f),
                delay(2f), fadeIn(1.5f, Interpolation.pow2),
                run(transitionRunnable)));
        stage.addActor(splashImg);
    }


    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            app.setScreen(app.mainMenuScreen);
        }

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        GlyphLayout creditsLayout = new GlyphLayout(polishFont, "Autor projektu: Sławomir Frydrych\nPromotor: dr Marcin Skuba", Color.WHITE, 0, Align.left, false);
        polishFont.draw(stage.getBatch(), creditsLayout, stage.getWidth() / 2 - creditsLayout.width / 2, 2 * stage.getHeight() / 3 - creditsLayout.height / 3);
        polishFont.getData().setScale(0.4f,0.4f);
        stage.getBatch().end();

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
