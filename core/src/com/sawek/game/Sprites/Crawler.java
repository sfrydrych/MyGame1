package com.sawek.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Hud;
import com.sawek.game.Screens.PlayScreen;

/**
 * Created by Sławek on 2017-09-19.
 */

public class Crawler extends Enemy {
    float angle;
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Crawler(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("crawler"), i * 16, 0, 16, 17));
        walkAnimation = new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MyGdxGame.PPM, 17 / MyGdxGame.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }


    public TextureRegion getFrame(float dt) {
        TextureRegion region;

        region = walkAnimation.getKeyFrame(stateTime, true);

        if (velocity.x > 0 && region.isFlipX() == false) {
            region.flip(true, false);
        }
        if (velocity.x < 0 && region.isFlipX() == true) {
            region.flip(true, false);
        }
        return region;
    }


    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("crawler"), 64, 0, 16, 17));
            stateTime = 0;
        } else if (!destroyed) {
            setRegion(getFrame(dt));
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);
        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT |
                MyGdxGame.WIN_WALL_BIT |
                MyGdxGame.COIN_BOX_BIT |
                MyGdxGame.INDEKS_BOX_BIT |
                MyGdxGame.BRICK_BIT |
                MyGdxGame.ENEMY_BIT |
                MyGdxGame.OBJECT_BIT |
                MyGdxGame.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / MyGdxGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / MyGdxGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / MyGdxGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / MyGdxGame.PPM);
        head.set(vertice);
        
        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = MyGdxGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    public void onEnemyHit(Enemy enemy) {
        if (enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.MOVING_SHELL) {
            setToDestroy = true;
            MyGdxGame.manager.get("audio/sounds/hit.wav", Sound.class).play();
            Hud.addScore(250);
        } else
            reverseVelocity(true, false);
    }

    @Override
    public void hitOnHead(Player player) {
        setToDestroy = true;
        MyGdxGame.manager.get("audio/sounds/hit.wav", Sound.class).play();
        Hud.addScore(200);
    }
}
