package no.kh498.motc.objects.network;

import no.kh498.motc.objects.InteractiveDevice;

import java.util.HashMap;
import java.util.Random;

/**
 * @author karl henrik
 * @since 28 jul 2017
 */
public class Network {

    private final HashMap<Integer, InteractiveDevice> connectedDevices;
    private static final Random random;
    private static final int MIN_ID = 1024;
    private static final int MAX_ID = 9999 - MIN_ID;

    static {
        random = new Random();
    }

    public Network(final InteractiveDevice... connectedDevicesArray) {
        this.connectedDevices = new HashMap<>();

        for (final InteractiveDevice device : connectedDevicesArray) {
            int id = -1;
            while (id == -1 || this.connectedDevices.containsKey(id)) {
                id = random.nextInt(MAX_ID) + MIN_ID;
            }
            this.connectedDevices.put(id, device);
        }
    }

    public InteractiveDevice get(final int ID) {
        return this.connectedDevices.get(ID);
    }

    /**
     * @return A shallow copy of the connected devices
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, InteractiveDevice> getMap() {
        return (HashMap<Integer, InteractiveDevice>) this.connectedDevices.clone();
    }


    @Override
    public String toString() {
        return this.connectedDevices.toString();
    }
}
