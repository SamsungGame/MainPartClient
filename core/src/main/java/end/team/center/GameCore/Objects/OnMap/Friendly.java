package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.Screens.Game.GameScreen;

public abstract class Friendly extends Entity {

    public Friendly(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, float worldHeight, float worldWidth) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
    }

    public void move(float deltaX, float deltaY, float delta) {
        isMoving = deltaX != 0 || deltaY != 0;

        Vector2 potentialPosition = new Vector2(vector.x + deltaX * speed * delta,
            vector.y + deltaY * speed * delta);

        // Ограничения по границам мира
        potentialPosition.x = Math.max(BOUNDARY_PADDING,
            Math.min(potentialPosition.x, worldWidth - BOUNDARY_PADDING - width));
        potentialPosition.y = Math.max(BOUNDARY_PADDING,
            Math.min(potentialPosition.y, worldHeight - BOUNDARY_PADDING - height));

        if (deltaX > 0) {
            mRight = true;
        } else if (deltaX < 0) {
            mRight = false;
        }

//        // Проверка касания врагов
//        for(Enemy e: GameScreen.enemies) {
//            if (e.getBound().overlaps(new Rectangle(vector.x + deltaX, vector.y + deltaY, width, height))) {
//                if (e.getBound().contains(new Rectangle(vector.x + deltaX, e.getBound().y, width, height))) deltaX = -deltaX;
//                if (e.getBound().contains(new Rectangle(e.getBound().x, vector.y + deltaY, width, height))) deltaY = -deltaY;
//            }
//        }

        vector.set(potentialPosition);
        setPosition(vector.x, vector.y);

        updateBound();

        stateTime += delta;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
