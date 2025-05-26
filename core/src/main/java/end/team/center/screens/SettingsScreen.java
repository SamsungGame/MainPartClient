package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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

public class SettingsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontSettings = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontMusic = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layoutSettings = new GlyphLayout();
    private final GlyphLayout layoutMusic = new GlyphLayout();
    private final Music mainMenuMusic;
    private final Stage stage;
    private final Skin skin;
    private final String turnOnMusic = "Включить музыку";
    private final String turnOffMusic = "Выключить музыку";

    public SettingsScreen(MyGame game) {
        this.mainMenuMusic = MyGame.mainMenuMusic;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        TextButton backButton = new TextButton("Назад", skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        TextButton musicButton = new TextButton("", skin);
        if (mainMenuMusic.getVolume() > 0) {
            musicButton.setText(turnOffMusic);
        }
        else {
            musicButton.setText(turnOnMusic);
        }
        musicButton.setSize(350, 150);
        musicButton.setPosition(Gdx.graphics.getWidth() / 2 - musicButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - musicButton.getHeight());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (mainMenuMusic.getVolume() == 0.0f) {
                    mainMenuMusic.setVolume(0.75f);
                    musicButton.setText(turnOffMusic);
                }
                else {
                    mainMenuMusic.setVolume(0.0f);
                    musicButton.setText(turnOnMusic);
                }
            }
        });

        stage.addActor(backButton);
        stage.addActor(musicButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        String settingsText = "Настройки";
        layoutSettings.setText(fontSettings, settingsText);
        float layoutSettingsX = (Gdx.graphics.getWidth() - layoutSettings.width) / 2;
        float layoutSettingsY = Gdx.graphics.getHeight() - layoutSettings.height;

        String musicText = "Музыка:";
        layoutMusic.setText(fontMusic, musicText);
        float layoutMusicX = (Gdx.graphics.getWidth() - layoutMusic.width) / 2 - 100;
        float layoutMusicY = Gdx.graphics.getHeight() / 2 - layoutMusic.height + 15;

        batch.begin();
        fontSettings.getData().setScale(4.0f);
        fontSettings.draw(batch, layoutSettings, layoutSettingsX, layoutSettingsY);
        fontMusic.getData().setScale(1.0f);
        fontMusic.draw(batch, layoutMusic, layoutMusicX, layoutMusicY);
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
        fontSettings.dispose();
        stage.dispose();
        skin.dispose();
    }
}
