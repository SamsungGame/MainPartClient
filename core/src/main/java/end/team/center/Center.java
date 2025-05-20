package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Center extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        // Создание спрайт-объекта, который будет использоваться для отрисовки
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen());

    }

    @Override
    public void render() {
        super.render(); // Рендерит текущий экран
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
