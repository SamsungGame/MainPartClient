package end.team.center.ProgramSetting.LocalDB.controllers;

import end.team.center.ProgramSetting.LocalDB.dao.UserStateDao;
import end.team.center.ProgramSetting.LocalDB.models.UserState;

public class UserStateController {
    private final UserStateDao userStateDao;

    public UserStateController(UserStateDao userStateDao) {
        this.userStateDao = userStateDao;
    }

    public UserState getUserState() {
        return userStateDao.getUserState();
    }

    public void addCoins(int amount) {
        UserState current = userStateDao.getUserState();
        current.setCoins(current.getCoins() + amount); // теперь работает
        userStateDao.insertUserState(current);         // сохранить обновлённое состояние
    }

    public void saveUserState(UserState userState) {
        userStateDao.insertUserState(userState);
    }
}
