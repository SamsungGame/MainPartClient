package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor {
    protected Texture texture;
    protected Rectangle bound;
    protected Vector2 vector; // Это ваше поле Vector2 для позиции
    protected float height, width;

    public GameObject(Texture texture, Vector2 vector, float height, float width) {
        this.texture = texture;
        // Важно: Инициализируем наш Vector2.
        // Если переданный Vector2 - это временный объект,
        // лучше создать новый, чтобы избежать неожиданных изменений извне.
        // Или убедиться, что передаваемый vector не будет изменяться где-то ещё.
        this.vector = new Vector2(vector.x, vector.y);

        this.width = width;
        this.height = height;

        // Устанавливаем начальную позицию, используя наш новый синхронизирующий метод
        updatePosition(this.vector.x, this.vector.y);
    }

    /**
     * Централизованный метод для обновления позиции объекта.
     * Он синхронизирует:
     * 1. Собственный Vector2 'vector'.
     * 2. Внутренние координаты Actor (x, y).
     * 3. Границы объекта (Rectangle 'bound').
     * Всегда используйте этот метод для изменения позиции GameObject.
     * @param x Новая координата X
     * @param y Новая координата Y
     */
    public void updatePosition(float x, float y) {
        // Обновляем наш Vector2
        this.vector.set(x, y);

        // Обновляем внутренние координаты LibGDX Actor
        // Это вызывает super.setX(x) и super.setY(y)
        super.setPosition(x, y);

        // Обновляем Rectangle границ
        if (bound == null) {
            bound = new Rectangle(x, y, width, height);
        } else {
            bound.set(x, y, width, height);
        }
    }

    // Переопределяем методы setPosition из Actor, чтобы они использовали наш updatePosition
    @Override
    public void setPosition(float x, float y) {
        updatePosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        updatePosition(x, y);
    }

    // Методы getX() и getY() наследуются от Actor и будут возвращать
    // правильные, синхронизированные координаты.

    public Vector2 getVector() {
        return vector; // Возвращаем ссылку на наш Vector2
    }

    public Vector2 getCenterVector() {
        // Используем наш vector для расчета центра
        return new Vector2(vector.x + width / 2, vector.y + height / 2);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public Rectangle getBound() {
        return bound; // bound всегда будет актуальным
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Базовая отрисовка Actor'а использует Actor.x и Actor.y, которые теперь синхронизированы
        super.draw(batch, parentAlpha);
    }

    public void dispose() {
        // Освобождение ресурсов, если необходимо
    }
}
