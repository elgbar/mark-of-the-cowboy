package no.kh498.motc.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import no.kh498.motc.objects.devices.CCTVDevice;
import no.kh498.motc.objects.devices.DoorDevice;
import no.kh498.motc.objects.devices.RouterDevice;

import java.lang.reflect.InvocationTargetException;

/**
 * @author karl henrik
 * @since 28 jul 2017
 */
public enum Type {
    CCTV(CCTVDevice.class),
    DOOR(DoorDevice.class),
    ROUTER(RouterDevice.class);

    private final Class<? extends InteractiveDevice> clazz;

    Type(final Class<? extends InteractiveDevice> clazz) {
        this.clazz = clazz;
    }

    public static Type valueOf(final TextureMapObject object) {
        return Type.valueOf((String) object.getProperties().get("type"));
    }

    /**
     * @param textureObject
     *     The object to create the device from
     *
     * @return A new instance of the correct extended interactiveObject
     */
    public static InteractiveDevice getNewInstance(final TextureMapObject textureObject) {
        final Type objType = valueOf(textureObject);
        try {
            return objType.clazz.getDeclaredConstructor(TextureMapObject.class).newInstance(textureObject);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
