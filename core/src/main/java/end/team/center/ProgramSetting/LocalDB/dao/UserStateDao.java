package end.team.center.ProgramSetting.LocalDB.dao;


import end.team.center.ProgramSetting.LocalDB.models.UserState;

public interface UserStateDao {
    UserState getUserState();
    void insertUserState(UserState userState);
    void addCoins(int amount);
}
