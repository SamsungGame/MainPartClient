package end.team.center.GameCore.Objects.OnMap;

import static end.team.center.Screens.Game.GameScreen.hero;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;

import java.util.Objects;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.Effects.OneSpriteEffect;
import end.team.center.GameCore.SuperAbilities.ShieldAbility;
import end.team.center.Screens.Game.GameScreen;

public class Ammo extends OneSpriteEffect {
    protected TextureRegion r;
    protected Hero hero;
    protected Enemy enemy;
    protected Rectangle bound;
    protected Vector2 position;
    protected Death die;

    // НОВОЕ: Вектор, хранящий ИСХОДНОЕ направление движения снаряда
    protected Vector2 initialDirection;

    private Polygon ammoPolygon;
    private float[] ammoVertices;

    public Ammo(Texture texture, TextureRegion r, Death die, Enemy enemy, Hero hero, int width, int height, float speed) {
        super(texture, width, height, speed);
        this.hero = hero;
        this.enemy = enemy;
        this.r = r;
        this.die = die;

        position = new Vector2(enemy.getCenterVector());
        bound = new Rectangle(position.x, position.y, width, height);
        Vector2 target = new Vector2(hero.getCenterVector().x, hero.getCenterVector().y);

        initialDirection = new Vector2(target).sub(position);
        if (initialDirection.len() > 0) { // Нормализуем, чтобы получить единичный вектор направления
            initialDirection.nor();
        } else {

            initialDirection.set(0, 1);
        }

        ammoVertices = new float[]{0, 0, width, 0, width, height, 0, height};
        ammoPolygon = new Polygon(ammoVertices);
        ammoPolygon.setOrigin(width / 2, height / 2); // Центр для вращения
    }

    protected void updateBound() {

        bound.x = getX();
        bound.y = getY();
        ammoPolygon.setPosition(getX(), getY());
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
        position.add(initialDirection.x * speed * delta, initialDirection.y * speed * delta);
        setPosition(position.x, position.y);


        float margin = Math.max(getWidth(), getHeight());
        if (position.y >= GameScreen.WORLD_HEIGHT + margin || position.y <= -margin ||
            position.x >= GameScreen.WORLD_WIDTH + margin || position.x <= -margin) {
            remove();
            return;
        }

        // --- Проверка столкновения с героем и щитом ---

        // Получаем Polygon щита. Он может быть null, если щит неактивен.
        Polygon shieldPolygon = null;
        if (hero.uniqueAbility instanceof ShieldAbility) {
            shieldPolygon = ((ShieldAbility) hero.uniqueAbility).getShieldPolygon();
        }

        // Проверяем столкновение сначала со щитом, если он есть и активен
        if (shieldPolygon != null && Intersector.overlapConvexPolygons(ammoPolygon, shieldPolygon)) {
            die.whoDie(this);
            remove();
            return;
        }

        // если герой тоже Rectangle. Если герой - Polygon, то использовать Intersector.overlapConvexPolygons)
        if (bound.overlaps(hero.getBound())) {
            hero.setHealth(hero.getHealth() - enemy.getDamage());
            die.whoDie(this);
            remove();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        // Здесь можно добавить dispose для текстуры снаряда, если она уникальна для каждого Ammo
        // r.getTexture().dispose(); // Если текстура управляется здесь
    }
}
