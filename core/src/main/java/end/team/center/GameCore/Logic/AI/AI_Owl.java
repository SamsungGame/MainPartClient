package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Mobs.Owl;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.Objects.OnMap.Enemy; // Импортируем Enemy, если еще не импортирован

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

    // НОВОЕ: Перегруженный конструктор, который принимает Enemy owner
    public AI_Owl(Hero hero, Enemy owner) {
        super(hero, owner); // Вызываем новый конструктор родительского класса AI
    }


    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        // Убедитесь, что aiOwner не null, прежде чем использовать его
        if (aiOwner == null || !(aiOwner instanceof Owl)) {
            // Это должно быть инициализировано в конструкторе или методе init
            // Для временного решения, если AI создан без владельца, можно его получить
            // Но лучше, чтобы Owl сам передавал себя при создании AI.
            Gdx.app.error("AI_Owl", "aiOwner is null or not an Owl!");
            return new Vector2(0,0); // Возвращаем нулевой вектор, чтобы избежать NRE
        }

        ((Owl)aiOwner).setAttackingOverrideRepulsion(false);


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
            ((Owl)aiOwner).setAttackingOverrideRepulsion(true);
            return new Vector2(0, 0);
        }

        // 3. Обработка активной фазы пикирования
        if (isDiveAttacking && isAttaking) {
            ((Owl)aiOwner).setAttackingOverrideRepulsion(true);

            if (!GMath.checkVectorDistance(position, lockAttack, 10, 10)) {
                return super.MoveToPlayer(lockAttack, position, speed * diveSpeedMultiplier, delta);
            } else {
                Gdx.app.log("AI_Owl", "Dive attack completed! Resetting state.");
                lockAttack = null;
                isAttaking = false;
                isDiveAttacking = false;
                isTimeGo = true;
                currentReloadTimer = timeToReloadDive;

                ((Owl)aiOwner).setAttackingOverrideRepulsion(false);

                return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
            }
        }

        // 4. Обычное поведение: сова движется к герою
        return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
    }

    public void diveAttack(Owl owl) {
        // Убедитесь, что aiOwner уже установлен или установите его здесь, если AI создается без owner
        if (this.aiOwner == null) { // Если AI был создан без owner, устанавливаем его
            this.aiOwner = owl;
        }

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
