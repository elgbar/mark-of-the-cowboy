package no.kh498.motc.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author karl henrik
 * @since 0.1.0
 */
public enum ETile {

    AIR(null),
    WALL("wall.png");

    private Texture texture = null;

    ETile(final String file) {
        if (file != null) {
            this.texture = new Texture(Gdx.files.internal(file));
        }
    }

    private static final ETile VALUES[] = values();

    public static ETile getTile(final byte b) {
        return ETile.VALUES[b];
    }

    public Texture getTexture() {
        return this.texture;
    }
}
