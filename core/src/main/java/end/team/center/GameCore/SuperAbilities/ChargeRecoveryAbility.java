package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.Gdx;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class ChargeRecoveryAbility implements HeroAbility {
    private Hero hero;
    private boolean active;
    private float duration = 5.0f; // Длительность восстановления (например, 5 секунд)
    private float recoveryAmountPerSecond = 5.0f; // Сколько восстанавливается в секунду
    private float cooldown = 0; // Перезарядка
    private final float MAX_COOLDOWN = 5f;
    private float elapsedTime = 0;

    public ChargeRecoveryAbility(Hero hero) {
        this.hero = hero;
        this.active = false;


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
        active = false;
    }

    @Override
    public void update(float delta) {
        if (active) {
            elapsedTime += delta;
            hero.addCostumePower(recoveryAmountPerSecond * delta);

            if (elapsedTime >= duration) {
                deactivate();
            }
        } else cooldown -= delta;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
