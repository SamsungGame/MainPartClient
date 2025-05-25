package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Experience;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.Screens.Game.GameScreen;

public abstract class Enemy extends Entity {
    private static final boolean STAN_IS_STOP_MOB = true;

    protected AI ai;
    protected int level;
    protected boolean stan = false;
    protected float timeToReload = 0;
    protected float timePlayerInvulnerability = 1f;
    protected int damage, exp;

    public Enemy(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, int exp, float worldHeight, float worldWidth, AI ai) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
        this.level = level;
        this.ai = ai;
        this.damage = damage;
        this.exp = exp;
    }


    public void attack(Hero hero) {
        if (isLive) {
            if (!hero.isInvulnerability && !stan) {
                hero.health -= this.damage;
                hero.frameInvulnerability(timePlayerInvulnerability);

                timeToReload = 0;
            }
        }
    }

    public void move(float deltaX, float deltaY, float delta) {
        if (isLive) {
            if (!stan && STAN_IS_STOP_MOB) {
                isMoving = deltaX != 0 || deltaY != 0;

                // Проверка касания врагов
                for(Enemy e: GameScreen.enemies) {
                    if (e != this) {
                        if (e.getBound().overlaps(new Rectangle(vector.x + deltaX, vector.y + deltaY, width, height))) {
                            if (e.getBound().overlaps(new Rectangle(vector.x + deltaX, e.getBound().y, width, height))) deltaX = -deltaX;
                            if (e.getBound().overlaps(new Rectangle(e.getBound().x, vector.y + deltaY, width, height))) deltaY = -deltaY;
                        }
                    }
                }

                vector.add(new Vector2(deltaX, deltaY));
                setPosition(vector.x, vector.y);

                if (deltaX > 0) {
                    mRight = true;
                } else if (deltaX < 0) {
                    mRight = false;
                }

                updateBound();

                stateTime += delta;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (isLive) {
            super.act(delta);
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
