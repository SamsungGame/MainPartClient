package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import end.team.center.GameCore.Objects.OnMap.Hero;

public interface HeroAbility {
    void activate();
    void deactivate();
    void update(float delta);
    boolean isActive();
    void draw(Batch batch);
}
