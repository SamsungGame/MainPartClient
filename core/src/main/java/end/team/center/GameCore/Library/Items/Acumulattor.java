package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random; // Убедитесь, что Random импортирован

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class Acumulattor extends Drops {

    private final Random random; // Объявляем Random как поле класса

    public Acumulattor(Texture texture, Vector2 vector, Hero hero, int width, int height) {
        super(texture, vector, hero, width, height);
        this.random = new Random(); // Инициализируем Random один раз в конструкторе
    }

    public Acumulattor(ItemType type, Vector2 vector, Hero hero) {
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
            // Исправленная строка: генерация числа от 10 до 30 включительно
            // random.nextInt(max - min + 1) + min
            // random.nextInt(30 - 10 + 1) + 10 = random.nextInt(21) + 10
            hero.addCostumePower(random.nextInt(21) + 10);

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
