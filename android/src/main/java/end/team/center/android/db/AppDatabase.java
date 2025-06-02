package end.team.center.android.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import end.team.center.android.db.dao.UserStateDaoRoom;
import end.team.center.android.db.entity.UserStateEntity;

@Database(entities = {UserStateEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserStateDaoRoom userStateDaoRoom();
}
