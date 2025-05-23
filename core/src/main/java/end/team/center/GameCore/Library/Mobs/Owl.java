package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Animations.CharacterAnimation;
import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Owl;
import end.team.center.GameCore.Objects.Enemy;

public class Owl extends Enemy {

    protected Circle circle;
    protected Texture lDiveTexture, rDiveTexture;

    public Owl(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, float worldHeight, float worldWidth, AI ai) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, level, worldHeight, worldWidth, ai);
        initialization();
    }

    public Owl(EnemyType type, Vector2 vector, float worldHeight, float worldWidth, AI ai) {
        super(type.getTexture(), type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), type.getLevel(), worldHeight, worldWidth, ai);
        initialization();
    }

    public void initialization() {
        circle = new Circle(vector.x + width / 2, vector.y + height / 2, 1400);

        lDiveTexture = new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlLeft_down.png"));
        rDiveTexture = new Texture(Gdx.files.internal("UI/GameUI/Mobs/Owl/owlRight_down.png"));
    }

    public Circle getCircle() {
        return circle;
    }
    public void updateCircle() {
        circle.x = vector.x;
        circle.y = vector.y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (((AI_Owl) ai).isAttaking) batch.draw(mRight ? rDiveTexture : lDiveTexture, vector.x, vector.y, getWidth(), getHeight());
        else super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateCircle();

        ((AI_Owl) ai).diveAttack(this);

        if (ai instanceof AI_Owl) {
            Vector2 v = ((AI_Owl) ai).MoveToPlayer(null, vector, speed, delta);

            move(v.x, v.y, delta, true);
        }
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
