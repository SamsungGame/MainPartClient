package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class StaticObject extends GameObject {
    protected boolean canWalk; // Можно ли проходить сквозь блок

    public StaticObject(Texture texture, Vector2 vector, float height, float width, boolean canWalk) {
        super(texture, vector, height, width);
        this.canWalk = canWalk;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, vector.x, vector.y, getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
