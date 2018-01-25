package com.sawek.game.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Screens.PlayScreen;
import com.sawek.game.Sprites.CoinBox;
import com.sawek.game.Sprites.IndeksBox;
import com.sawek.game.Sprites.Player;

/**
 * Created by Sławek on 2017-10-04.
 */

public class Coin extends Item {
    private float stateTime;
    private Animation<TextureRegion> blinkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Coin(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("point"), i * 16, 0, 16, 16));
        blinkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 0.1)
            super.draw(batch);
    }


    @Override
    public void use(CoinBox coinBox) {
        setToDestroy = true;
    }

    @Override
    public void use2(Player player) {

    }

    @Override
    public void use3(IndeksBox indeksBox) {

    }


    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);
        fdef.filter.categoryBits = MyGdxGame.COIN_BIT;
        fdef.filter.maskBits = MyGdxGame.PLAYER_BIT |
                MyGdxGame.GROUND_BIT |
                MyGdxGame.BRICK_BIT |
                MyGdxGame.OBJECT_BIT |
                MyGdxGame.COIN_BOX_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        } else if (!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(blinkAnimation.getKeyFrame(stateTime, true));
        }
    }
}