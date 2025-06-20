package end.team.center.GameCore.SuperAbilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import end.team.center.GameCore.Objects.OnMap.Hero;

public interface HeroAbility {
    void activate();
    void deactivate();
    void update(float delta); // Метод для обновления состояния способности каждый кадр
    boolean isActive(); // Проверяет, активна ли способность в данный момент
    // void draw(Batch batch); // Если способность имеет свою отрисовку (например, визуальный эффект)
}
