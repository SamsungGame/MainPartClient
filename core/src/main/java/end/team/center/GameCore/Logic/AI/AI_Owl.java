package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Mobs.Owl;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class AI_Owl extends AI {

    // Флаги состояния атаки совы (сохраняем старые названия для совместимости с Owl)
    public boolean isDiveAttacking = false; // Сова начала пикировать (идет к lockAttack)
    public boolean isAttaking = false;      // Сова находится в активной фазе атаки (после задержки перед пикированием)
    public boolean isTimeGo = false;        // Флаг для перезарядки (старое название timeToReloadDive)

    // Целевая точка для пикирования
    public Vector2 lockAttack;

    // Таймеры и параметры пикирования (сохраняем старые названия для совместимости с Owl)
    public float timeToDive = 1.0f; // Задержка перед началом пикирования (сова зависает)
    private float currentDiveDelayTimer = 0; // Внутренний таймер для timeToDive

    public float timeToReloadDive = 5.0f; // Длительность перезарядки пикирования
    private float currentReloadTimer = 0;  // Внутренний таймер для timeToReloadDive

    // Скорости
    private float normalSpeedMultiplier = 1.0f; // Множитель скорости для обычного движения
    private float diveSpeedMultiplier = 6.0f;   // Множитель скорости для пикирования (увеличиваем скорость)

    public AI_Owl(Hero hero) {
        super(hero);
    }

    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        // Логика таймеров и состояний должна быть в MoveToPlayer или отдельном методе updateAI
        // чтобы избежать использования Thread.sleep()

        // 1. Обработка перезарядки
        if (isTimeGo) { // Соответствует timeToReloadDive > 0 в старом коде
            currentReloadTimer -= delta;
            if (currentReloadTimer <= 0) {
                isTimeGo = false;
                timeToReloadDive = 0; // Сбрасываем внешний флаг для Owl
                currentReloadTimer = 0; // Сбрасываем внутренний таймер
            }
            // Во время перезарядки сова движется к герою с обычной скоростью
            return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
        }

        // 2. Обработка задержки перед пикированием
        if (isDiveAttacking && !isAttaking) { // isAttaking = false означает, что еще идет задержка
            currentDiveDelayTimer += delta;
            if (currentDiveDelayTimer >= timeToDive) {
                isAttaking = true; // Переход в фазу активной атаки
                currentDiveDelayTimer = 0; // Сброс таймера задержки
            }
            // Во время задержки сова не двигается сама
            return new Vector2(0, 0);
        }

        // 3. Обработка активной фазы пикирования
        if (isDiveAttacking && isAttaking) {
            // Проверяем, достигнута ли точка пикирования
            if (!GMath.checkVectorDistance(position, lockAttack, 50, 50)) { // 50 - пороговое расстояние для завершения пикирования
                // Сова еще не достигла цели, движемся к lockAttack с усиленной скоростью
                return super.MoveToPlayer(lockAttack, position, speed * diveSpeedMultiplier, delta);
            } else {
                // Цель пикирования достигнута, сбрасываем состояние
                lockAttack = null;
                isAttaking = false;      // Отключаем активную фазу
                isDiveAttacking = false; // Отключаем флаг пикирования
                isTimeGo = true;         // Включаем перезарядку
                currentReloadTimer = timeToReloadDive; // Устанавливаем таймер перезарядки
                // Продолжаем движение к герою с обычной скоростью
                return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
            }
        }

        // 4. Обычное поведение: сова движется к герою
        // Этот блок будет выполнен, если ни одно из вышеперечисленных условий не активно
        return super.MoveToPlayer(hero.getVector(), position, speed * normalSpeedMultiplier, delta);
    }

    /**
     * Попытка начать пикирующую атаку совы.
     * Вызывается из метода act() класса Owl.
     * @param owl Текущий объект совы, для доступа к ее кругам атаки.
     */
    public void diveAttack(Owl owl) {
        // Условия для начала пикирования:
        // 1. Герой находится в пределах начального круга атаки совы.
        // 2. Герой не находится в пределах конечного круга атаки совы (чтобы сова не пикировала, если герой уже под ней).
        // 3. Сова не находится в процессе пикирования (isDiveAttacking == false).
        // 4. Сова не находится на перезарядке (isTimeGo == false).
        if (GMath.circleRectangleOverlap(owl.getStartCircle(), hero.getBound()) &&
            !GMath.circleRectangleOverlap(owl.getEndCircle(), hero.getBound()) &&
            !isDiveAttacking && !isTimeGo) {

            isDiveAttacking = true; // Начинаем подготовку к пикированию
            isAttaking = false;      // Пока не активная фаза пикирования (идет задержка)
            currentDiveDelayTimer = 0; // Сброс таймера задержки
            lockAttack = new Vector2(hero.getVector().x, hero.getVector().y); // Захватываем текущую позицию героя

            // Gdx.app.log("AI_Owl", "Owl initiating dive attack towards: " + lockAttack); // Для отладки
        }
    }

    // Этот метод теперь не нужен, так как вся логика перезагрузки управляется внутренними таймерами AI_Owl
    // и флагом isTimeGo.
    /*
    public void handleReloadTimer(float delta) {
        if (isTimeGo) {
            currentReloadTimer -= delta;
            if (currentReloadTimer <= 0) {
                isTimeGo = false;
                timeToReloadDive = 0; // Сбрасываем, чтобы Owl знал, что перезарядка окончена
            }
        }
    }
    */
}
