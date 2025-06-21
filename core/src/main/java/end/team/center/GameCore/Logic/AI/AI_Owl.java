// AI_Owl.java
package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Mobs.Owl;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class AI_Owl extends AI {

    public boolean isDiveAttacking = false;
    public boolean isAttaking = false;
    public boolean isTimeGo = false;

    public Vector2 lockAttack;

    public float timeToDive = 1.0f;
    private float currentDiveDelayTimer = 0;

    public float timeToReloadDive = 5.0f;
    private float currentReloadTimer = 0;

    private float normalSpeedMultiplier = 1.0f;
    private float diveSpeedMultiplier = 6.0f;

    public AI_Owl(Hero hero) {
        super(hero);
    }

    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        // 1. Обработка перезарядки
        if (isTimeGo) {
            currentReloadTimer -= delta;
            if (currentReloadTimer <= 0) {
                isTimeGo = false;
                currentReloadTimer = 0;
            }
            return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
        }

        // 2. Обработка задержки перед пикированием
        if (isDiveAttacking && !isAttaking) {
            currentDiveDelayTimer += delta;
            if (currentDiveDelayTimer >= timeToDive) {
                isAttaking = true;
                currentDiveDelayTimer = 0;
                Gdx.app.log("AI_Owl", "Dive active! Moving to: " + lockAttack);
            }
            return new Vector2(0, 0);
        }

        // 3. Обработка активной фазы пикирования
        if (isDiveAttacking && isAttaking) {
            if (!GMath.checkVectorDistance(position, lockAttack, 10, 10)) {
                return super.MoveToPlayer(lockAttack, position, speed * diveSpeedMultiplier, delta);
            } else {
                // Цель пикирования достигнута
                resetDiveState(); // Используем новый метод для сброса состояния
                Gdx.app.log("AI_Owl", "Dive attack completed! Resetting state.");
                return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
            }
        }

        // 4. Обычное поведение
        return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
    }

    /**
     * Сбрасывает состояние пикирующей атаки совы.
     * Может быть вызвана извне (например, при столкновении со щитом).
     */
    public void resetDiveState() {
        lockAttack = null;
        isAttaking = false;
        isDiveAttacking = false;
        isTimeGo = true; // Включаем перезарядку после сброса
        currentReloadTimer = timeToReloadDive;
    }

    public void diveAttack(Owl owl) {
        if (GMath.circleRectangleOverlap(owl.getStartCircle(), hero.getBound()) &&
            !GMath.circleRectangleOverlap(owl.getEndCircle(), hero.getBound()) &&
            !isDiveAttacking && !isTimeGo) {

            isDiveAttacking = true;
            isAttaking = false;
            currentDiveDelayTimer = 0;
            lockAttack = new Vector2(hero.getVector().x, hero.getVector().y);
            Gdx.app.log("AI_Owl", "Owl initiating dive attack towards: " + lockAttack);
        }
    }
}
