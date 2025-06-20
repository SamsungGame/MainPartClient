package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.Gdx;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class ProjectionAbility  implements HeroAbility {

    private Hero hero;
    private boolean active;
    private float duration = 3.0f;
    private float elapsedTime = 0;
    private float cooldown = 0;
    private final float MAX_COOLDOWN = 5f;
    private float beforeSpeed;
    private float afterSpeed;

    public ProjectionAbility (Hero hero) {
        this.hero = hero;
        this.active = false;

        this.beforeSpeed = hero.getSpeed();
        this.afterSpeed = this.beforeSpeed * 3f;
    }

    @Override
    public void activate() {
        if (!active && cooldown <= 0) {
            active = true;
            elapsedTime = 0;

        }
    }

    @Override
    public void deactivate() {
        cooldown = MAX_COOLDOWN;
        hero.setSpeed(beforeSpeed);
        active = false;
    }

    @Override
    public void update(float delta) {
        if (active) {
            elapsedTime += delta;
            hero.setSpeed(afterSpeed);

            if (elapsedTime >= duration) {
                deactivate();
            }
        }else cooldown -= delta;


    }

    @Override
    public boolean isActive() {
        return active;
    }
}
