package end.team.center.ProgramSetting.LocalDB;


import end.team.center.ProgramSetting.LocalDB.dao.UserStateDao;

public interface DatabaseManager {
    UserStateDao getUserStateDao();

    // SkinDao getSkinDao();
    // AchievementDao getAchievementDao();
}
