package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Screen;
import end.team.center.Center;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private BitmapFont font;
    private Button playButton;
    private Skin skin;

    @Override
    public void show() {
        // Создаем stage для UI
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skinPlayButton.json"));
        playButton = new Button(skin);
        playButton.setSize(640, 180);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

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

        stage.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
