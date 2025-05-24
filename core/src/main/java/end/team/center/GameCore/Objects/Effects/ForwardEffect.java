package end.team.center.GameCore.Objects.Effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class ForwardEffect extends OneSpriteEffect {
    protected Vector2 target, pos;
    protected boolean start = false;
    protected float elapsedTime = 0, duration;
    protected Death die;
    public ForwardEffect(Texture texture, int width, int height, float speed, float duration) {
        super(texture, width, height, speed);
        this.duration = duration;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void go(Death die, Vector2 pos, Vector2 target, float degrees) {
        start = true;
        pos.y -= getHeight() / 2;
        pos.x -= getWidth() / 2;
        this.pos = pos;
        this.target = target;
        this.die = die;

        setOrigin(getWidth() / 2, getHeight() / 2);
        setRotation(degrees);

        setPosition(pos.x, pos.y);

        System.out.println("Попало");
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (start) {
            elapsedTime += delta;

            if (elapsedTime >= duration) {
                die.die();
            } else {
                Vector2 fixTarget = new Vector2(target.x, target.y);
                Vector2 fixPosition = new Vector2(pos.x, pos.y);

                Vector2 direction = fixTarget.sub(fixPosition);

                // Нормализуем вектор, чтобы сделать его единичной длины
                if (direction.len() > 0) direction.nor(); // Нормализуем

                System.out.println("Вектор: " + direction.scl(speed * delta).x + "/" + direction.scl(speed * delta).y);

                // Обновляем позицию врага
                pos.add(direction.scl(speed * delta));
                setPosition(pos.x, pos.y);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
