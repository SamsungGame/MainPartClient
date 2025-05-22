package end.team.center.GameCore.Logic;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.Hero;

public class AI {
    public static Vector2 MoveToPlayer(Hero hero, Vector2 vector, float speed, float delta) {
        Vector2 target = new Vector2(hero.getVector().x, hero.getVector().y);
        Vector2 direction = target.sub(vector);

        // Нормализуем вектор, чтобы сделать его единичной длины
        if (direction.len() > 0) {
            direction.nor(); // Нормализуем

            // Обновляем позицию врага
            vector.add(direction.scl(speed * delta));
        }

        // Устанавливаем новую позицию врага
        return vector;
    }
}
