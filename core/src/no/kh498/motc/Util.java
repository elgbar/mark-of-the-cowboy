package no.kh498.motc;

import no.kh498.motc.level.Tile;

import static com.badlogic.gdx.physics.box2d.Shape.Type;

/**
 * @author karl henrik
 * @since 0.1.0
 */
public class Util {

    //add a cache of the shape type values as .values() is calculated every time it is called
    private static final Type VALUES[];
    public static Type getShapeType(final byte type) {
        return type == Tile.NO_SHAPE_TYPE ? null : VALUES[type];
    }

    static {
        VALUES = Type.values();
    }
}
