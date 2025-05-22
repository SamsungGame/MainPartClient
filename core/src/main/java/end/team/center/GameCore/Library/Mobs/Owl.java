package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Animations.CharacterAnimation;
import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Objects.Enemy;

public class Owl extends Enemy {

    public Owl(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, float worldHeight, float worldWidth) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, level, worldHeight, worldWidth);
        initialization();
    }

    public Owl(EnemyType type, Vector2 vector, float worldHeight, float worldWidth) {
        super(type.getTexture(), type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), type.getLevel(), worldHeight, worldWidth);
        initialization();
    }

    public void initialization() {
        setSize(120, 140);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void move(float deltaX, float deltaY, float delta, boolean isMob) {
        super.move(deltaX, deltaY, delta, isMob);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
