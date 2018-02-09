package com.sawek.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sawek.game.Items.Coin;
import com.sawek.game.Items.Indeks;
import com.sawek.game.Items.Item;
import com.sawek.game.Items.ItemDef;
import com.sawek.game.MyGdxGame;
import com.sawek.game.Scenes.Background;
import com.sawek.game.Scenes.Controller;
import com.sawek.game.Scenes.Hud;
import com.sawek.game.Sprites.Enemy;
import com.sawek.game.Sprites.Player;
import com.sawek.game.Tools.B2WorldCreator;
import com.sawek.game.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

import static com.badlogic.gdx.Gdx.app;


public class PlayScreen implements Screen {


    private boolean debug = false;
    private MyGdxGame game;
    private TextureAtlas atlas;
    //public static boolean alreadyDestroyed = false;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Player player;
    private Music music;
    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn2;
    int lvl;

    private Background[] backgrounds;
    Controller controller;

    public PlayScreen(MyGdxGame game) {

        Preferences prefs = app.getPreferences("mygdxgame");
        this.lvl = prefs.getInteger("level", 1);
        atlas = new TextureAtlas("player_enemies_items.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam);
        hud = new Hud(game.batch);
        controller = new Controller(game.batch);

        maploader = new TmxMapLoader();
        //map = maploader.load("level1.tmx");
        map = maploader.load("level" + lvl +".tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        player = new Player(this);
        world.setContactListener(new WorldContactListener());
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
        itemsToSpawn2 = new LinkedBlockingQueue<ItemDef>();
        music = MyGdxGame.manager.get("audio/music/theme.mp3", Music.class);

        Texture bgs = MyGdxGame.manager.get("img/bgs" + lvl +".png", Texture.class);
        TextureRegion sky = new TextureRegion(bgs, 0, 0, 320, 240);
        TextureRegion clouds = new TextureRegion(bgs, 0, 240, 320, 240);
        TextureRegion mountains = new TextureRegion(bgs, 0, 480, 320, 240);
        TextureRegion buildings = new TextureRegion(bgs, 0, 720, 3840, 208);
        backgrounds = new Background[4];
        backgrounds[0] = new Background(sky, gamecam, 0f);
        backgrounds[1] = new Background(clouds, gamecam, 10f);
        backgrounds[2] = new Background(mountains, gamecam, 20f);
        backgrounds[3] = new Background(buildings, gamecam, 50f);
    }


    public void spawnItem(ItemDef idef) {
        itemsToSpawn.add(idef);
    }

    public void spawnItem2(ItemDef idef) {
        itemsToSpawn2.add(idef);
    }

    public void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Coin.class) {
                items.add(new Coin(this, idef.position.x, idef.position.y));
            }
        }
    }

    public void handleSpawningItems2() {
        if (!itemsToSpawn2.isEmpty()) {
            ItemDef idef = itemsToSpawn2.poll();
            if (idef.type == Indeks.class) {
                items.add(new Indeks(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if ((player.currentState != Player.State.DEAD) && (player.currentState == Player.State.JUMPING) && (player.currentState != Player.State.WINNER)) {
            if (((Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)) || (controller.isRightPressed()) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (((Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)) || (controller.isLeftPressed()) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if ((player.currentState != Player.State.DEAD) && (player.currentState != Player.State.JUMPING) && (player.currentState != Player.State.WINNER)) {
            if (((Gdx.app.getType() != Application.ApplicationType.Android) && (Gdx.input.isKeyJustPressed(Input.Keys.UP))) || ((Gdx.app.getType() == Application.ApplicationType.Android) && (controller.isUpPressed())))
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            if (((Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)) || (controller.isRightPressed()) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (((Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)) || (controller.isLeftPressed()) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {
        handleInput(dt);
        handleSpawningItems();
        handleSpawningItems2();
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        for (Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / MyGdxGame.PPM) {
                enemy.b2body.setActive(true);
            }
        }
        for (Item item : items) {
            item.update(dt);
        }

        for (Item item : creator .getItems()){
            item.update(dt);
        }

        hud.update(dt);
        if ((player.currentState != Player.State.DEAD) && (player.currentState != Player.State.WINNER)) {
            if ((player.getX() >= 192 / MyGdxGame.PPM) && (player.getX() <= 3632 / MyGdxGame.PPM))
                gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        for(int i = 0; i < backgrounds.length; i++) {
            backgrounds[i].render(game.batch);
        }

        renderer.render();
        if(debug) {
            b2dr.render(world, gamecam.combined);
        }

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies()) {
            enemy.draw(game.batch);
        }
        for(Item item : creator .getItems()) {
            item.draw(game.batch);
        }
        for (Item item : items) {
            item.draw(game.batch);
        }
        game.batch.end();
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            music.stop();
            dispose();
        }

        if (win()) {
            game.setScreen(new WinScreen(game));
            music.stop();
            dispose();
        }

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();
    }

    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 2) {
            return true;
        }
        return false;
    }

    public boolean win() {
        if (player.currentState == Player.State.WINNER && player.getStateTimer() > 1) {
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud() {
        return hud;
    }
}
