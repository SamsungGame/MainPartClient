package end.team.center.GameCore.Objects.OnMap;

import static end.team.center.Screens.Game.GameScreen.hero;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon; // Импортируем Polygon
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector; // Импортируем Intersector

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
    protected Rectangle bound; // Bound для прямоугольного столкновения снаряда
    protected Vector2 position, target;
    protected Death die;

    // Добавляем Polygon для точного столкновения со снарядом, если он не круглый
    private Polygon ammoPolygon;
    private float[] ammoVertices; // Вершины для полигона снаряда

    public Ammo(Texture texture, TextureRegion r, Death die, Enemy enemy, Hero hero, int width, int height, float speed) {
        super(texture, width, height, speed);
        this.hero = hero;
        this.enemy = enemy;
        this.r = r;
        this.die = die;

        position = new Vector2(enemy.getCenterVector());
        bound = new Rectangle(position.x, position.y, width, height);
        target = new Vector2(hero.getCenterVector().x, hero.getCenterVector().y);

        // Инициализируем вершины для полигона снаряда
        ammoVertices = new float[]{0, 0, width, 0, width, height, 0, height};
        ammoPolygon = new Polygon(ammoVertices);
        // Устанавливаем опорную точку для вращения, если снаряд может вращаться
        ammoPolygon.setOrigin(width / 2, height / 2);
    }

    protected void updateBound() {
        // Обновляем позицию прямоугольного bound
        bound.x = getX();
        bound.y = getY();
        // Обновляем позицию полигона
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
        updateBound(); // Обновляем и прямоугольный bound, и ammoPolygon

        // Проверка выхода за границы мира
        if (position.y >= GameScreen.WORLD_HEIGHT || position.y <= 0 || position.x >= GameScreen.WORLD_WIDTH || position.x <= 0) {
            remove();
            return; // Важно выйти, чтобы не выполнять дальнейшую логику для удаленного снаряда
        }

        // Движение снаряда к цели
        Vector2 fixTarget = new Vector2(target.x, target.y);
        Vector2 fixPosition = new Vector2(position.x, position.y);

        Vector2 direction = fixTarget.sub(fixPosition);

        if (direction.len() > 0) direction.nor();

        position.add(direction.scl(speed * delta));
        setPosition(position.x, position.y);

        // Проверка столкновения с героем
        if (bound.overlaps(hero.getBound())) {
            hero.setHealth(hero.getHealth() - enemy.getDamage());
            die.whoDie(this); // Вызываем эффект смерти/исчезновения снаряда
            remove(); // Удаляем снаряд из сцены
            return; // Выходим после обработки столкновения
        }

        // --- НОВОЕ: Проверка столкновения со щитом ---
        Polygon shieldPolygon = null;
        if (hero.uniqueAbility instanceof ShieldAbility) {
            shieldPolygon = ((ShieldAbility) hero.uniqueAbility).getShieldPolygon();
        }

        if (shieldPolygon != null) { // Убедимся, что щит существует
            // Используем Intersector.overlapConvexPolygons для точной проверки
            if (Intersector.overlapConvexPolygons(ammoPolygon, shieldPolygon)) {
                die.whoDie(this); // Вызываем эффект смерти/исчезновения снаряда
                remove(); // Удаляем снаряд из сцены
                return;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
