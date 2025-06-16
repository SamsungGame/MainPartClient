package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import end.team.center.Screens.Game.GameScreen;

public class NebulaCloud {
    private final Array<Texture> templates = new Array<>();
    private final Array<Actor> actors = new Array<>();

    public NebulaCloud(int count) {
        // Сгенерировать уникальных шаблонов
        for (int i = 0; i < 5; i++) {
            templates.add(generateNebulaTexture(
                MathUtils.random(900, 1500),
                MathUtils.random(900, 1500),
                0.6f
            ));
        }

        // Создать заданное количество туманностей
        for (int i = 0; i < count; i++) {
            Texture tex = templates.random();
            NebulaActorLite actor = new NebulaActorLite(tex);
            actors.add(actor);
        }
    }

    public void addToStage(Stage stage) {
        for (Actor actor : actors) {
            stage.addActor(actor);
        }
    }

    public void dispose() {
        for (Texture tex : templates) tex.dispose();
    }

    private Texture generateNebulaTexture(int width, int height, float maxAlpha) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        int blobCount = MathUtils.random(3, 8);
        int clusterCount = MathUtils.random(3, 6);
        int[] clusterX = new int[clusterCount];
        int[] clusterY = new int[clusterCount];

        for (int i = 0; i < clusterCount; i++) {
            clusterX[i] = MathUtils.random(width);
            clusterY[i] = MathUtils.random(height);
        }

        for (int i = 0; i < blobCount; i++) {
            int radius = MathUtils.random(300, 400);
            int clusterIndex = MathUtils.random(clusterCount - 1);
            float angle = MathUtils.random(0f, MathUtils.PI2);
            float dist = MathUtils.random(10, width / 4f);

            int cx = (int)(clusterX[clusterIndex] + MathUtils.cos(angle) * dist + MathUtils.random(-15, 15));
            int cy = (int)(clusterY[clusterIndex] + MathUtils.sin(angle) * dist + MathUtils.random(-15, 15));
            cx = MathUtils.clamp(cx, radius, width - radius);
            cy = MathUtils.clamp(cy, radius, height - radius);

            float r = 0.9f, g = 1.0f, b = 0.9f, a = MathUtils.random(0.55f, maxAlpha);
            pixmap.setColor(r, g, b, a);
            pixmap.fillCircle(cx, cy, radius);
        }

        Texture tex = new Texture(pixmap);
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixmap.dispose();
        return tex;
    }

    private static class NebulaActorLite extends Actor {
        private final Texture texture;
        private float alpha = 0.45f;
        private float time = 0f;

        public NebulaActorLite(Texture texture) {
            this.texture = texture;
            setSize(texture.getWidth(), texture.getHeight());
            setOrigin(getWidth() / 2f, getHeight() / 2f);
            setPosition(MathUtils.random(100, GameScreen.WORLD_WIDTH),
                MathUtils.random(100, GameScreen.WORLD_HEIGHT));
            setRotation(MathUtils.random(360f));
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            time += delta;
            alpha = 0.3f + 0.2f * MathUtils.sin(time);
            setRotation(getRotation() + delta * 2f);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.setColor(1f, 1f, 1f, alpha * parentAlpha);
            batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), 1, 1, getRotation(),
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
            batch.setColor(Color.WHITE);
        }
    }
}
