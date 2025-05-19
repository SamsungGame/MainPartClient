package end.team.center.GameCore.Library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum EnemyType {
    // Типы существующих врагов
    fogFrog(new Texture(Gdx.files.internal("")), new Texture(Gdx.files.internal("")), 5, 0, 1, 1, 2.0f);

    private final Texture leftTurn, rightTurn;
    private final int health, defense, damage, level; // Базовые
    private final float speed;

    EnemyType(Texture leftTurn, Texture rightTurn, int health, int defense, int damage, int level, float speed) {
        this.leftTurn = leftTurn;
        this.rightTurn = rightTurn;
        this.health = health;
        this.defense = defense;
        this.damage = damage;
        this.level = level;
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public Texture getLeftTurn() {
        return leftTurn;
    }

    public Texture getRightTurn() {
        return rightTurn;
    }

    public int getLevel() {
        return level;
    }
}
