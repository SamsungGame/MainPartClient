package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class DeathScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontDeath = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontCause = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layoutDeath = new GlyphLayout();
    private final GlyphLayout layoutCause = new GlyphLayout();
    public final float layoutDeathX;
    public final float layoutDeathY;
    public final float layoutCauseX;
    public final float layoutCauseY;
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));

    public DeathScreen(MyGame game, String cause) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        fontDeath.getData().setScale(4.0f);
        fontDeath.setColor(Color.RED);
        String deathText = "Вы умерли...";
        layoutDeath.setText(fontDeath, deathText);
        layoutDeathX = (Gdx.graphics.getWidth() - layoutDeath.width) / 2;
        layoutDeathY = Gdx.graphics.getHeight() - layoutDeath.height;

        fontCause.getData().setScale(2.0f);
        layoutCause.setText(fontCause, cause);
        layoutCauseX = (Gdx.graphics.getWidth() - layoutCause.width) / 2;
        layoutCauseY = (Gdx.graphics.getHeight() + layoutCause.height) / 2;

        TextButton backToMainMenuScreenButton = new TextButton("В главное меню", skin);
        backToMainMenuScreenButton.setSize(300, 150);
        backToMainMenuScreenButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMainMenuScreenButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - backToMainMenuScreenButton.getHeight() * 1.5f - 15);

        TextButton restartButton = new TextButton("Сыграть ещё раз", skin);
        restartButton.setSize(300, 150);
        restartButton.setPosition(Gdx.graphics.getWidth() / 2 - backToMainMenuScreenButton.getWidth() / 2,
            backToMainMenuScreenButton.getY() - restartButton.getHeight() / 2 - 15);

        backToMainMenuScreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new FieldScreen(game));
            }
        });

        stage.addActor(backToMainMenuScreenButton);
        stage.addActor(restartButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(0.2f, 0.2f, 0.2f, 1f);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fontDeath.draw(batch, layoutDeath, layoutDeathX, layoutDeathY);
        fontCause.draw(batch, layoutCause, layoutCauseX, layoutCauseY);
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
        fontDeath.dispose();
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
