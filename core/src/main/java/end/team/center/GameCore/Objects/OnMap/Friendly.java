package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Logic.Chunk;
import end.team.center.GameCore.Objects.Map.Tree;

public abstract class Friendly extends Entity {
    private Chunk nowChunk;

    public Friendly(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, float worldHeight, float worldWidth) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
    }

    public void move(float deltaX, float deltaY, float delta) {
        isMoving = deltaX != 0 || deltaY != 0;

        float moveSpeed = speed * delta;

        // Направление движения
        if (deltaX > 0) {
            mRight = true;
        } else if (deltaX < 0) {
            mRight = false;
        }

        // === Перемещение по X ===
        float newX = vector.x + deltaX * moveSpeed;
        newX = Math.max(BOUNDARY_PADDING, Math.min(newX, worldWidth - BOUNDARY_PADDING - width));
        Rectangle futureX = new Rectangle(newX, vector.y, width, height);
        boolean blockedX = false;

        for (Tree t : nowChunk.getTrees()) {
            if (t.getBound().overlaps(futureX)) {
                blockedX = true;
                break;
            }
        }

        if (!blockedX) {
            vector.x = newX;
        }

        // === Перемещение по Y ===
        float newY = vector.y + deltaY * moveSpeed;
        newY = Math.max(BOUNDARY_PADDING, Math.min(newY, worldHeight - BOUNDARY_PADDING - height));
        Rectangle futureY = new Rectangle(vector.x, newY, width, height);
        boolean blockedY = false;

        for (Tree t : nowChunk.getTrees()) {
            if (t.getBound().overlaps(futureY)) {
                blockedY = true;
                break;
            }
        }

        if (!blockedY) {
            vector.y = newY;
        }

        // Применяем новую позицию
        setPosition(vector.x, vector.y);
        updateBound();

        stateTime += delta;
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public Chunk getChunk() {
        return nowChunk;
    }

    public void setChunk(Chunk c) {
        nowChunk = c;
    }
}
