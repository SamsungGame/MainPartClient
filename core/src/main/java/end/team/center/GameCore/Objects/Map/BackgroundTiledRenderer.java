package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundTiledRenderer {
    private final TextureRegion[] tileTextures;
    private final int tileWidth, tileHeight;

    public BackgroundTiledRenderer(TextureRegion[] tileTextures, int tileWidth, int tileHeight) {
        this.tileTextures = tileTextures;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void render(Batch batch, OrthographicCamera camera) {
        int startX = (int)(camera.position.x - camera.viewportWidth / 2) / tileWidth - 1;
        int startY = (int)(camera.position.y - camera.viewportHeight / 2) / tileHeight - 1;
        int endX = (int)(camera.position.x + camera.viewportWidth / 2) / tileWidth + 1;
        int endY = (int)(camera.position.y + camera.viewportHeight / 2) / tileHeight + 1;

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                int worldX = x * tileWidth;
                int worldY = y * tileHeight;

                // Детерминированный выбор тайла (чтобы не менялся при движении камеры)
                int index = Math.abs((x * 73856093) ^ (y * 19349663)) % tileTextures.length;

                batch.draw(tileTextures[index], worldX, worldY);
            }
        }
    }
}
