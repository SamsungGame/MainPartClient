package end.team.center.android.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CoinsEntity.class, UnlockStateEntity.class, CurrentSkinEntity.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
}
