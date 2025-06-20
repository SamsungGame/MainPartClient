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

    // В классе Friendly.java

// ... (остальные поля и методы Friendly) ...

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

        // Временные переменные для новой позиции, инициализируем текущей позицией
        // Теперь current vector.x/y всегда актуален, благодаря updatePosition в GameObject
        float tempX = vector.x;
        float tempY = vector.y;

        // === Проверка и перемещение по X ===
        float newX = tempX + actualMoveX;
        // Ограничиваем позицию границами мира
        newX = Math.max(BOUNDARY_PADDING, Math.min(newX, worldWidth - BOUNDARY_PADDING - width));

        // Проверяем, в дереве ли игрок

        boolean inTree = false;
        for (Tree t: nowChunk.getTrees()) {
            if (bound.overlaps(t.getBound())) inTree = true;
        }

        if (!inTree) {
            Rectangle nextXBound = new Rectangle(newX, tempY, width, height); // Проверяем только изменение X
            boolean collidedX = false;

            if (nowChunk != null) {
                for (Tree t : nowChunk.getTrees()) {
                    Rectangle treeBound = t.getBound();
                    if (nextXBound.overlaps(treeBound)) {
                        collidedX = true;
                        // Если произошло столкновение по X, корректируем позицию X
                        if (actualMoveX > 0) { // Движение вправо
                            newX = vector.x; // Прижимаем к левой границе дерева
                        } else if (actualMoveX < 0) { // Движение влево
                            newX = vector.x; // Прижимаем к правой границе дерева
                        }
                        nextXBound.x = newX; // Обновляем для дальнейших проверок
                        break; // Столкновение найдено, выходим из цикла
                    }
                }
            }
            tempX = newX; // Применяем скорректированный X

            // === Проверка и перемещение по Y ===
            float newY = tempY + actualMoveY;
            // Ограничиваем позицию границами мира
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
                            newY = vector.y; // Прижимаем к нижней границе дерева
                        } else if (actualMoveY < 0) { // Движение вниз
                            newY = vector.y; // Прижимаем к верхней границе дерева
                        }
                        nextYBound.y = newY; // Обновляем для дальнейших проверок
                        break; // Столкновение найдено, выходим из цикла
                    }
                }
            }
            tempY = newY; // Применяем скорректированный Y
        } else {
            for (Tree t : nowChunk.getTrees()) {
                Rectangle treeBound = t.getBound();
                if (bound.overlaps(treeBound)) {
                    vector.x = t.getX() - getWidth();
                    vector.y = t.getY() - getHeight();
                }
            }
        }

        // Обновляем позицию объекта, используя централизованный метод
        // Теперь vector, Actor.x/y и bound будут гарантированно синхронизированы
        updatePosition(tempX, tempY);

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
