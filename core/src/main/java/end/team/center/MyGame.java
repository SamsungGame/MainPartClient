package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import end.team.center.screens.MainMenuScreen;

public class MyGame extends Game {
    public static Music mainMenuMusic;

    @Override
    public void create() {
        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("mainMenuMusic.mp3"));
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(0.75f);
        mainMenuMusic.play();
        setScreen(new MainMenuScreen(this));
    }
}
