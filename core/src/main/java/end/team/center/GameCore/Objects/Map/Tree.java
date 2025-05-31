package end.team.center.GameCore.Objects.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.StaticObject;

public class Tree extends StaticObject {
    private Rectangle bound;
    public Tree(Texture texture, Vector2 vector, float height, float width, boolean canWalk) {
        super(texture, vector, height, width, canWalk);

        bound = new Rectangle(vector.x, vector.y, width, height);
    }

    public void updateRectangle() {
        bound.x = vector.x;
        bound.y = vector.y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        updateRectangle();
    }
}
