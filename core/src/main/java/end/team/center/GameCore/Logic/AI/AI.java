package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.Hero;

public abstract class AI {
    protected Hero hero;

    public AI(Hero hero) {
        this.hero = hero;
    }
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        Vector2 fixTarget   = new Vector2(target.x, target.y);
        Vector2 fixPosition = new Vector2(position.x, position.y);

        Vector2 direction = fixTarget.sub(fixPosition);

        // Нормализуем вектор, чтобы сделать его единичной длины
        if (direction.len() > 0) direction.nor(); // Нормализуем

        // Обновляем позицию врага
        return direction.scl(speed * delta);
    }
}
