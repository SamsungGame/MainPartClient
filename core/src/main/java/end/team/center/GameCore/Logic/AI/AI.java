package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.math.Vector2;
import end.team.center.GameCore.Objects.OnMap.Enemy; // Импортируем класс Enemy
import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class AI {
    protected Hero hero;
    protected Enemy aiOwner; // Добавлено: ссылка на объект Enemy, которым управляет этот AI

    public AI(Hero hero) {
        this.hero = hero;
    }

    // Добавлен новый конструктор для инициализации aiOwner
    public AI(Hero hero, Enemy aiOwner) {
        this.hero = hero;
        this.aiOwner = aiOwner;
    }

    /**
     * Вычисляет вектор движения к цели.
     * @param target Целевая позиция.
     * @param position Текущая позиция управляемого объекта.
     * @param speed Скорость движения.
     * @param delta Время, прошедшее с последнего кадра.
     * @return Вектор движения, который нужно применить к позиции.
     */
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        Vector2 fixTarget   = new Vector2(target.x, target.y);
        Vector2 fixPosition = new Vector2(position.x, position.y);

        Vector2 direction = fixTarget.sub(fixPosition);

        // Нормализуем вектор, чтобы сделать его единичной длины
        if (direction.len() > 0) direction.nor();

        // Обновляем позицию врага
        return direction.scl(speed * delta);
    }

    /**
     * Метод для обновления логики AI в каждом кадре.
     * Должен быть переопределен в дочерних классах для конкретной логики врага.
     * @param delta Время, прошедшее с последнего кадра.
     */
    public void update(float delta) {
        // Базовая реализация может быть пустой или содержать общую логику
    }

    public Hero getHero() {
        return hero;
    }

    // Добавлены геттеры и сеттеры для aiOwner, если они понадобятся
    public Enemy getAiOwner() {
        return aiOwner;
    }

    public void setAiOwner(Enemy aiOwner) {
        this.aiOwner = aiOwner;
    }
}
