package com.sawek.game.Scenes;

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
    private static Integer score;
    private static Label scoreLabel;
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private boolean timeUp;
    private float timeCount;
    private Label countdownLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label playerLabel;


    public Hud(SpriteBatch sb) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("CZAS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel = new Label("PUNKTY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(playerLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
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
