package end.team.center.LocalDB;


import end.team.center.LocalDB.dao.UserStateDao;

public interface DatabaseManager {
    UserStateDao getUserStateDao();

    // SkinDao getSkinDao();
    // AchievementDao getAchievementDao();
}
