package end.team.center.screens;

import static end.team.center.MyGame.currentSkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class SkinsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    public final float layoutX;
    public final float layoutY;
    private final Stage stage;
    private final Skin skin;
    private final Skin leftButtonSkin;
    private final Skin rightButtonSkin;
    private final Texture[] images; // Массив из 2 изображений
    private final Image currentImage; // Отображаемый Image
    private int currentIndex;
    private float touchStartX;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));

    public SkinsScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                touchStartX = screenX;
                return stage.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float deltaX = screenX - touchStartX;
                if (Math.abs(deltaX) > 50) {
                    if (deltaX > 0) {
                        showPreviousImage();
                    }
                    else {
                        showNextImage();
                    }
                    return true;
                }
                return stage.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return stage.touchDragged(screenX, screenY, pointer);
            }
        });

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));
        leftButtonSkin = new Skin();
        leftButtonSkin.add("button_up", new Texture(Gdx.files.internal("buttons/leftButton.png")));
        leftButtonSkin.add("button_down", new Texture(Gdx.files.internal("buttons/leftButton.png")));
        ImageButton.ImageButtonStyle leftButtonStyle = new ImageButton.ImageButtonStyle();
        leftButtonStyle.imageUp = leftButtonSkin.getDrawable("button_up");
        leftButtonStyle.imageDown = leftButtonSkin.getDrawable("button_down");
        rightButtonSkin = new Skin();
        rightButtonSkin.add("button_up", new Texture(Gdx.files.internal("buttons/rightButton.png")));
        rightButtonSkin.add("button_down", new Texture(Gdx.files.internal("buttons/rightButton.png")));
        ImageButton.ImageButtonStyle rightButtonStyle = new ImageButton.ImageButtonStyle();
        rightButtonStyle.imageUp = rightButtonSkin.getDrawable("button_up");
        rightButtonStyle.imageDown = rightButtonSkin.getDrawable("button_down");

        font.getData().setScale(4.0f);
        String skinsText = "Скины";
        layout.setText(font, skinsText);
        layoutX = (Gdx.graphics.getWidth() - layout.width) / 2;
        layoutY = Gdx.graphics.getHeight() - layout.height;

        TextButton backButton = new TextButton("Назад", skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        ImageButton leftButton = new ImageButton(leftButtonStyle);
        leftButton.setSize(100, 100);
        leftButton.setPosition(100, Gdx.graphics.getHeight() / 2 - leftButton.getHeight() / 2);

        ImageButton rightButton = new ImageButton(rightButtonStyle);
        rightButton.setSize(100, 100);
        rightButton.setPosition(Gdx.graphics.getWidth() - rightButton.getWidth() - 100, Gdx.graphics.getHeight() / 2 - rightButton.getHeight() / 2);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPreviousImage();
            }
        });

        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showNextImage();
            }
        });

        // Загружаем 2 изображения
        images = new Texture[] {
            new Texture(Gdx.files.internal("skins/heroLeft.png")),
            new Texture(Gdx.files.internal("skins/heroNightLeft.png"))
        };

        currentIndex = currentSkin - 1; // Начинаем с первого изображения

        // Создаём Image для отображения текущего изображения
        currentImage = new Image(new TextureRegionDrawable(images[currentIndex]));
        currentImage.setSize(200, 200);
        currentImage.setPosition(Gdx.graphics.getWidth() / 2 - currentImage.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - currentImage.getHeight() / 2);

        stage.addActor(backButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(currentImage);
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % images.length; // Циклический переход
        updateImage();
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + images.length) % images.length; // Циклический переход назад
        updateImage();
    }

    private void updateImage() {
        currentImage.setDrawable(new TextureRegionDrawable(images[currentIndex]));
        currentSkin = currentIndex + 1;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, layout, layoutX, layoutY);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for (Texture texture : images) {
            texture.dispose();
        }
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
        leftButtonSkin.dispose();
        rightButtonSkin.dispose();
        backgroundTexture.dispose();
    }
}
