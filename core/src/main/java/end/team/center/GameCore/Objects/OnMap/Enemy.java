package end.team.center.GameCore.Objects.OnMap;

import static end.team.center.Screens.Game.GameScreen.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Experience;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.Map.Tree;
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

    public void move(float deltaX, float deltaY, float delta) {
        if (isLive) {
            if (!stan && STAN_IS_STOP_MOB) {
                isTreeTouch = false;
                isMoving = deltaX != 0 || deltaY != 0;

                if (deltaX >= 0) {
                    mRight = true;
                } else if (deltaX < 0) {
                    mRight = false;
                }

                // Временные переменные для новой позиции, основываемся на текущем положении
                // Теперь vector всегда актуален благодаря updatePosition в GameObject
                float currentX = vector.x;
                float currentY = vector.y;

                // Рассчитываем предполагаемую новую позицию
                float nextX = currentX + deltaX;
                float nextY = currentY + deltaY;

                // === Логика столкновений с деревьями ===
                // Важно: эта логика работает с deltaX/deltaY,
                // а затем корректирует их, чтобы предотвратить проход сквозь дерево.
                // После корректировки мы будем использовать новые deltaX/deltaY для updatePosition.
                for (Tree t : hero.getChunk().getTrees()) {
                    Rectangle treeBound = t.getBound();

                    // Проверка столкновения по X
                    if (treeBound.overlaps(new Rectangle(currentX + deltaX, currentY, width, height))) {
                        deltaX = 0; // Отменяем движение по X
                        // Пробуем двигаться только по Y, если герой находится выше или ниже врага
                        if (hero.getVector().y > vector.y) deltaY = speed * delta;
                        else                               deltaY = -(speed * delta);
                        isTreeTouch = true;
                    }

                    // Проверка столкновения по Y (после возможной корректировки по X)
                    if (treeBound.overlaps(new Rectangle(currentX, currentY + deltaY, width, height))) {
                        deltaY = 0; // Отменяем движение по Y
                        // Пробуем двигаться только по X, если герой находится правее или левее врага
                        if (hero.getVector().x > vector.x) deltaX = speed * delta;
                        else                               deltaX = -(speed * delta);
                        isTreeTouch = true;
                    }
                }

                // После всех корректировок, вычисляем финальную новую позицию
                float finalX = currentX + deltaX;
                float finalY = currentY + deltaY;

                // Обновляем позицию объекта, используя централизованный метод
                // Теперь vector, Actor.x/y и bound будут гарантированно синхронизированы
                updatePosition(finalX, finalY); // <-- ВОТ ГЛАВНОЕ ИЗМЕНЕНИЕ!

                stateTime += delta;
            }
        }
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

    public void stan(int sec) {
        if (!stan) {
            stan = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(sec * 1000L);
                    } catch (InterruptedException ignore) {}

                    stan = false;
                }
            }).start();
        }
    }
}
