package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

import end.team.center.screens.MainMenuScreen;

public class MyGame extends Game {
    public static Music mainMenuMusic;
    public static float currentVolume = 0.5f;
    public static int currentSkin = 1;
    public static ArrayList<String> prices = new ArrayList<>();
    public static ArrayList<Boolean> isBought = new ArrayList<>();

    @Override
    public void create() {
        prices.add("0");
        prices.add("100");
        isBought.add(true);
        isBought.add(false);

        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainMenuMusic.mp3"));
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(currentVolume);
        mainMenuMusic.play();
        setScreen(new MainMenuScreen(this));
    }
}
