package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Owl;
import end.team.center.GameCore.Objects.OnMap.Enemy;

public class Owl extends Enemy {

    protected Circle startAttack, endAttack;
    protected TextureRegion lDiveTexture, rDiveTexture;

    public Owl(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, int exp, float worldHeight, float worldWidth, AI ai) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, level, exp, worldHeight, worldWidth, ai);
        intialization();
    }

    public Owl(EnemyType type, Vector2 vector, int level, float worldHeight, float worldWidth, AI ai) {
        super(type.getTexture(), type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), level, type.getExp(), worldHeight, worldWidth, ai);
        intialization();
    }

    public void intialization() {
        startAttack = new Circle(vector.x + width / 2, vector.y + height / 2, 1000);
        endAttack = new Circle(vector.x + width / 2, vector.y + height / 2, 400);

        lDiveTexture = new TextureRegion(this.texture); // this.texture
        rDiveTexture = new TextureRegion(this.texture);
        rDiveTexture.flip(true, false);

        health = health + (health / 4 * level);

        if (level > 7 && level < 14) damage = 2;
        else if (level >= 14) damage = 3;

        speed = speed + 30 * level;
    }

    public Circle getStartCircle() {
        return startAttack;
    }
    public Circle getEndCircle() {
        return endAttack;
    }
    public void updateCircle() {
        startAttack.x = vector.x;
        startAttack.y = vector.y;
        endAttack.x = vector.x;
        endAttack.y = vector.y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isLive) {
            super.draw(batch, parentAlpha);

            if (((AI_Owl) ai).isAttaking) {
                if (mRight)  batch.draw(new TextureRegion(rDiveTexture), vector.x, vector.y, getWidth(), getHeight());
                if (!mRight) batch.draw(new TextureRegion(lDiveTexture), vector.x, vector.y, getWidth(), getHeight());
            } else {
                TextureRegion currentFrame;

                if (isMoving) {
                    currentFrame = mRight
                        ? walkRightAnimation.getKeyFrame(stateTime, true)
                        : walkLeftAnimation .getKeyFrame(stateTime, true);
                } else {
                    currentFrame = mRight
                        ? stayRightAnimation.getKeyFrame(stateTime, true)
                        : stayLeftAnimation .getKeyFrame(stateTime, true);
                }

                batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateCircle();

        ((AI_Owl) ai).diveAttack(this);

        if (ai instanceof AI_Owl) {
            Vector2 v = ((AI_Owl) ai).MoveToPlayer(null, vector, speed, delta);

            move(v.x, v.y, delta);
        }
    }

    @Override
    public void move(float deltaX, float deltaY, float delta) {
        super.move(deltaX, deltaY, delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
