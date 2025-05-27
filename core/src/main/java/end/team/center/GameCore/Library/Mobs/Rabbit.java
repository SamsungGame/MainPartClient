package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Rabbit;
import end.team.center.GameCore.Objects.OnMap.Ammo;
import end.team.center.GameCore.Objects.OnMap.Enemy;

public class Rabbit extends Enemy {
    protected AI_Rabbit ai;

    public Rabbit(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, int exp, float worldHeight, float worldWidth, AI ai) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, level, exp, worldHeight, worldWidth, ai);

        initialization();
    }

    public Rabbit(Texture texture, EnemyType type, Vector2 vector, int level, float worldHeight, float worldWidth, AI ai) {
        super(texture, type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), level, type.getExp(), worldHeight, worldWidth, ai);
        this.ai = (AI_Rabbit) ai;

        initialization();
    }

    public void initialization() {
        health = health + (health / 4 * level);
    }

    @Override
    public void move(float deltaX, float deltaY, float delta) {
        super.move(deltaX, deltaY, delta);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Vector2 v = ai.MoveToPlayer(null, getVector(), speed, delta);
        move(v.x, v.y, delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isLive) {
            TextureRegion currentFrame;

            if (isMoving) {
                currentFrame = mRight
                    ? firstTypeAnimation.getKeyFrame(stateTime, true) // Хотьба направо
                    : secondTypeAnimation.getKeyFrame(stateTime, true); // Хотьба налево
            } else currentFrame = firstTypeAnimation.getKeyFrame(stateTime, true);

            batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
