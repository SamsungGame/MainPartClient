package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Animations.CharacterAnimation;

public class Hero extends Friendly {

    protected boolean isInvulnerability = false;

    public Hero(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, float worldWidth, float worldHeight) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
    }

    @Override
    public void move(float deltaX, float deltaY, float delta, boolean isMob) {
        super.move(deltaX, deltaY, delta, isMob);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void frameInvulnerability(float sec) {
        isInvulnerability = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long) (sec * 1000L));

                    isInvulnerability = false;
                } catch (InterruptedException ignore) {}
            }
        }).start();
    }


}
