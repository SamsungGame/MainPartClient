package end.team.center.ProgramSetting.LocalDB.dao;

import end.team.center.ProgramSetting.LocalDB.models.Achievement;

public interface AchievementDao {
    void insertAchievement(Achievement achievement);
    Achievement getAchievementByCode(String code);
}
