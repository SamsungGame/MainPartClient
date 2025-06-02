package end.team.center.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import end.team.center.Center;
import end.team.center.Screens.Menu.MainMenuScreen;
import end.team.center.android.db.DatabaseManagerImpl;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.

        DatabaseManagerImpl dbManager = new DatabaseManagerImpl(this);
        initialize(new Center(dbManager), configuration);
    }
}
