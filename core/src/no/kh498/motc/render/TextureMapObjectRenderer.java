package no.kh498.motc.render;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import no.kh498.motc.MotC;

/**
 * @author karl henrik
 * @since 28 jul 2017
 */
public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

    public TextureMapObjectRenderer(final TiledMap map, final float unitScale) {
        super(map, unitScale);
    }

    @Override
    public void renderObject(final MapObject object) {
        if (object.isVisible() && object instanceof TextureMapObject) {
            final TextureMapObject textureObject = (TextureMapObject) object;
            this.batch.draw(textureObject.getTextureRegion(), textureObject.getX() / MotC.TILE_RESOLUTION,
                            textureObject.getY() / MotC.TILE_RESOLUTION, textureObject.getOriginX(),
                            textureObject.getOriginY(),
                            textureObject.getTextureRegion().getRegionWidth() / MotC.TILE_RESOLUTION,
                            textureObject.getTextureRegion().getRegionHeight() / MotC.TILE_RESOLUTION,
                            textureObject.getScaleX(), textureObject.getScaleY(), textureObject.getRotation());
        }
    }
}
