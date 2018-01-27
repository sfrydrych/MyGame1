package com.sawek.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sawek.game.MyGdxGame;

/**
 * Created by Sławek on 2017-12-26.
 */

public class Background {
    private TextureRegion image;
    private OrthographicCamera gamecam;
    private float scale;

    private float x;
    private float y;
    private int numDrawX;
    private int numDrawY;

    private float dx;
    private float dy;

    public Background(TextureRegion image, OrthographicCamera gamecam, float scale) {
        this.image = image;
        this.gamecam = gamecam;
        this.scale = scale;
        numDrawX = MyGdxGame.V_WIDTH / image.getRegionWidth() + 2;
        numDrawY = MyGdxGame.V_HEIGHT / image.getRegionHeight() + 1;
    }

    public void setVector(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update(float dt) {
        x += (dx * scale) * dt;
        y += (dy * scale) * dt;
    }

    public void render(SpriteBatch sb) {

        float x = ((this.x + gamecam.viewportWidth / 2 - gamecam.position.x) * scale) % image.getRegionWidth();
        float y = ((this.y + gamecam.viewportHeight / 2 - gamecam.position.y) * scale) % image.getRegionHeight();

        sb.begin();

        int colOffset = x > 0 ? -1 : 0;
        int rowOffset = y > 0 ? -1 : 0;
        for(int row = 0; row < numDrawY; row++) {
            for(int col = 0; col < numDrawX; col++) {
                sb.draw(image, x + (col + colOffset) * image.getRegionWidth(), y + (rowOffset + row) * image.getRegionHeight());
            }
        }

        sb.end();

    }
}
