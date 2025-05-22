package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Objects.Enemy;

public class ForFrog extends Enemy {

    public ForFrog(Texture rightTurn, Texture leftTurn, Vector2 vector, int health, int damage, int defence, float speed, int level) {
        super(rightTurn, leftTurn, vector, health, damage, defence, speed, level);
        initialization();
    }

    public ForFrog(EnemyType type, Vector2 vector) {
        super(type.getRightTurn(), type.getLeftTurn(), vector, type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), type.getLevel());
        initialization();
    }

    public void initialization() {
        setSize(120, 140);
    }
}
