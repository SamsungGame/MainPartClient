package end.team.center.GameCore.Library.Particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Effects;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.Particles.Effect;

public class ForwardsEffect extends Effect {
    protected Vector2 target;
    protected int duration;
    protected boolean start = false;
    public ForwardsEffect(Texture texture, Vector2 vector, int width, int height, int duration, float speed) {
        super(texture, vector, width, height, speed);
        this.duration = duration;
    }

    public ForwardsEffect(Effects effect, Vector2 vector, int width, int height) {
        super(effect.getTexture(), vector, width, height, effect.getSpeed());
        this.duration = effect.getDuration();
    }

    private boolean startIllusion(float delta) {
        Vector2 potentialPosition = new Vector2(vector.x + target.x * speed * delta,
            vector.y + target.y * speed * delta);

        if (GMath.checkVectorDistance(potentialPosition, target, (int) potentialPosition.x, (int) potentialPosition.y)) {
            return true;
        }

        vector.set(potentialPosition);
        setPosition(vector.x, vector.y);

        return false;
    }

    public void startMove(Vector2 target, float rotation) {
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

        boolean s = true;
        if(start) s = startIllusion(delta);
        if(s) start = false;
        else  start = true;
    }

}
