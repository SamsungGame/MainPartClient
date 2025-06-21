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
    protected boolean stan = false;
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

    // --- Переменные для отталкивания от других врагов ---
    private float enemyRepulsionForce = 200f;
    private float enemyRepulsionDuration = 0.05f;
    private float currentEnemyRepulsionTime = 0;
    private boolean isBeingRepelledByOtherEnemy = false;

    // --- НОВОЕ: Флаг для игнорирования отталкивания от других врагов ---
    protected boolean isAttackingOverrideRepulsion = false;


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


    public void push(Vector2 direction) {
        isBeingRepelledByOtherEnemy = false;
        currentEnemyRepulsionTime = 0;

        isBeingPushed = true;
        pushDirection.set(direction).nor();
        currentPushTime = pushDuration;
        stan((int) (pushDuration * 1000L));
        Gdx.app.log("Enemy", "Enemy Pushed by Shield! Direction: " + pushDirection.x + ", " + pushDirection.y);
    }

    public void repelFromEnemy(Vector2 direction) {
        if (!isBeingPushed && !isAttackingOverrideRepulsion) { // Не отталкиваемся, если атакуем или пушимся щитом
            isBeingRepelledByOtherEnemy = true;
            pushDirection.set(direction).nor();
            currentEnemyRepulsionTime = enemyRepulsionDuration;
        }
    }

    // --- НОВОЕ: Геттер и сеттер для флага игнорирования отталкивания ---
    public void setAttackingOverrideRepulsion(boolean override) {
        this.isAttackingOverrideRepulsion = override;
    }
    // --- КОНЕЦ НОВОГО ---


    public void move(float targetDeltaX, float targetDeltaY, float delta) {
        if (!isLive) {
            return;
        }

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

            if (!checkCollisionAt(vector.x + pushMoveX, vector.y + pushMoveY, enemyPolygon, shieldPolygon, false)) {
                updatePosition(vector.x + pushMoveX, vector.y + pushMoveY);
            } else {
                currentPushTime = 0;
            }

            currentPushTime -= delta;
            if (currentPushTime <= 0) {
                isBeingPushed = false;
            }
            return;
        }

        // Обработка отталкивания от других врагов
        if (isBeingRepelledByOtherEnemy) {
            float repelMoveX = pushDirection.x * enemyRepulsionForce * delta;
            float repelMoveY = pushDirection.y * enemyRepulsionForce * delta;

            if (!checkCollisionAt(vector.x + repelMoveX, vector.y + repelMoveY, enemyPolygon, shieldPolygon, false)) {
                updatePosition(vector.x + repelMoveX, vector.y + repelMoveY);
            } else {
                currentEnemyRepulsionTime = 0;
            }

            currentEnemyRepulsionTime -= delta;
            if (currentEnemyRepulsionTime <= 0) {
                isBeingRepelledByOtherEnemy = false;
            }
            // Здесь мы НЕ делаем return; чтобы позволить врагу двигаться по своему AI,
            // если его движение не полностью заблокировано отталкиванием.
            // Однако, если вы хотите, чтобы отталкивание полностью перехватывало контроль,
            // верните return;
            // Для совы, которая должна пикировать, нам нужно, чтобы она могла продолжать движение,
            // даже если ее немного оттолкнуло, поэтому пока оставим без return.
            // Если ее оттолкнуло в дерево, currentEnemyRepulsionTime уже будет сброшен.
            // Продолжаем к AI-движению ниже.
        }


        if (stan && STAN_IS_STOP_MOB) {
            return;
        }

        // isTreeTouch = false; // Эта строка не нужна, так как isTreeTouch устанавливается в checkCollisionAt
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

        enemyPolygon.setPosition(currentX, currentY);


        // --- ОТТАЛКИВАНИЕ ОТ ДРУГИХ ВРАГОВ НА ОСНОВЕ СТОЛКНОВЕНИЙ ---
        // Этот блок теперь будет запускать отталкивание, только если isAttackingOverrideRepulsion = false
        if (GameScreen.enemies != null && !isAttackingOverrideRepulsion) {
            for (Enemy otherEnemy : GameScreen.enemies) {
                if (otherEnemy != this && otherEnemy.isLive()) {
                    Polygon otherEnemyPolygon = new Polygon(enemyVertices);
                    otherEnemyPolygon.setOrigin(otherEnemy.width / 2, otherEnemy.height / 2);
                    otherEnemyPolygon.setPosition(otherEnemy.vector.x, otherEnemy.vector.y);

                    if (Intersector.overlapConvexPolygons(enemyPolygon, otherEnemyPolygon)) {
                        Vector2 thisCenter = getCenterVector();
                        Vector2 otherCenter = otherEnemy.getCenterVector();
                        Vector2 repulsionDir = thisCenter.cpy().sub(otherCenter).nor();
                        repelFromEnemy(repulsionDir);
                        // Здесь мы не используем return; так как `repelFromEnemy` устанавливает флаг
                        // `isBeingRepelledByOtherEnemy`, который обрабатывается в начале метода.
                        // Это позволяет отталкиванию быть более "мягким" и не прерывать сразу AI-движение,
                        // если оно не полностью заблокировано.
                    }
                }
            }
        }
        // --- КОНЕЦ ОТТАЛКИВАНИЯ ОТ ДРУГИХ ВРАГОВ ---


        // ПРОВЕРКА СТОЛКНОВЕНИЙ ДЛЯ AI ДВИЖЕНИЯ
        // Сначала проверяем потенциальное движение по X
        float potentialX = currentX + actualDeltaX;
        if (checkCollisionAt(potentialX, currentY, enemyPolygon, shieldPolygon, true)) {
            actualDeltaX = 0;
        }

        // Затем проверяем потенциальное движение по Y
        float potentialY = currentY + actualDeltaY;
        if (checkCollisionAt(currentX, potentialY, enemyPolygon, shieldPolygon, true)) {
            actualDeltaY = 0;
        }

        // Если движение было заблокировано по одной из осей, но не по другой,
        // проверяем движение по диагонали еще раз
        if (actualDeltaX != 0 && actualDeltaY != 0) {
            if (checkCollisionAt(currentX + actualDeltaX, currentY + actualDeltaY, enemyPolygon, shieldPolygon, true)) {
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
                // isTreeTouch = true; // Эта строка не нужна, так как isTreeTouch нигде не используется после этого.
                return true;
            }
        }

        if (shieldPolygon != null && canPushByShield) {
            enemyPolygon.setPosition(nextX, nextY);
            if (Intersector.overlapConvexPolygons(enemyPolygon, shieldPolygon)) {
                Vector2 shieldCenter = new Vector2();
                shieldPolygon.getBoundingRectangle().getCenter(shieldCenter);

                Vector2 enemyCenter = new Vector2();
                enemyPolygon.getBoundingRectangle().getCenter(enemyCenter);

                Vector2 pushDirection = enemyCenter.cpy().sub(shieldCenter);
                push(pushDirection);
                return true;
            }
        }
        return false;
    }


    @Override
    public void act(float delta) {
        if (isLive) {
            super.act(delta);

            // Убрал этот блок из-за совы, она регулирует скорость через свой AI
            // if (!GMath.checkVectorDistance(ai.getHero().getCenterVector(), getCenterVector(), 800, 800)) {
            //     speed = runSpeed;
            // } else {
            //     speed = runSpeed / 8;
            // }
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

    public void stan(int ms) {
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
