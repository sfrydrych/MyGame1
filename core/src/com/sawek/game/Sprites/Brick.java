package com.sawek.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Hud;
import com.sawek.game.Screens.PlayScreen;


/**
 * Created by Sławek on 2017-09-18.
 */

public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
        setCategoryFilter(MyGdxGame.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(50);
        MyGdxGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
