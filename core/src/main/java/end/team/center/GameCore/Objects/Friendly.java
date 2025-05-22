package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Friendly extends Entity {

    public Friendly(Texture rightTurn, Texture leftTurn, Vector2 vector, int health, int damage, int defence, float speed) {
        super(rightTurn, leftTurn, vector, health, damage, defence, speed);
    }

}
