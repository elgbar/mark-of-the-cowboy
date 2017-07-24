package no.kh498.motc.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author karl henrik
 * @since 0.1.0
 */
public class Level {

    private final byte sizeX;
    private final byte sizeY;
    private Region[][] regions;
    private final World world;
    private final String name;

    public Level(final String name, final byte sizeX, final byte sizeY) {
        this(name, sizeX, sizeY, null);
        this.regions = new Region[sizeX][sizeY];
        for (byte i = 0; i < this.regions.length; i++) {
            for (byte j = 0; j < this.regions[i].length; j++) {
                this.regions[i][j] = new Region(i, j);
            }
        }
    }

    Level(final String name, final byte sizeX, final byte sizeY, final Region[][] regions) {
        if (sizeX < 1 || sizeY < 1) {
            throw new IllegalArgumentException("The size must be a positive number!");
        }
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.regions = regions;

        this.world = new World(new Vector2(0, 9.81F), true);
    }

    public void render(final Batch batch) {
        for (byte i = 0; i < this.regions.length; i++) {
            for (byte j = 0; j < this.regions[i].length; j++) {
                this.regions[i][j].render(batch);
            }
        }
    }

    public Region[][] getRegions() {
        return this.regions;
    }
    public World getWorld() {
        return this.world;
    }
    public String getName() {
        return this.name;
    }
    public byte getSizeY() {
        return this.sizeY;
    }
    public byte getSizeX() {
        return this.sizeX;
    }
}
