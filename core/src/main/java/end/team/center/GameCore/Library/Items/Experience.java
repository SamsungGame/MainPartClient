package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class Experience extends Drops {
    protected static final int DISTANCE_TO_PICKUP = 250;
    protected int count;
    protected static final int speed = 150;
    private final Sound sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/exp.mp3"));
    public Experience(Texture texture, Vector2 vector, Hero hero, int width, int height, int count) {
        super(texture, vector, hero, width, height);
        this.count = count;
    }

    public Experience(ItemType type, Vector2 vector, Hero hero, int count) {
        super(type.getTexture(), vector, hero, type.getWidth(), type.getHeight());
        this.count = count;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (GMath.checkVectorDistance(getCenterVector(), hero.getCenterVector(), DISTANCE_TO_PICKUP, DISTANCE_TO_PICKUP)) {
            Vector2 fixTarget   = new Vector2(hero.getCenterVector().x, hero.getCenterVector().y);
            Vector2 fixPosition = new Vector2(vector.x, vector.y);

            Vector2 direction = fixTarget.sub(fixPosition);

            // Нормализуем вектор, чтобы сделать его единичной длины
            if (direction.len() > 0) direction.nor(); // Нормализуем

            // Обновляем позицию врага
            vector.add(direction.scl((float) (speed * delta)));

            setPosition(vector.x, vector.y);
            updateBound();
        }
        if (hero.getBound().overlaps(bound)) {
            hero.addExp(count);
            long id = sound.play();
            sound.setVolume(id, 0.5f);
            System.out.println("Выдано " + count + "exp");
            remove();
            GameScreen.drop.remove(this);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
