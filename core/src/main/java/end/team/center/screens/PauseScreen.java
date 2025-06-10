package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class PauseScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layout = new GlyphLayout();
    public final float layoutX;
    public final float layoutY;
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));

    public PauseScreen(MyGame game, FieldScreen fieldScreen) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        font.getData().setScale(4.0f);
        String pauseText = "Пауза";
        layout.setText(font, pauseText);
        layoutX = (Gdx.graphics.getWidth() - layout.width) / 2;
        layoutY = Gdx.graphics.getHeight() - layout.height;

        TextButton backToMainMenuScreenButton = new TextButton("В главное меню", skin);
        backToMainMenuScreenButton.setSize(300, 150);
        backToMainMenuScreenButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMainMenuScreenButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - backToMainMenuScreenButton.getHeight() * 1.5f - 15);

        TextButton continueButton = new TextButton("Продолжить игру", skin);
        continueButton.setSize(300, 150);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMainMenuScreenButton.getWidth() / 2,
            backToMainMenuScreenButton.getY() - continueButton.getHeight() / 2 - 15);

        backToMainMenuScreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(fieldScreen);
            }
        });

        stage.addActor(backToMainMenuScreenButton);
        stage.addActor(continueButton);
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
        batch.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }
}
