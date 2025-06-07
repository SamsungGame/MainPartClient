package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

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

    public Owl(EnemyType type, Texture texture, Vector2 vector, int level, float worldHeight, float worldWidth, AI ai) {
        super(texture, type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), level, type.getExp(), worldHeight, worldWidth, ai);
        intialization();
    }

    public void intialization() {
        startAttack = new Circle(vector.x + width / 2, vector.y + height / 2, 1000);
        endAttack = new Circle(vector.x + width / 2, vector.y + height / 2, 400);

        lDiveTexture = new TextureRegion(this.texture); // this.texture
        rDiveTexture = new TextureRegion(this.texture);
        rDiveTexture.flip(true, false);

        health = health + (health / 6 * level);
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

            OrthographicCamera camera = (OrthographicCamera) getStage().getCamera();

            float camLeft = camera.position.x - camera.viewportWidth / 2 * camera.zoom;
            float camRight = camera.position.x + camera.viewportWidth / 2 * camera.zoom;
            float camBottom = camera.position.y - camera.viewportHeight / 2 * camera.zoom;
            float camTop = camera.position.y + camera.viewportHeight / 2 * camera.zoom;

            // Рисуем только если дерево в зоне видимости камеры
            if (getX() + getWidth() < camLeft || getX() > camRight ||
                getY() + getHeight() < camBottom || getY() > camTop) {
                return;
            }

            TextureRegion currentFrame;

            if (isMoving && !((AI_Owl) ai).isAttaking) {
                currentFrame = mRight
                    ? firstTypeAnimation.getKeyFrame(stateTime, true) // Хотьба направо
                    : secondTypeAnimation.getKeyFrame(stateTime, true); // Хотьба налево
            } else if (((AI_Owl) ai).isAttaking) {
                currentFrame = mRight
                    ? therdTypeAnimation.getKeyFrame(stateTime, true) // Пикирование направо
                    : fourthTypeAnimation.getKeyFrame(stateTime, true); // Пикирование налево
            } else currentFrame = firstTypeAnimation.getKeyFrame(stateTime, true);

            batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
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

        if (isTreeTouch && ((AI_Owl) ai).isAttaking) {
            ((AI_Owl) ai).isAttaking = false;

            ((AI_Owl) ai).lockAttack = null;
            ((AI_Owl) ai).isAttaking = false;
            ((AI_Owl) ai).isDiveAttacking = false;
            ((AI_Owl) ai).timeToReloadDive = 5;
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
