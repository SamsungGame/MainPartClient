package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class SkinsScreen implements Screen {
    private Stage stage;
    private Texture[] images; // массив из 3 изображений
    private Image currentImage; // отображаемые Image
    private int currentIndex;
    private float touchStartX;

    public SkinsScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());

        // Загружаем 3 изображения
        images = new Texture[] {
            new Texture(Gdx.files.internal("field/bossField.png")),
            new Texture(Gdx.files.internal("field/dungeon.png")),
            new Texture(Gdx.files.internal("field/grass.png"))
        };

        currentIndex = 0; // начинаем с первого изображения

        // Создаём Image для отображения текущего изображения
        currentImage = new Image(new TextureRegionDrawable(images[currentIndex]));
        currentImage.setSize(100, 100);
        currentImage.setPosition(Gdx.graphics.getWidth() / 2 - currentImage.getWidth() / 2, Gdx.graphics.getHeight() / 2 - currentImage.getHeight() / 2);
        stage.addActor(currentImage);

        // Обработка свайпов через InputAdapter
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                touchStartX = screenX;
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float deltaX = screenX - touchStartX;

                if (Math.abs(deltaX) > 50) { // порог для определения свайпа
                    if (deltaX > 0) {
                        showPreviousImage(); // свайп вправо — показываем предыдущее
                    } else {
                        showNextImage(); // свайп влево — показываем следующее
                    }
                }
                return true;
            }
        });
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % images.length; // циклический переход
        updateImage();
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + images.length) % images.length; // циклический переход назад
        updateImage();
    }

    private void updateImage() {
        currentImage.setDrawable(new TextureRegionDrawable(images[currentIndex]));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        for (Texture tex : images) tex.dispose();
        stage.dispose();
    }
}
