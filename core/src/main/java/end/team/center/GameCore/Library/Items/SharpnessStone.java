package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class SharpnessStone extends Drops {

    private final Random random; // Объявляем Random как поле класса

    public SharpnessStone(Texture texture, Vector2 vector, Hero hero, int width, int height) {
        super(texture, vector, hero, width, height);
        this.random = new Random(); // Инициализируем Random один раз в конструкторе
    }

    public SharpnessStone(ItemType type, Vector2 vector, Hero hero) {
        super(type.getTexture(), vector, hero, type.getWidth(), type.getHeight());
        this.random = new Random(); // Инициализируем Random один раз в конструкторе
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (bound.overlaps(hero.getBound())) {
            // Исправленная строка: генерация числа от 3 до 10 включительно
            // random.nextInt(max - min + 1) + min
            // random.nextInt(10 - 3 + 1) + 3 = random.nextInt(8) + 3
            hero.addExpWeapon(random.nextInt(6) + 3);

            GameScreen.isPickupItem = true;

            remove();
            GameScreen.drop.remove(this);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
