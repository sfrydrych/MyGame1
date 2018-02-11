package com.sawek.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sawek.game.Screens.PlayScreen;

/**
 * Created by Sławek on 2017-09-19.
 */

public abstract class Enemy extends Sprite {
    public Body b2body;
    public Vector2 velocity;
    protected World world;
    protected PlayScreen screen;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1, -1);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public abstract void hitOnHead(Player player);

    public abstract void onEnemyHit(Enemy enemy);

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
