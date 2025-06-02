package end.team.center.LocalDB.dao;


import end.team.center.LocalDB.models.UserState;

public interface UserStateDao {
    UserState getUserState();
    void insertUserState(UserState userState);
    void addCoins(int amount);
}
