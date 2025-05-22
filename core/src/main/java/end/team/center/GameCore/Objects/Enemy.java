package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.Screens.Game.GameScreen;

public class Enemy extends Entity {
    protected int level;
    private Hero hero;

    public Enemy(Texture rightTurn, Texture leftTurn, Vector2 vector, int health, int damage, int defence, float speed, int level) {
        super(rightTurn, leftTurn, vector, health, damage, defence, speed);
        this.level = level;
    }


    public void attack() {

    }

    @Override
    public void move(float delta) {
        super.move(delta);

        Vector2 direction = hero.vector.sub(vector);

        // Нормализуем вектор, чтобы сделать его единичной длины
        if (direction.len() > 0) {
            direction.nor(); // Нормализуем

            // Обновляем позицию врага
            vector.add(direction.scl(speed * delta));
        }

        // Устанавливаем новую позицию врага
        setPosition(vector.x, vector.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
