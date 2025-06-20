package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.Enemy;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class TeleportAbility implements HeroAbility {

    private Hero hero;
    private Enemy enemy;
    private boolean active;
    private float cooldown = 0;
    private Vector2 localEnemy;

    public TeleportAbility (Hero hero, Enemy enemy) {
        this.hero = hero;
        this.enemy = enemy;
        this.active = false;

        this.localEnemy = enemy.getCenterVector();
    }

    @Override
    public void activate() {
        if (!active && cooldown <= 0) {
            active = true;
        }
    }

    @Override
    public void deactivate() {
        cooldown = 5f;
        active = false;
    }

    @Override
    public void update(float delta) {
        if (active) {

            deactivate();
        }else cooldown -= delta;


    }

    @Override
    public boolean isActive() {
        return active;
    }
}
