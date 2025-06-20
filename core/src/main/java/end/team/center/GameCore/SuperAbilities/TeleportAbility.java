package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList; // Не забудьте импортировать ArrayList

import end.team.center.GameCore.Objects.OnMap.Enemy;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.Logic.GMath; // Нужен для GMath.checkVectorDistance
import end.team.center.Screens.Game.GameScreen; // Нужен для доступа к списку врагов

public class TeleportAbility implements HeroAbility {

    private Hero hero;
    private boolean active;
    private float cooldown = 0;
    private final float MAX_COOLDOWN = 5f;
    private Enemy targetEnemy; // Сохраняем ссылку на врага, к которому телепортируемся

    public TeleportAbility (Hero hero) {
        this.hero = hero;
        this.active = false;
    }

    @Override
    public void activate() {
        if (!active && cooldown <= 0) {
            // 1. Найти ближайшего врага
            targetEnemy = findNearestEnemy();

            if (targetEnemy != null) { // Если враг найден, активируем способность
                active = true;
                Gdx.app.log("Ability", "TeleportAbility активирована! Цель: " + targetEnemy.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void deactivate() {
        active = false;
        cooldown = MAX_COOLDOWN;
        Gdx.app.log("Ability", "TeleportAbility деактивирована. Кулдаун установлен: " + cooldown);
    }

    @Override
    public void update(float delta) {
        if (active) {
            if (targetEnemy != null && targetEnemy.isLive()) {
                hero.updatePosition(targetEnemy.getVector().x, targetEnemy.getVector().y);
                hero.updateBound();

                // ВАЖНО: Отключаем движение героя на короткое время после телепортации
                hero.setDisableMovement(true);

                targetEnemy.die();
                Gdx.app.log("Ability", "Телепортация к врагу и убийство: " + targetEnemy.getClass().getSimpleName());
            }

            deactivate(); // Деактивируем способность сразу после выполнения
        } else {
            if (cooldown > 0) {
                cooldown -= delta;
                if (cooldown < 0) {
                    cooldown = 0;
                    Gdx.app.log("Ability", "TeleportAbility: Кулдаун завершен!");
                }
            }
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public float getCooldown() {
        return cooldown;
    }

    private Enemy findNearestEnemy() {
        Enemy nearestEnemy = null;
        float minDistance = 500; // Инициализируем максимальным значением

        ArrayList<Enemy> currentEnemies = GameScreen.enemies;

        if (currentEnemies == null || currentEnemies.isEmpty()) {
            return null; // Нет врагов на карте
        }

        Vector2 heroCenter = hero.getCenterVector(); // Получаем центральную позицию героя

        for (Enemy enemy : currentEnemies) {
            if (enemy.isLive()) { // Учитываем только живых врагов
                Vector2 enemyCenter = enemy.getCenterVector(); // Получаем центральную позицию врага
                float distance = heroCenter.dst(enemyCenter); // Вычисляем дистанцию

                if (distance < minDistance) {
                    minDistance = distance;
                    nearestEnemy = enemy;
                }
            }
        }
        return nearestEnemy;
    }
}
