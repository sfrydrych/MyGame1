package com.sawek.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Screens.PlayScreen;

/**
 * Created by Sławek on 2017-09-17.
 */

public class Player extends Sprite {
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation<TextureRegion> playerRun;
    private TextureRegion playerDead;
    private TextureRegion playerJump;
    private boolean runningRight;
    private boolean playerIsDead;
    private boolean playerIsWinner;
    private boolean timeToRedefinePlayer;
    private boolean destroyed;
    private float stateTimer;
    private PlayScreen screen;

    public Player(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        destroyed = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), i * 16, 0, 16, 23));
        playerRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        playerJump = new TextureRegion(screen.getAtlas().findRegion("player"), 80, 0, 16, 23);
        playerStand = new TextureRegion(screen.getAtlas().findRegion("player"), 0, 0, 16, 23);
        playerDead = new TextureRegion(screen.getAtlas().findRegion("player"), 96, 0, 16, 23);

        definePlayer();
        setBounds(0, 0, 16 / MyGdxGame.PPM, 23 / MyGdxGame.PPM);
        setRegion(playerStand);
    }

    public void update(float dt) {
        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }
        if (b2body.getPosition().y <= 1 / MyGdxGame.PPM) {
            die();
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 4 / MyGdxGame.PPM);
        setRegion(getFrame(dt));

        if (timeToRedefinePlayer)
            redefinePlayer();
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case WINNER:
                region = playerStand;
                break;
            case DEAD:
                region = playerDead;
                break;
            case JUMPING:
                region = playerJump;
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }


        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (playerIsDead)
            return State.DEAD;
        else if (playerIsWinner)
            return State.WINNER;
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public boolean isDead() {
        return playerIsDead;
    }

    public boolean isWinner() {
        return playerIsWinner;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void hit(Enemy enemy) {
        if (enemy instanceof Turtle && ((Turtle) enemy).getCurrentState() == Turtle.State.STANDING_SHELL) {
            ((Turtle) enemy).kick(this.getX() <= enemy.getX() ? Turtle.KICK_RIGHT_SPEED : Turtle.KICK_LEFT_SPEED);
        } else {
            die();
        }
    }

    public void die() {
        if (!isDead()) {
            MyGdxGame.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = MyGdxGame.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public void win() {
        if (!isWinner()) {
            MyGdxGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
            playerIsWinner = true;
            timeToRedefinePlayer = true;
        }
    }

    public void redefinePlayer() {
        world.destroyBody(b2body);
        timeToRedefinePlayer = false;
        destroyed = true;
        stateTimer = 0;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);
        fdef.filter.categoryBits = MyGdxGame.PLAYER_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT |
                MyGdxGame.COIN_BOX_BIT |
                MyGdxGame.INDEKS_BOX_BIT |
                MyGdxGame.INDEKS_BIT |
                MyGdxGame.BRICK_BIT |
                MyGdxGame.ENEMY_BIT |
                MyGdxGame.OBJECT_BIT |
                MyGdxGame.ENEMY_HEAD_BIT |
                MyGdxGame.WIN_WALL_BIT |
                MyGdxGame.COIN_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MyGdxGame.PPM, 6 / MyGdxGame.PPM), new Vector2(2 / MyGdxGame.PPM, 6 / MyGdxGame.PPM));
        fdef.filter.categoryBits = MyGdxGame.PLAYER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, WINNER}
}
