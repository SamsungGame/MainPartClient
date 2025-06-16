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
        float actualMoveX = deltaX * moveSpeed;
        float actualMoveY = deltaY * moveSpeed;

        // Направление движения для анимации
        if (deltaX > 0) {
            mRight = true;
        } else if (deltaX < 0) {
            mRight = false;
        }

        // Временные переменные для новой позиции
        float tempX = vector.x;
        float tempY = vector.y;

        // === Проверка и перемещение по X ===
        float newX = tempX + actualMoveX;
        newX = Math.max(BOUNDARY_PADDING, Math.min(newX, worldWidth - BOUNDARY_PADDING - width));

        Rectangle nextXBound = new Rectangle(newX, tempY, width, height); // Проверяем только изменение X
        boolean collidedX = false;

        if (nowChunk != null) {
            for (Tree t : nowChunk.getTrees()) {
                Rectangle treeBound = t.getBound();
                if (nextXBound.overlaps(treeBound)) {
                    collidedX = true;
                    // Если произошло столкновение по X, корректируем позицию X
                    if (actualMoveX > 0) { // Движение вправо
                        newX = treeBound.x - width; // Прижимаем к левой границе дерева
                    } else if (actualMoveX < 0) { // Движение влево
                        newX = treeBound.x + treeBound.width; // Прижимаем к правой границе дерева
                    }
                    // Устанавливаем nextXBound на скорректированную позицию для дальнейших проверок (хотя здесь это не критично)
                    nextXBound.x = newX;
                    break; // Столкновение найдено, выходим из цикла
                }
            }
        }
        tempX = newX; // Применяем скорректированный X

        // === Проверка и перемещение по Y ===
        float newY = tempY + actualMoveY;
        newY = Math.max(BOUNDARY_PADDING, Math.min(newY, worldHeight - BOUNDARY_PADDING - height));

        Rectangle nextYBound = new Rectangle(tempX, newY, width, height); // Проверяем изменение Y, учитывая уже скорректированный X
        boolean collidedY = false;

        if (nowChunk != null) {
            for (Tree t : nowChunk.getTrees()) {
                Rectangle treeBound = t.getBound();
                if (nextYBound.overlaps(treeBound)) {
                    collidedY = true;
                    // Если произошло столкновение по Y, корректируем позицию Y
                    if (actualMoveY > 0) { // Движение вверх
                        newY = treeBound.y - height; // Прижимаем к нижней границе дерева
                    } else if (actualMoveY < 0) { // Движение вниз
                        newY = treeBound.y + treeBound.height; // Прижимаем к верхней границе дерева
                    }
                    nextYBound.y = newY; // Устанавливаем nextYBound на скорректированную позицию
                    break; // Столкновение найдено, выходим из цикла
                }
            }
        }
        tempY = newY; // Применяем скорректированный Y

        // Обновляем позицию объекта на основе скорректированных временных переменных
        vector.set(tempX, tempY);
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
