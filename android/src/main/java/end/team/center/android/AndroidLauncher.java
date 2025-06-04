package end.team.center.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import end.team.center.Center;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.android.db.GameRepositoryImpl;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.

        GameRepository gameRepository = new GameRepositoryImpl(this);
        Center center = new Center();
        center.setGameRepository(gameRepository);
        initialize(center, configuration);
    }
}
