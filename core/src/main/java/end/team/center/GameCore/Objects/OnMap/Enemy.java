package end.team.center.GameCore.Objects.OnMap;

import static end.team.center.Screens.Game.GameScreen.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Experience;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.Map.Tree;
import end.team.center.GameCore.SuperAbilities.ShieldAbility;
import end.team.center.Screens.Game.GameScreen;

public abstract class Enemy extends Entity {
    private static final boolean STAN_IS_STOP_MOB = true;

    protected AI ai;
    protected int level;
    protected boolean stan = false, isTreeTouch = false;
    protected float timeToReload = 0;
    protected float timePlayerInvulnerability = 1f;
    protected int damage, exp;
    protected float runSpeed;
    Sound soundUron = Gdx.audio.newSound(Gdx.files.internal("Sounds/udar.mp3"));

    private boolean isBeingPushed = false;
    private Vector2 pushDirection = new Vector2();
    private float pushSpeed = 300f;
    private float pushDuration = 0.15f;
    private float currentPushTime = 0;

    // --- НОВОЕ/ИЗМЕНЕНО: Переменные для отталкивания от других врагов ---
    private float enemyRepulsionForce = 200f; // Сила отталкивания от других врагов (меньше, чем pushSpeed)
    private float enemyRepulsionDuration = 0.05f; // Длительность отталкивания от других врагов
    private float currentEnemyRepulsionTime = 0;
    private boolean isBeingRepelledByOtherEnemy = false;
    // --- КОНЕЦ НОВОГО/ИЗМЕНЕНО ---


    public Enemy(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, int exp, float worldHeight, float worldWidth, AI ai) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
        this.level = level;
        this.ai = ai;
        this.damage = damage;
        this.exp = exp;

        runSpeed = speed * 8;

        bound = new Rectangle((float) (vector.x + (width * 0.5) / 2),
            (float) (vector.y + (height * 0.5) / 2), (float) (width * 0.5), (float) (height * 0.5));
    }


    public void attack(Hero hero) {
        if (isLive) {
            if (!hero.isInvulnerability && !stan) {
                hero.health -= this.damage;

                long id = soundUron.play();
                soundUron.setVolume(id, 0.5f);

                hero.frameInvulnerability(timePlayerInvulnerability);

                timeToReload = 0;
            }
        }
    }


    // Метод для отталкивания от щита
    public void push(Vector2 direction) {
        // Если уже отталкиваемся от другого врага, отменяем это
        isBeingRepelledByOtherEnemy = false;
        currentEnemyRepulsionTime = 0;

        isBeingPushed = true;
        pushDirection.set(direction).nor();
        currentPushTime = pushDuration;
        // Стан при отталкивании щитом сохраняем, так как это более сильный эффект
        stan((int) (pushDuration * 1000L));
        Gdx.app.log("Enemy", "Enemy Pushed by Shield! Direction: " + pushDirection.x + ", " + pushDirection.y);
    }

    // --- НОВОЕ: Метод для отталкивания от других врагов ---
    public void repelFromEnemy(Vector2 direction) {
        // Отталкиваемся, только если не находимся в сильном "пуше" от щита
        if (!isBeingPushed) {
            isBeingRepelledByOtherEnemy = true;
            pushDirection.set(direction).nor(); // Переиспользуем pushDirection
            currentEnemyRepulsionTime = enemyRepulsionDuration;
            // Можно добавить очень короткий стан, если нужно полностью остановить движение
            // stan((int) (enemyRepulsionDuration * 1000L));
        }
    }
    // --- КОНЕЦ НОВОГО ---


    public void move(float targetDeltaX, float targetDeltaY, float delta) {
        if (!isLive) {
            return;
        }

        // Определяем полигон врага для столкновений
        float[] enemyVertices = {0, 0, width, 0, width, height, 0, height};
        Polygon enemyPolygon = new Polygon(enemyVertices);
        enemyPolygon.setOrigin(width / 2, height / 2);

        Polygon shieldPolygon = null;
        if (hero.uniqueAbility instanceof ShieldAbility) {
            shieldPolygon = ((ShieldAbility) hero.uniqueAbility).getShieldPolygon();
        }


        // Обработка отталкивания от щита
        if (isBeingPushed) {
            float pushMoveX = pushDirection.x * pushSpeed * delta;
            float pushMoveY = pushDirection.y * pushSpeed * delta;

            // Проверяем столкновения для движения от щита
            if (!checkCollisionAt(vector.x + pushMoveX, vector.y + pushMoveY, enemyPolygon, shieldPolygon, false)) { // Передаем false, чтобы не вызывать push рекурсивно
                updatePosition(vector.x + pushMoveX, vector.y + pushMoveY);
            } else {
                // Если заблокировано, останавливаем push
                currentPushTime = 0;
            }

            currentPushTime -= delta;
            if (currentPushTime <= 0) {
                isBeingPushed = false;
            }
            return; // Не двигаемся в других направлениях, пока отталкиваемся щитом
        }

        // Обработка отталкивания от других врагов
        if (isBeingRepelledByOtherEnemy) {
            float repelMoveX = pushDirection.x * enemyRepulsionForce * delta;
            float repelMoveY = pushDirection.y * enemyRepulsionForce * delta;

            // Проверяем столкновения для движения от других врагов
            if (!checkCollisionAt(vector.x + repelMoveX, vector.y + repelMoveY, enemyPolygon, shieldPolygon, false)) { // Передаем false
                updatePosition(vector.x + repelMoveX, vector.y + repelMoveY);
            } else {
                // Если заблокировано, останавливаем отталкивание
                currentEnemyRepulsionTime = 0;
            }

            currentEnemyRepulsionTime -= delta;
            if (currentEnemyRepulsionTime <= 0) {
                isBeingRepelledByOtherEnemy = false;
            }
            return;
        }


        // Стан полностью останавливает моба, если STAN_IS_STOP_MOB = true
        if (stan && STAN_IS_STOP_MOB) {
            return;
        }

        isTreeTouch = false;
        isMoving = targetDeltaX != 0 || targetDeltaY != 0;

        if (targetDeltaX >= 0) {
            mRight = true;
        } else {
            mRight = false;
        }

        float currentX = vector.x;
        float currentY = vector.y;

        float actualDeltaX = targetDeltaX;
        float actualDeltaY = targetDeltaY;

        enemyPolygon.setPosition(currentX, currentY); // Устанавливаем текущую позицию


        // --- ОТТАЛКИВАНИЕ ОТ ДРУГИХ ВРАГОВ НА ОСНОВЕ СТОЛКНОВЕНИЙ ---
        if (GameScreen.enemies != null) {
            for (Enemy otherEnemy : GameScreen.enemies) {
                if (otherEnemy != this && otherEnemy.isLive()) {
                    Polygon otherEnemyPolygon = new Polygon(enemyVertices); // Создаем полигон для другого врага
                    otherEnemyPolygon.setOrigin(otherEnemy.width / 2, otherEnemy.height / 2);
                    otherEnemyPolygon.setPosition(otherEnemy.vector.x, otherEnemy.vector.y);

                    if (Intersector.overlapConvexPolygons(enemyPolygon, otherEnemyPolygon)) {
                        Vector2 thisCenter = getCenterVector();
                        Vector2 otherCenter = otherEnemy.getCenterVector();
                        Vector2 repulsionDir = thisCenter.cpy().sub(otherCenter).nor();
                        repelFromEnemy(repulsionDir);
                        // Поскольку мы начали отталкивание, в текущем кадре не даем врагу двигаться
                        // по своему AI, чтобы избежать застревания.
                        return;
                    }
                }
            }
        }
        // --- КОНЕЦ ОТТАЛКИВАНИЯ ОТ ДРУГИХ ВРАГОВ ---


        // ПРОВЕРКА СТОЛКНОВЕНИЙ ДЛЯ AI ДВИЖЕНИЯ
        // Сначала проверяем потенциальное движение по X
        float potentialX = currentX + actualDeltaX;
        if (checkCollisionAt(potentialX, currentY, enemyPolygon, shieldPolygon, true)) { // Передаем true, чтобы вызывать push для щита
            actualDeltaX = 0;
        }

        // Затем проверяем потенциальное движение по Y
        float potentialY = currentY + actualDeltaY;
        if (checkCollisionAt(currentX, potentialY, enemyPolygon, shieldPolygon, true)) { // Передаем true
            actualDeltaY = 0;
        }

        // Если движение было заблокировано по одной из осей, но не по другой,
        // проверяем движение по диагонали еще раз
        if (actualDeltaX != 0 && actualDeltaY != 0) {
            if (checkCollisionAt(currentX + actualDeltaX, currentY + actualDeltaY, enemyPolygon, shieldPolygon, true)) { // Передаем true
                actualDeltaX = 0;
                actualDeltaY = 0;
            }
        }

        // Обновляем позицию только после всех проверок
        float finalX = currentX + actualDeltaX;
        float finalY = currentY + actualDeltaY;

        updatePosition(finalX, finalY);
        stateTime += delta;
    }


    private boolean checkCollisionAt(float nextX, float nextY, Polygon enemyPolygon, Polygon shieldPolygon, boolean canPushByShield) {
        Rectangle enemyRectForTreeCheck = new Rectangle(nextX, nextY, width, height);
        for (Tree t : hero.getChunk().getTrees()) {
            if (t.getBound().overlaps(enemyRectForTreeCheck)) {
//                isTreeTouch = true;
                return true; // Столкновение с деревом обнаружено
            }
        }

        if (shieldPolygon != null && canPushByShield) { // Проверяем флаг canPushByShield
            enemyPolygon.setPosition(nextX, nextY);
            if (Intersector.overlapConvexPolygons(enemyPolygon, shieldPolygon)) {
                Vector2 shieldCenter = new Vector2();
                shieldPolygon.getBoundingRectangle().getCenter(shieldCenter);

                Vector2 enemyCenter = new Vector2();
                enemyPolygon.getBoundingRectangle().getCenter(enemyCenter);

                Vector2 pushDirection = enemyCenter.cpy().sub(shieldCenter);
                push(pushDirection); // Вызываем push, только если canPushByShield == true
                return true; // Столкновение со щитом обнаружено
            }
        }
        return false; // Столкновений не обнаружено
    }


    @Override
    public void act(float delta) {
        if (isLive) {
            super.act(delta);

            if (!GMath.checkVectorDistance(ai.getHero().getCenterVector(), getCenterVector(), 800, 800)) {
                speed = runSpeed;
            } else {
                speed = runSpeed / 8;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isLive) {
            super.draw(batch, parentAlpha);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void die() {
        isLive = false;
    }

    public int getExp() {
        return exp;
    }

    public boolean isLive() {
        return isLive;
    }

    public void stan(int ms) { // Изменил параметр на миллисекунды, чтобы быть более точным
        if (!stan) {
            stan = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(ms);
                    } catch (InterruptedException ignore) {}

                    stan = false;
                }
            }).start();
        }
    }
}
