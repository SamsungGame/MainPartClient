package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.Effects.OneSpriteEffect;
import end.team.center.Screens.Game.GameScreen;

public class Ammo extends OneSpriteEffect {
    protected TextureRegion r;
    protected Hero hero;
    protected Enemy enemy;
    protected Rectangle bound;
    protected Vector2 position, target;
    protected Death die;

    public Ammo(Texture texture, TextureRegion r, Death die, Enemy enemy, Hero hero, int width, int height, float speed) {
        super(texture, width, height, speed);
        this.hero = hero;
        this.enemy = enemy;
        this.r = r;
        this.die = die;

        position = new Vector2(enemy.getCenterVector());
        bound = new Rectangle(position.x, position.y, width, height);
        target = new Vector2(hero.getCenterVector().x, hero.getCenterVector().y);
    }

    protected void updateBound() {
        bound.x = getX();
        bound.y = getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(r, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateBound();

        if (position.y >= GameScreen.WORLD_HEIGHT || position.y <= 0 || position.x >= GameScreen.WORLD_WIDTH || position.x <= 0) {
            remove();
        } else {
            Vector2 fixTarget = new Vector2(target.x, target.y);
            Vector2 fixPosition = new Vector2(position.x, position.y);

            Vector2 direction = fixTarget.sub(fixPosition);

            // Нормализуем вектор, чтобы сделать его единичной длины
            if (direction.len() > 0) direction.nor(); // Нормализуем

            // Обновляем позицию врага
            position.add(direction.scl(speed * delta));
            setPosition(position.x, position.y);
        }

        if (bound.overlaps(hero.getBound())) {
            hero.setHealth(hero.getHealth() - enemy.getDamage());
            die.whoDie(this);
            remove();
        } else if (bound.overlaps(new Rectangle(target.x, target.y, 1, 1))) {
            die.whoDie(this);
            remove();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
