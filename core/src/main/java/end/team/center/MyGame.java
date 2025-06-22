package end.team.center;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

import end.team.center.screens.MainMenuScreen;

public class MyGame extends Game {
    public static Music mainMenuMusic;
    public static int currentWeapon = 1;
    public static int currentSkin = 1;
    public static float currentVolume = 0.5f;
    public static boolean isFieldScreenLoaded = false;
    public static ArrayList<String> pricesOfSkins = new ArrayList<>();
    public static ArrayList<Boolean> isBoughtSkins = new ArrayList<>();
    public static ArrayList<String> pricesOfWeapons = new ArrayList<>();
    public static ArrayList<Boolean> isBoughtWeapons = new ArrayList<>();

    @Override
    public void create() {
        pricesOfSkins.add("0");
        pricesOfSkins.add("50");
        pricesOfSkins.add("100");
        pricesOfSkins.add("150");
        isBoughtSkins.add(true);
        isBoughtSkins.add(false);
        isBoughtSkins.add(false);
        isBoughtSkins.add(false);
        pricesOfWeapons.add("0");
        pricesOfWeapons.add("50");
        pricesOfWeapons.add("100");
        isBoughtWeapons.add(true);
        isBoughtWeapons.add(false);
        isBoughtWeapons.add(false);

        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainMenuMusic.mp3"));
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(currentVolume);
        mainMenuMusic.play();
        setScreen(new MainMenuScreen(this));
    }
}
