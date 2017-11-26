package com.sawek.game.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Screens.PlayScreen;
import com.sawek.game.Sprites.CoinBox;
import com.sawek.game.Sprites.Player;

/**
 * Created by Sławek on 2017-10-04.
 */

public abstract class Item extends Sprite {
    protected Vector2 velocity;
    public Body body;
    protected World world;
    protected PlayScreen screen;
    protected boolean toDestroy;
    protected boolean destroyed;

    public Item(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        setBounds(getX(), getY(), 16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    protected abstract void defineItem();

    public abstract void use(CoinBox coinBox);

    public abstract void use2(Player player);

    public void update(float dt) {
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    public void destroy() {
        toDestroy = true;
    }
}
