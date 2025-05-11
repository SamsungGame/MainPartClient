package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Отображение экрана игры
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Логика игры
    }

    @Override
    public void resize(int width, int height) {
        // Подстроить разрешение при изменении размеров экрана
    }

    @Override
    public void hide() {
        // Освобождение ресурсов
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
