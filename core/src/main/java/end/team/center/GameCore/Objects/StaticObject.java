package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;

public class StaticObject extends GameObject {
    protected boolean canWalk; // Можно ли проходить сквозь блок
    public StaticObject(Texture texture, boolean canWalk) {
        super(texture);
        this.canWalk = canWalk;
    }


}
