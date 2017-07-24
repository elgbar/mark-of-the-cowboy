package no.kh498.motc.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import no.kh498.motc.Log;
import no.kh498.motc.MotC;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author karl henrik
 * @since 0.1.0
 */
public class Region {
    public static final byte REGION_SIZE = 10;

    private final byte regionX;
    private final byte regionY;

    private final int worldX;
    private final int worldY;

    private final Tile[][] tiles;

    /**
     * @param x The region x
     * @param y The region y
     */
    Region(final byte x, final byte y) {
        this.regionX = x;
        this.regionY = y;

        this.worldX = x * REGION_SIZE;
        this.worldY = y * REGION_SIZE;

        this.tiles = new Tile[REGION_SIZE][REGION_SIZE];

        for (int i = 0; i < REGION_SIZE; i++) {
            for (int j = 0; j < REGION_SIZE; j++) {
                this.tiles[i][j] =
                    new Tile(MotC.TILE_RESOLUTION * i + this.worldX, MotC.TILE_RESOLUTION * j + this.worldY, null);
            }
        }
    }

    Region(final byte x, final byte y, final Tile[][] tiles) {
        this.regionX = x;
        this.regionY = y;

        this.worldX = x * REGION_SIZE;
        this.worldY = y * REGION_SIZE;
        Log.log("region '" + x + "," + y + "' world coordinates: " + this.worldX + "," + this.worldY);

        this.tiles = tiles;
    }

    public void write(final OutputStream outputStream) throws IOException {
        outputStream.write(this.regionX);
        outputStream.write(this.regionY);
        for (final Tile[] tile_array : this.tiles) {
            for (final Tile tile : tile_array) {
                tile.write(outputStream);
            }
        }
    }

    public void render(final Batch batch) {
        for (final Tile[] tile_array : this.tiles) {
            for (final Tile tile : tile_array) {
                tile.render(batch);
            }
        }
    }

    /**
     * @param x The x value for the tile
     * @param y The y value for the tile
     *
     * @return A tile within this region
     */
    public Tile getTile(final int x, final int y) {
        return this.tiles[x][y];
    }

    /**
     * @return The x value of the region
     */
    public byte getRegionX() {
        return this.regionX;
    }
    /**
     * @return The x value of the region
     */
    public byte getRegionY() {
        return this.regionY;
    }
    /**
     * @return The x value of the starting tile in the world
     */
    public int getWorldX() {
        return this.worldX;
    }
    /**
     * @return The x value of the starting tile in the world
     */
    public int getWorldY() {
        return this.worldY;
    }
    /**
     * @return the list of tiles that this region contains
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }
}
