package no.kh498.motc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import no.kh498.motc.objects.InteractiveDevice;
import no.kh498.motc.objects.Type;
import no.kh498.motc.objects.network.Network;
import no.kh498.motc.render.TextureMapObjectRenderer;
import no.kh498.motc.terminal.InputListener;
import no.kh498.motc.terminal.Terminal;

import java.io.File;
import java.util.Random;

public class MotC extends ApplicationAdapter {

    private static final String LEVEL_1 = "maps" + File.separatorChar + "level1.tmx";
    private static MotC INSTANCE;
    public static final float TILE_RESOLUTION = 32f;

    private SpriteBatch batch;
    private BitmapFont font;
    //    private AssetManager assetManager;
    private TiledMap tiledMap;
    private TextureMapObjectRenderer mapRenderer;
    private OrthographicCamera worldCamera;

    private final Vector3[] backgroundColors = new Vector3[4];

    private int bkClr;
    private Player player;
    private Terminal terminal;

    private Network network;

    @Override
    public void create() {
        INSTANCE = this;

        Gdx.input.setInputProcessor(new InputListener());

        final float div = 255f;
        this.backgroundColors[0] = new Vector3(60 / div, 181 / div, 181 / div); //green
        this.backgroundColors[1] = new Vector3(252 / div, 217 / div, 32 / div); //yellow
        this.backgroundColors[2] = new Vector3(229 / div, 59 / div, 81 / div);  //red
        this.backgroundColors[3] = new Vector3(236 / div, 108 / div, 32 / div); //orange

        //select a random background color
        this.bkClr = new Random().nextInt(this.backgroundColors.length);

        this.batch = new SpriteBatch();

//        this.assetManager = new AssetManager();
//        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
//        this.assetManager.load(LEVEL_1, TiledMap.class);
//        this.assetManager.finishLoadingAsset(LEVEL_1);
//        this.tiledMap = this.assetManager.get(LEVEL_1);

        this.tiledMap = new TmxMapLoader().load(LEVEL_1);

        this.worldCamera = new OrthographicCamera();
        this.worldCamera.setToOrtho(false, 20, 10);

        final float unitScale = 1 / TILE_RESOLUTION;
        this.mapRenderer = new TextureMapObjectRenderer(this.tiledMap, unitScale);
        this.mapRenderer.setView(this.worldCamera);

        this.font = new BitmapFont();

        this.player = new Player(0, 4);

        this.terminal = new Terminal();

        setupObjects();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(this.backgroundColors[this.bkClr].x, this.backgroundColors[this.bkClr].y,
                            this.backgroundColors[this.bkClr].z, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.worldCamera.position.set(this.player.x, this.worldCamera.position.y, 0);


        /* Drawing */
        this.mapRenderer.setView(this.worldCamera);
        this.mapRenderer.render();

        this.batch.begin();
        this.player.update(this.batch, Gdx.graphics.getDeltaTime());
        this.font.draw(this.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, this.font.getLineHeight());
        this.terminal.render(this.batch);
        this.batch.end();

        this.worldCamera.update();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        this.mapRenderer.dispose();
        this.tiledMap.dispose();
        this.terminal.dispose();
    }

    @Override
    public void resize(final int width, final int height) {
        this.terminal.resize(width, height);
    }


    private void setupObjects() {
        final MapLayer layer = this.tiledMap.getLayers().get("Objects");
        final MapObjects objects = layer.getObjects();
        final InteractiveDevice[] interactiveDevices = new InteractiveDevice[objects.getCount()];

        int i = 0;
        for (final Object object : objects) {
            interactiveDevices[i] = Type.getNewInstance((TextureMapObject) object);
            i++;
        }

        this.network = new Network(interactiveDevices);
        System.out.println("network = " + this.network);
    }

//    private void wireVisibility() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
//            final boolean visible = this.tiledMap.getLayers().get("Wire").isVisible();
//            this.tiledMap.getLayers().get("Wire").setVisible(!visible);
//        }
//    }
//
//    private void basicMove(final OrthographicCamera camera, final float delta) {
//        final Vector2 newCamPos = new Vector2();
//
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            newCamPos.x -= 1;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            newCamPos.x += 1;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            newCamPos.y += 1;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            newCamPos.y -= 1;
//        }
//        camera.position.add(newCamPos.x * delta * 3, newCamPos.y * delta * 3, 0);
//    }


    public static MotC getInstance() {
        return INSTANCE;
    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    public TiledMap getTiledMap() {
        return this.tiledMap;
    }

    public Network getNetwork() {
        return this.network;
    }

    public Player getPlayer() {
        return this.player;
    }
}
