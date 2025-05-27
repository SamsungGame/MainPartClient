package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {

    private final TextureRegion[][] tileMap;
    private final TextureRegion[] tileTextures;
    private final int rows, cols;
    private final int tileWidth, tileHeight;

    public BackgroundActor(TextureRegion[] tileTextures, int cols, int rows, int tileWidth, int tileHeight) {
        this.tileTextures = tileTextures;
        this.rows = rows;
        this.cols = cols;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        // Заранее генерируем карту спрайтов
        tileMap = new TextureRegion[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tileMap[y][x] = tileTextures[MathUtils.random(tileTextures.length - 1)];
            }
        }

        // Размер всего актора
        setSize(cols * tileWidth, rows * tileHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                batch.draw(tileMap[y][x], x * tileWidth, y * tileHeight);
            }
        }
    }
}
