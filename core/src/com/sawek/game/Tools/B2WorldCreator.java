package com.sawek.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sawek.game.Items.FreeCoin;
import com.sawek.game.Items.FreeIndeks;
import com.sawek.game.Items.Item;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Screens.PlayScreen;
import com.sawek.game.Sprites.Brick;
import com.sawek.game.Sprites.CoinBox;
import com.sawek.game.Sprites.Crawler;
import com.sawek.game.Sprites.Enemy;
import com.sawek.game.Sprites.IndeksBox;
import com.sawek.game.Sprites.Turtle;
import com.sawek.game.Sprites.WinWall;

/**
 * Created by Sławek on 2017-09-17.
 */

public class B2WorldCreator {
    private Array<Crawler> crawlers;
    private Array<Turtle> turtles;
    private Array<FreeCoin> freeCoins;
    private Array<FreeIndeks> freeIndekses;

    public B2WorldCreator(PlayScreen screen) {
        BodyDef bdef = new BodyDef();
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Create groud body/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MyGdxGame.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //Create pipe body/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Create win wall body/fixtures
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            new WinWall(screen, object);
        }

        //Create brick body/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            new Brick(screen, object);
        }

        //Create coin box body/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            new CoinBox(screen, object);
        }

        //Create indeks box body/fixtures
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            new IndeksBox(screen, object);
        }

        //Create all crawlers
        crawlers = new Array<Crawler>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            crawlers.add(new Crawler(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }

        //Create all turtles
        turtles = new Array<Turtle>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }

        //Create all free coins
        freeCoins = new Array<FreeCoin>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            freeCoins.add(new FreeCoin(screen, rect.getX() / MyGdxGame.PPM, rect. getY() / MyGdxGame.PPM));
        }

        //Create all free indekses
        freeIndekses = new Array<FreeIndeks>();
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            freeIndekses.add(new FreeIndeks(screen, rect.getX() / MyGdxGame.PPM, rect. getY() / MyGdxGame.PPM));
        }
    }

    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(crawlers);
        enemies.addAll(turtles);
        return enemies;
    }

    public Array<Item> getItems(){
        Array<Item> items = new Array<Item>();
        items.addAll(freeCoins);
        items.addAll(freeIndekses);
        return items;
    }
}
