package end.team.center.screens;

import static end.team.center.MyGame.defaultVolume;

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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class SettingsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontSettings = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontMusic = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontVolume = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layoutSettings = new GlyphLayout();
    private final GlyphLayout layoutMusic = new GlyphLayout();
    private final GlyphLayout layoutVolume = new GlyphLayout();
    private final float layoutSettingsX;
    private final float layoutSettingsY;
    private final float layoutMusicX;
    private final float layoutMusicY;
    private final float layoutVolumeX;
    private final float layoutVolumeY;
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

        String settingsText = "Настройки";
        fontSettings.getData().setScale(4.0f);
        layoutSettings.setText(fontSettings, settingsText);
        layoutSettingsX = (Gdx.graphics.getWidth() - layoutSettings.width) / 2;
        layoutSettingsY = Gdx.graphics.getHeight() - layoutSettings.height;

        String musicText = "Музыка:";
        fontMusic.getData().setScale(1.0f);
        layoutMusic.setText(fontMusic, musicText);
        layoutMusicX = (Gdx.graphics.getWidth() - layoutMusic.width) / 2 - 100;
        layoutMusicY = Gdx.graphics.getHeight() / 2 - layoutMusic.height + 15;

        String volumeText = "Громкость:";
        fontVolume.getData().setScale(1.0f);
        layoutVolume.setText(fontVolume, volumeText);
        layoutVolumeX = (Gdx.graphics.getWidth() - layoutMusic.width) / 2 - 100;
        layoutVolumeY = Gdx.graphics.getHeight() / 2 - layoutVolume.height - 120;

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
                if (mainMenuMusic.getVolume() == 0f) {
                    mainMenuMusic.setVolume(defaultVolume);
                    musicButton.setText(turnOffMusic);
                }
                else {
                    mainMenuMusic.setVolume(0f);
                    musicButton.setText(turnOnMusic);
                }
            }
        });

//        Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
//        volumeSlider.setValue(defaultVolume);
//        volumeSlider.setSize(300, 50);
//        volumeSlider.setPosition(layoutMusicX - volumeSlider.getWidth(),
//            layoutMusicY - volumeSlider.getHeight());
//
//        volumeSlider.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                defaultVolume = volumeSlider.getValue();
//                mainMenuMusic.setVolume(defaultVolume);
//            }
//        });

        stage.addActor(backButton);
        stage.addActor(musicButton);
//        stage.addActor(volumeSlider);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        fontSettings.draw(batch, layoutSettings, layoutSettingsX, layoutSettingsY);
        fontMusic.draw(batch, layoutMusic, layoutMusicX, layoutMusicY);
        fontVolume.draw(batch, layoutVolume, layoutVolumeX, layoutVolumeY);
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
        fontMusic.dispose();
        fontVolume.dispose();
        stage.dispose();
        skin.dispose();
    }
}
