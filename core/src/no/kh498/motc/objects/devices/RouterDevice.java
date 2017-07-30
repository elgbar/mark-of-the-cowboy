package no.kh498.motc.objects.devices;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import no.kh498.motc.objects.InteractiveDevice;

import java.util.HashSet;

/**
 * @author karl henrik
 * @since 28 jul 2017
 */
public class RouterDevice extends InteractiveDevice {

    private final HashSet<InteractiveDevice> connectedDevices;

    public RouterDevice(final TextureMapObject object) {
        super(object);
        this.connectedDevices = new HashSet<>();
    }

    public void add(final InteractiveDevice object) {
        this.connectedDevices.add(object);
    }

    public void remove(final InteractiveDevice object) {
        this.connectedDevices.remove(object);
    }

    public HashSet<InteractiveDevice> getConnectedDevices() {
        return this.connectedDevices;
    }
}
