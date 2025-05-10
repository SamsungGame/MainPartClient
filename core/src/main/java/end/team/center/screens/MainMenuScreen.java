package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Screen;
import end.team.center.Center;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private BitmapFont font;
    private TextButton playButton;

    @Override
    public void show() {
        // Создаем stage для UI
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Загружаем шрифт для кнопок
        font = new BitmapFont(); // Используем стандартный шрифт
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;  // Цвет шрифта

        // Создаем кнопку
        playButton = new TextButton("Play", buttonStyle);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);

        // Добавляем обработчик нажатия на кнопку
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Переход к экрану игры (например, GameScreen)
                ((Center) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        // Добавляем кнопку на экран
        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {
        // Очищаем экран
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Рисуем stage (кнопки, текст и т. д.)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Обновляем размер stage при изменении размеров экрана
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Освобождаем ресурсы
        stage.dispose();
        font.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
