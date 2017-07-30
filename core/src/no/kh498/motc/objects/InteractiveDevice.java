package no.kh498.motc.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;
import no.kh498.motc.MotC;

/**
 * @author karl henrik
 * @since 28 jul 2017
 */
public class InteractiveDevice {

    private final TextureMapObject object;
    private final Type type;
    private final String name;

    public InteractiveDevice(final TextureMapObject object) {
        this.object = object;
        this.type = Type.valueOf(object);
        this.name = object.getName();
    }

    public Vector2 getLocation() {
        return new Vector2(this.object.getX() / MotC.TILE_RESOLUTION, this.object.getY() / MotC.TILE_RESOLUTION);
    }

    public final Type getType() {
        return this.type;
    }

    public final TextureMapObject getObject() {
        return this.object;
    }

    public final String getName() {
        return this.name;
    }

    public boolean isVisible() {
        return this.object.isVisible();
    }

    public void setVisible(final boolean visible) {
        this.object.setVisible(visible);
    }

    @Override
    public String toString() {
        return "InteractiveDevice{" + "type=" + this.type + ", name='" + this.name + '}';
    }
}
