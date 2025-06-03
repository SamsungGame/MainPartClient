package end.team.center.android.db;

import androidx.room.Room;
import android.content.Context;

import end.team.center.ProgramSetting.LocalDB.DatabaseManager;
import end.team.center.ProgramSetting.LocalDB.dao.UserStateDao;
import end.team.center.android.db.adapters.UserStateDaoImpl;

public class DatabaseManagerImpl implements DatabaseManager {

    private final AppDatabase database;
    private final UserStateDao userStateDao;

    public DatabaseManagerImpl(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "mygame-db").build();

        // создаём адаптеры, связываем с Room DAO
        userStateDao = new UserStateDaoImpl(database.userStateDaoRoom());

        // аналогично: skinDao = new SkinDaoImpl(database.skinDaoRoom());
    }

    @Override
    public UserStateDao getUserStateDao() {
        return userStateDao;
    }

}
