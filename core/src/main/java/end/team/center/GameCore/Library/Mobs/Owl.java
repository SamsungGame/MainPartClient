package end.team.center.GameCore.Library.Mobs;

import static end.team.center.Screens.Game.GameScreen.hero;

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

        lDiveTexture = new TextureRegion(this.texture);
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

            if (getX() + getWidth() < camLeft || getX() > camRight ||
                getY() + getHeight() < camBottom || getY() > camTop) {
                return;
            }

            TextureRegion currentFrame;

            // Убедитесь, что ai является экземпляром AI_Owl, прежде чем приводить тип
            if (ai instanceof AI_Owl) {
                AI_Owl owlAI = (AI_Owl) ai;
                if (isMoving && !owlAI.isAttaking) {
                    currentFrame = mRight
                        ? firstTypeAnimation.getKeyFrame(stateTime, true)
                        : secondTypeAnimation.getKeyFrame(stateTime, true);
                } else if (owlAI.isAttaking) {
                    currentFrame = mRight
                        ? therdTypeAnimation.getKeyFrame(stateTime, true)
                        : fourthTypeAnimation.getKeyFrame(stateTime, true);
                } else {
                    currentFrame = firstTypeAnimation.getKeyFrame(stateTime, true);
                }
            } else {
                currentFrame = firstTypeAnimation.getKeyFrame(stateTime, true); // Fallback
            }

            batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateCircle();

        if (ai instanceof AI_Owl) {
            AI_Owl owlAI = (AI_Owl) ai;
            owlAI.diveAttack(this); // Вызываем логику начала пикирования

            Vector2 v = owlAI.MoveToPlayer(null, vector, speed, delta); // AI определяет движение
            move(v.x, v.y, delta); // Применяем движение

            // !!! УДАЛЕН ИЛИ ИЗМЕНЕН ЭТОТ БЛОК:
            // if (((AI_Owl) ai).isAttaking) { ... }
            // Теперь логика завершения атаки и перезарядки полностью управляется в AI_Owl.MoveToPlayer
            // и не должна быть здесь.
        } else {
            // Если AI не AI_Owl, можно оставить стандартное движение или другую логику
            Vector2 v = ai.MoveToPlayer(hero.getVector(), vector, speed, delta);
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
