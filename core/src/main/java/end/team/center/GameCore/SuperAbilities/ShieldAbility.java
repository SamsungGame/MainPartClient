package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class ShieldAbility implements HeroAbility {
    private Hero hero;
    private boolean active;
    private float duration = 15.0f; // Длительность действия щита
    private float cooldown = 0; // Перезарядка способности
    private final float MAX_COOLDOWN = 10f; // Максимальное время перезарядки
    private float elapsedTime = 0; // Время, прошедшее с активации

    // Параметры отрисовки щита
    private Texture shieldTexture; // Текстура активного щита
    private float shieldWidth;    // Ширина текстуры палки-щита
    private float shieldHeight;   // Высота текстуры палки-щита
    private float rotationRadius; // Расстояние от ЦЕНТРА героя до ЦЕНТРА палки-щита
    private float currentAngle;   // Текущий угол вращения палки-щита вокруг героя (в градусах)
    private Polygon shieldPolygon;

    // Конструктор
    public ShieldAbility(Hero hero) {
            this.hero = hero;
            this.active = false;

            this.shieldTexture = new Texture(Gdx.files.internal("UI/GameUI/AbilityEffects/knightShield.png"));

            this.shieldWidth = 60;
            this.shieldHeight = 200;
            this.rotationRadius = 100;
            this.currentAngle = 0;

            // Инициализируем Polygon.
            // Вершины для прямоугольника с нижним левым углом в (0,0)
            float[] vertices = {
                0, 0,
                shieldWidth, 0,
                shieldWidth, shieldHeight,
                0, shieldHeight
            };
            this.shieldPolygon = new Polygon(vertices);
            // Устанавливаем Origin для вращения Polygon (центр палки)
            this.shieldPolygon.setOrigin(shieldWidth / 2, shieldHeight / 2);
        }


    @Override
    public void activate() {
        if (!active && cooldown <= 0) {
            active = true;
            elapsedTime = 0;
            Gdx.app.log("ShieldAbility", "Щит активирован!");
        }
    }

    @Override
    public void deactivate() {
        cooldown = MAX_COOLDOWN;
        active = false;
        Gdx.app.log("ShieldAbility", "Щит деактивирован!");
    }

    @Override
    public void update(float delta) {
        if (active) {
            elapsedTime += delta;

            float inputX = GameScreen.touchpadMove.getKnobPercentX();
            float inputY = GameScreen.touchpadMove.getKnobPercentY();

            float deadZone = 0.1f;
            if (Math.abs(inputX) > deadZone || Math.abs(inputY) > deadZone) {
                float angleRad = MathUtils.atan2(inputY, inputX);
                currentAngle = angleRad * MathUtils.radiansToDegrees;
                // Не забудьте про возможную корректировку currentAngle,
                // если текстура щита нарисована не так, как ожидается по умолчанию.
                // Например, currentAngle -= 90;
            }

            // --- ОБНОВЛЕНИЕ ПОЛИГОНА ЩИТА ЗДЕСЬ ---
            float heroCenterX = hero.getX() + hero.getWidth() / 2f;
            float heroCenterY = hero.getY() + hero.getHeight() / 2f;

            float shieldCenterX = heroCenterX + rotationRadius * MathUtils.cosDeg(currentAngle);
            float shieldCenterY = heroCenterY + rotationRadius * MathUtils.sinDeg(currentAngle);

            // Переводим центральную точку щита в координаты его нижнего левого угла
            float shieldDrawX = shieldCenterX - shieldWidth / 2;
            float shieldDrawY = shieldCenterY - shieldHeight / 2;

            // Обновляем позицию и вращение Polygon
            this.shieldPolygon.setPosition(shieldDrawX, shieldDrawY);
            this.shieldPolygon.setRotation(currentAngle); // Устанавливаем вращение для Polygon

            if (elapsedTime >= duration) {
                deactivate();
            }
        } else {
            if (cooldown > 0) {
                cooldown -= delta;
                if (cooldown < 0) {
                    cooldown = 0;
                }
            }
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void draw(Batch batch) {
        if (active) {
            // Координаты для отрисовки берутся из shieldPolygon, но для batch.draw
            // нам нужны те же расчеты, что и для setPosition Polygon.
            // Эти переменные уже рассчитаны в update, но можно пересчитать для clarity
            float heroCenterX = hero.getX() + hero.getWidth() / 2f;
            float heroCenterY = hero.getY() + hero.getHeight() / 2f;
            float shieldCenterX = heroCenterX + rotationRadius * MathUtils.cosDeg(currentAngle);
            float shieldCenterY = heroCenterY + rotationRadius * MathUtils.sinDeg(currentAngle);
            float shieldDrawX = shieldCenterX - shieldWidth / 2;
            float shieldDrawY = shieldCenterY - shieldHeight / 2;

            batch.draw(shieldTexture,
                shieldDrawX, shieldDrawY,         // Координаты нижнего левого угла для отрисовки
                shieldWidth / 2, shieldHeight / 2, // OriginX, OriginY (центр самой палки-щита для вращения)
                shieldWidth, shieldHeight,         // Ширина и высота текстуры
                1, 1,                               // ScaleX, ScaleY
                currentAngle,                       // Угол вращения (тот же угол, что и для вращения вокруг героя)
                0, 0,                               // srcX, srcY
                shieldTexture.getWidth(), shieldTexture.getHeight(),
                false, false);                      // FlipX, FlipY
        }
    }

    // --- Новый метод для получения Polygon щита ---
    public Polygon getShieldPolygon() {
        // Возвращаем null, если щит неактивен, чтобы не проверять столкновения с невидимым щитом
        return active ? shieldPolygon : null;
    }
}
