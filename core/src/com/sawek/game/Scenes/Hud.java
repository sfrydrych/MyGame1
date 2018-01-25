package com.sawek.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sawek.game.MyGdxGame;

/**
 * Created by Sławek on 2017-09-17.
 */

public class Hud implements Disposable {
    public static Integer indeks;
    private static Label indeksLabel;
    private Label indekszLabel;
    public static Integer score;
    private static Label scoreLabel;
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    public static Integer timer;
    private boolean timeUp;
    private float timeCount;
    private Label countdownLabel;
    private Label timezLabel;
    private Label scorezLabel;
    BitmapFont polishFont;


    public Hud(SpriteBatch sb) {

        polishFont = new BitmapFont(Gdx.files.internal("fonts/polishfont.fnt"));
        polishFont.getData().setScale(0.3f,0.3f);

        worldTimer = 300;
        timeCount = 0;
        score = 0;
        indeks = 0;
        timer = worldTimer;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(polishFont, Color.WHITE));
        indeksLabel = new Label(String.format("%01d/3", indeks), new Label.LabelStyle(polishFont, Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(polishFont, Color.WHITE));
        timezLabel = new Label("CZAS", new Label.LabelStyle(polishFont, Color.WHITE));
        indekszLabel = new Label("INDEKSY", new Label.LabelStyle(polishFont, Color.WHITE));
        scorezLabel = new Label("PUNKTY", new Label.LabelStyle(polishFont, Color.WHITE));

        table.add(indekszLabel).expandX().padTop(10);
        table.add(timezLabel).expandX().padTop(10);
        table.add(scorezLabel).expandX().padTop(10);
        table.row();
        table.add(indeksLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void addIndeks(int value) {
        indeks += value;
        indeksLabel.setText(String.format("%01d / 3", indeks));
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() {
        return timeUp;
    }
}
