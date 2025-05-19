package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class StaticObject extends GameObject {
    protected boolean canWalk; // Можно ли проходить сквозь блок

    public StaticObject(Vector2 vector, boolean canWalk) {
        super(vector);
        this.canWalk = canWalk;
    }
}
