package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Animations.CharacterAnimation;
import end.team.center.Screens.Game.GameScreen;

public class Enemy extends Entity {
    private static final boolean STAN_IS_STOP_MOB = false;

    protected int level;
    protected boolean stan = false;
    protected float timeToReload = 0;
    protected float timePlayerInvulnerability = 0.5f;

    public Enemy(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, float worldHeight, float worldWidth) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
        this.level = level;
    }


    public void attack(Hero hero) {
        if (!hero.isInvulnerability && !stan) {
            hero.health--;
            hero.frameInvulnerability(timePlayerInvulnerability);

            stan = true;
            timeToReload = 0;
        }
    }

    @Override
    public void move(float deltaX, float deltaY, float delta, boolean isMob) {
        if (!stan && STAN_IS_STOP_MOB) super.move(deltaX, deltaY, delta, isMob);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (stan) {
            timeToReload += delta;

            if (timeToReload >= 1.5f) stan = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
