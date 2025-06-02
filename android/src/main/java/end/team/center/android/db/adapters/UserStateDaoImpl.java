package end.team.center.android.db.adapters;

import end.team.center.LocalDB.dao.UserStateDao;
import end.team.center.LocalDB.models.UserState;
import end.team.center.android.db.dao.UserStateDaoRoom;
import end.team.center.android.db.entity.UserStateEntity;

public class UserStateDaoImpl implements UserStateDao {

    private final UserStateDaoRoom roomDao;

    public UserStateDaoImpl(UserStateDaoRoom roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public UserState getUserState() {
        UserStateEntity entity = roomDao.getUserState();
        if (entity == null) {
            return new UserState(0);
        }
        return new UserState(entity.coins);
    }

    @Override
    public void insertUserState(UserState userState) {
        UserStateEntity entity = new UserStateEntity();
        entity.id = 1; // фиксированный ID для локального пользователя
        entity.coins = userState.getCoins();
        roomDao.insertUserState(entity);
    }

    @Override
    public void addCoins(int amount) {
        roomDao.addCoins(amount);
    }
}
