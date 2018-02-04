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

public class InfoScreen implements Screen {
    private Stage stage;
    private final MyGdxGame app;
    BitmapFont polishFont;
    private Image backImg;
    private TextureRegion reg;

    public InfoScreen(final MyGdxGame app) {
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

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        Texture backTex = app.manager.get("img/back.png", Texture.class);
        backImg = new Image(backTex);
        backImg.setPosition(stage.getWidth() / 4 + backImg.getWidth() / 16, stage.getHeight() / 4 - backImg.getHeight() / 8);
        backImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.mainMenuScreen);
            }
        });
        backImg.addAction(scaleTo(.120f, .120f));
        stage.addActor(backImg);


        stage.getBatch().begin();
        stage.getBatch().draw(reg, 0, 0);
        GlyphLayout questionLayout = new GlyphLayout(polishFont, "Gracz wciela się w rolę studenta, którego zadaniem jest\nzbieranie monet i indeksów rozmieszczonych i ukrytych\nna mapie. Za zebrane przedmioty gracz otrzymuje punkty,\nktóre na koniec są podliczane i porównywane z najwyższym\nwynikiem uzyskanym na danym poziomie. Poruszając się po\nmapie należy unikać przeszkód i uważać na przeciwników\nw postaci słabych ocen. Za ich pokonanie otrzymywane są\ndodatkowe punkty. Poziom zostaje zaliczony po przejściu\nmapy w wyznaczonym czasie.", Color.WHITE, 0, Align.left, false);
        polishFont.draw(stage.getBatch(), questionLayout, 4, stage.getHeight() - 10);
        polishFont.getData().setScale(0.265f,0.265f);
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


