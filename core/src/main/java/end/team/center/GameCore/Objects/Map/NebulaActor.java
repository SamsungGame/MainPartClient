package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import end.team.center.Screens.Game.GameScreen;

/**
 * Актёр Scene2D, представляющий однотонную, мягкую туманность.
 */
public class NebulaActor extends Actor {

    private Texture texture;
    private float alpha;
    private float time;

    public NebulaActor(int width, int height, float alpha) {
        this.alpha = alpha;
        this.texture = generateNebulaTexture(width, height, alpha);

        setSize(width, height);
        setPosition(MathUtils.random(100, GameScreen.WORLD_WIDTH), MathUtils.random(100, GameScreen.WORLD_HEIGHT));
        setOrigin(width / 2f, height / 2f);
        setRotation(MathUtils.random(360f));
    }

    private Texture generateNebulaTexture(int width, int height, float maxAlpha) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        int blobCount = MathUtils.random(70, 100);
        int clusterCount = MathUtils.random(3, 6); // случайное число кластеров

        // Случайные центры кластеров
        int[] clusterX = new int[clusterCount];
        int[] clusterY = new int[clusterCount];
        for (int i = 0; i < clusterCount; i++) {
            clusterX[i] = MathUtils.random(width);
            clusterY[i] = MathUtils.random(height);
        }

        for (int i = 0; i < blobCount; i++) {
            int minRadius = Math.max(8, Math.min(width, height) / 6);
            int maxRadius = Math.max(16, Math.min(width, height) / 3);

            if (minRadius > maxRadius) {
                int temp = minRadius;
                minRadius = maxRadius;
                maxRadius = temp;
            }

            int radius = MathUtils.random(minRadius, maxRadius);
            if (radius * 2 >= width || radius * 2 >= height) continue;

            int margin = radius;

            // Выбираем кластер
            int clusterIndex = MathUtils.random(clusterCount - 1);
            float angle = MathUtils.random(0f, MathUtils.PI2);
            float distance = MathUtils.random(10, width / 4f); // Разброс внутри кластера

            int cx = (int)(clusterX[clusterIndex] + MathUtils.cos(angle) * distance + MathUtils.random(-15, 15));
            int cy = (int)(clusterY[clusterIndex] + MathUtils.sin(angle) * distance + MathUtils.random(-15, 15));

            // Иногда (20%) — полностью случайные круги (для "шума")
            if (MathUtils.randomBoolean(0.2f)) {
                cx = MathUtils.random(margin, width - margin);
                cy = MathUtils.random(margin, height - margin);
            } else {
                cx = MathUtils.clamp(cx, margin, width - margin);
                cy = MathUtils.clamp(cy, margin, height - margin);
            }

            float r = 0.9f;
            float g = 1.0f;
            float b = 0.9f;
            float a = MathUtils.random(0.3f, maxAlpha);

            drawFlatCircle(pixmap, cx, cy, radius, r, g, b, a);
        }

        Texture tex = new Texture(pixmap);
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixmap.dispose();
        return tex;
    }

    /**
     * Рисует однородный круг с равномерной прозрачностью.
     */
    private void drawFlatCircle(Pixmap pixmap, int cx, int cy, int radius,
                                float r, float g, float b, float alpha) {
        pixmap.setColor(r, g, b, alpha);
        pixmap.fillCircle(cx, cy, radius);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        alpha = 0.3f + 0.2f * MathUtils.sin(time); // колебание прозрачности
        setRotation(getRotation() + delta * 2f);  // плавное вращение
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1f, 1f, 1f, alpha * parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), 1, 1, getRotation(),
            0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.setColor(Color.WHITE);
    }

    @Override
    public boolean remove() {
        if (texture != null) texture.dispose();
        return super.remove();
    }
}
