package end.team.center.GameCore.Library.Particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Effects;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.Objects.Particles.Effect;

public class ForwardsEffect extends Effect {
    protected Vector2 target;
    protected int duration;
    protected boolean start = false;
    protected Hero hero;
    protected float elapsedTime = 0f;
    public ForwardsEffect(Texture texture, Vector2 vector, Hero hero, int width, int height, int duration, float speed) {
        super(texture, vector, width, height, speed);
        this.duration = duration;
    }

    public ForwardsEffect(Effects effect, Vector2 vector, Hero hero) {
        super(effect.getTexture(), vector, effect.getWidth(), effect.getHeight(), effect.getSpeed());
        this.duration = effect.getDuration();
        this.hero = hero;
    }

    private boolean startIllusion(float delta) {
        Vector2 potentialPosition = new Vector2(vector.x + target.x * speed * delta,
            vector.y + target.y * speed * delta);

        elapsedTime += delta;

        if (elapsedTime >= duration) return true;

        vector.set(potentialPosition);
        setPosition(vector.x, vector.y);

        return false;
    }

    public void startMove(Vector2 vector, Vector2 target, float rotation) {
        this.vector = vector;
        start = true;
        this.target = target;

        setRotation(rotation);
    }

    public boolean getStart() {
        return start;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (start) batch.draw(new TextureRegion(texture), vector.x, vector.y, getOriginX(), getOriginY(), width, height, getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(start) {
            if(startIllusion(delta)) {
                start = false;
                vector = hero.getCenterVector();
            }
        } else {
            vector = hero.getCenterVector();
        }
    }

}
