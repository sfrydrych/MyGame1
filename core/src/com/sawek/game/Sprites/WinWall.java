package com.sawek.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Screens.PlayScreen;

/**
 * Created by Sławek on 2017-09-22.
 */

public class WinWall extends InteractiveTileObject {

    public WinWall(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.WIN_WALL_BIT);
    }

    @Override
    public void onHeadHit(Player player) {

    }
}
