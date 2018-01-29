package com.sawek.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.sawek.game.Items.Coin;
import com.sawek.game.Items.ItemDef;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Hud;
import com.sawek.game.Screens.PlayScreen;

/**
 * Created by Sławek on 2017-09-18.
 */

public class CoinBox extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public CoinBox(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.COIN_BOX_BIT);
    }

    @Override
    public void onHeadHit(Player player) {

        if (getCell().getTile().getId() == BLANK_COIN) {
            MyGdxGame.manager.get("audio/sounds/hit.wav", Sound.class).play();
            Hud.addScore(0);
        } else {
            MyGdxGame.manager.get("audio/sounds/point.wav", Sound.class).play();
            Hud.addScore(200);
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
                    body.getPosition().y + 20 / MyGdxGame.PPM), Coin.class));
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
}
