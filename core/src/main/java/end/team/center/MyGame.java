package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import end.team.center.screens.MainMenuScreen;

public class MyGame extends Game {
    public static Music mainMenuMusic;
    public static float defaultVolume = 0.5f;

    @Override
    public void create() {
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainMenuMusic.mp3"));
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(defaultVolume);
        mainMenuMusic.play();
        setScreen(new MainMenuScreen(this));
    }
}
