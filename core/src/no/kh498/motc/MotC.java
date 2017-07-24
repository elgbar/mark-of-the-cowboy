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

    Vector3 backgroundColors = new Vector3() {{
        add(60, 181, 181); //green
        add(252, 217, 32); //yellow
        add(229, 59, 81);  //red
        add(236, 108, 32); //orange
    }};

    @Override
    public void create() {
        INSTANCE = this;
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
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
