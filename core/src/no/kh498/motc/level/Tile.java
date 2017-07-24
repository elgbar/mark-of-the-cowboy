package no.kh498.motc.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import no.kh498.motc.Log;
import no.kh498.motc.MotC;
import no.kh498.motc.Util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author karl henrik
 */
public class Tile {

    private final int worldX;
    private final int worldY;
    private Shape.Type type = null;
    private ETile background = ETile.WALL;
    private ETile middleground = ETile.WALL;
    private ETile foreground = ETile.WALL;

    private Body collisionBody = null;

    public final static byte NO_SHAPE_TYPE = (byte) 0xff; // -1

    Tile(final int worldX, final int worldY, final Shape.Type type) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.type = type;
        Log.log("tile world coordinates: " + this.worldX + "," + this.worldY);
        if (type != null) {
            final BodyDef bodyDef = new BodyDef();
//            this.collisionBody = MotC.getInstance().getLevel().getWorld().createBody(bodyDef);
            bodyDef.position.set(new Vector2(worldX, worldY - MotC.TILE_RESOLUTION / 2));

            final Shape shape;

            switch (type) {
                case Polygon:
                    shape = new PolygonShape();
                    ((PolygonShape) shape).setAsBox(MotC.TILE_RESOLUTION / 2, MotC.TILE_RESOLUTION / 2);
                    break;
                default:
                    System.out.println("Shape type '" + type.name() + "' cannot be used!");
            }
        }
    }

    Tile(final int worldX, final int worldY, final byte background, final byte middleground, final byte foreground,
         final byte shapeType) {
        this.worldX = worldX;
        this.worldY = worldY;
        Log.log("tile world coordinates: " + this.worldX + "," + this.worldY);
        this.background = ETile.getTile(background);
        this.middleground = ETile.getTile(middleground);
        this.foreground = ETile.getTile(foreground);
        this.type = Util.getShapeType(shapeType);
    }


    public void write(final OutputStream bw) throws IOException {
        bw.write((byte) this.background.ordinal());
        bw.write((byte) this.middleground.ordinal());
        bw.write((byte) this.foreground.ordinal());
        bw.write((this.type == null) ? NO_SHAPE_TYPE : (byte) this.type.ordinal());
    }

    public void render(final Batch batch) {
        if (this.background != ETile.AIR) {
            batch.draw(this.background.getTexture(), this.worldX, this.worldY);
        }
        if (this.middleground != ETile.AIR) {
            batch.draw(this.middleground.getTexture(), this.worldX, this.worldY);
        }
        if (this.foreground != ETile.AIR) {
            batch.draw(this.foreground.getTexture(), this.worldX, this.worldY);
        }
    }

    public ETile getBackground() {
        return this.background;
    }

    public void setBackground(final ETile background) {
        this.background = background;
    }

    public ETile getMiddleground() {
        return this.middleground;
    }

    public void setMiddleground(final ETile middleground) {
        this.middleground = middleground;
    }

    public ETile getForeground() {
        return this.foreground;
    }

    public void setForeground(final ETile foreground) {
        this.foreground = this.foreground;
    }

    public Body getCollisionBody() {
        return this.collisionBody;
    }

    public void setCollisionBody(final Body collisionBody) {
        this.collisionBody = collisionBody;
    }
}
