package no.kh498.motc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.io.File;
import java.util.Random;

public class MotC extends ApplicationAdapter {

    public final static int TILE_RESOLUTION = 32;

    private SpriteBatch batch;
    private BitmapFont font;
    //    private AssetManager assetManager;
    private TiledMap tiledMap;
    private final float unitScale = 1 / 16f;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private static final String LEVEL_1 = "maps" + File.separatorChar + "level1.tmx";
    private static MotC INSTANCE;

    private final Vector3[] backgroundColors = new Vector3[4];

    private int bkClr;

    @Override
    public void create() {
        INSTANCE = this;

        final float div = 255f;
        this.backgroundColors[0] = new Vector3(60 / div, 181 / div, 181 / div); //green
        this.backgroundColors[1] = new Vector3(252 / div, 217 / div, 32 / div); //yellow
        this.backgroundColors[2] = new Vector3(229 / div, 59 / div, 81 / div);  //red
        this.backgroundColors[3] = new Vector3(236 / div, 108 / div, 32 / div); //orange

        this.bkClr = new Random().nextInt(this.backgroundColors.length);

        this.batch = new SpriteBatch();

//        this.assetManager = new AssetManager();
//        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
//        this.assetManager.load(LEVEL_1, TiledMap.class);
//        this.assetManager.finishLoadingAsset(LEVEL_1);
//        this.tiledMap = this.assetManager.get(LEVEL_1);

        this.tiledMap = new TmxMapLoader().load(LEVEL_1);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 20, 10);

        this.mapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap, this.unitScale);
        this.mapRenderer.setView(this.camera);

        this.font = new BitmapFont();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(this.backgroundColors[this.bkClr].x, this.backgroundColors[this.bkClr].y,
                            this.backgroundColors[this.bkClr].z, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        basicMove(this.camera, Gdx.graphics.getDeltaTime());
        wireVisibility();

        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();

        /* Drawing */
        this.batch.begin();
        this.font.draw(this.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 30);
        this.batch.end();

        this.camera.update();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }

    private void wireVisibility() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            final boolean visible = this.tiledMap.getLayers().get("Wire").isVisible();
            this.tiledMap.getLayers().get("Wire").setVisible(!visible);
        }
    }

    private void basicMove(final OrthographicCamera camera, final float delta) {
        final Vector2 newCamPos = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newCamPos.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newCamPos.x += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newCamPos.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newCamPos.y -= 1;
        }
        camera.position.add(newCamPos.x * delta * 3, newCamPos.y * delta * 3, 0);
    }

    public static MotC getInstance() {
        return INSTANCE;
    }
}
