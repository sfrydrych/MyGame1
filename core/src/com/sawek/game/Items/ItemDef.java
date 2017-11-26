package com.sawek.game.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Sławek on 2017-10-05.
 */

public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }
}
