package no.kh498.motc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author karl henrik
 */
public class Player {

    public float x;
    public float y;

    private final float startx;
    private final float starty;

    private static final Texture playerTexture;

    static {
        final Pixmap pixmap =
            new Pixmap((int) MotC.TILE_RESOLUTION, (int) MotC.TILE_RESOLUTION * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(38 / 255f, 127 / 255f, 0 / 255f, 1);
        pixmap.fill();
        playerTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    Player(final float x, final float y) {
        this.x = this.startx = (x + 5) * 2;
        this.y = this.starty = y + .5f;

    }

    public Vector2 getLocation() {
        return new Vector2(this.x, this.y);
    }

    void update(final Batch batch, final float delta) {
        final int SPEED = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= delta * SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += delta * SPEED;
        }
        batch.draw(playerTexture, (Gdx.graphics.getWidth() * (1 / 3f)) - MotC.TILE_RESOLUTION / 2,
                   (this.starty * MotC.TILE_RESOLUTION * 2));
    }
}
