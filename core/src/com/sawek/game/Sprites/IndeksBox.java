package com.sawek.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.sawek.game.Items.Indeks;
import com.sawek.game.Items.ItemDef;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Hud;
import com.sawek.game.Screens.PlayScreen;

/**
 * Created by Sławek on 2018-01-05.
 */

public class IndeksBox extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public IndeksBox(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.INDEKS_BOX_BIT);
    }

    @Override
    public void onHeadHit(Player player) {

        if (getCell().getTile().getId() == BLANK_COIN) {
            MyGdxGame.manager.get("audio/sounds/hit.wav", Sound.class).play();
            Hud.addScore(0);
        } else {
            MyGdxGame.manager.get("audio/sounds/indeks.wav", Sound.class).play();
            Hud.addScore(250);
            Hud.addIndeks(1);
            screen.spawnItem2(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 20 / MyGdxGame.PPM), Indeks.class));
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}