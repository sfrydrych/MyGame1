package com.sawek.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sawek.game.Items.Item;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Sprites.CoinBox;
import com.sawek.game.Sprites.Enemy;
import com.sawek.game.Sprites.IndeksBox;
import com.sawek.game.Sprites.InteractiveTileObject;
import com.sawek.game.Sprites.Player;

/**
 * Created by Sławek on 2017-09-18.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MyGdxGame.PLAYER_HEAD_BIT | MyGdxGame.BRICK_BIT:
            case MyGdxGame.PLAYER_HEAD_BIT | MyGdxGame.COIN_BOX_BIT:
            case MyGdxGame.PLAYER_HEAD_BIT | MyGdxGame.INDEKS_BOX_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;
            case MyGdxGame.ENEMY_HEAD_BIT | MyGdxGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                else
                    ((Enemy) fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            /*case MyGdxGame.ENEMY_BIT | MyGdxGame.WIN_WALL_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;*/
            case MyGdxGame.PLAYER_BIT | MyGdxGame.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.PLAYER_BIT)
                    if (fixA.getFilterData().categoryBits == MyGdxGame.PLAYER_BIT)
                        ((Player) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                    else
                        ((Player) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                ((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
                break;
            case MyGdxGame.PLAYER_BIT | MyGdxGame.WIN_WALL_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.PLAYER_BIT)
                    ((Player) fixA.getUserData()).win();
                else
                    ((Player) fixB.getUserData()).win();
                break;
            case MyGdxGame.COIN_BIT | MyGdxGame.COIN_BOX_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.COIN_BIT)
                    ((Item) fixA.getUserData()).use((CoinBox) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((CoinBox) fixA.getUserData());
                break;
            case MyGdxGame.INDEKS_BIT | MyGdxGame.INDEKS_BOX_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.COIN_BIT)
                    ((Item) fixA.getUserData()).use3((IndeksBox) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use3((IndeksBox) fixA.getUserData());
                break;
            case MyGdxGame.COIN_BIT | MyGdxGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.COIN_BIT)
                    ((Item)fixA.getUserData()).use2((Player)fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use2((Player)fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
