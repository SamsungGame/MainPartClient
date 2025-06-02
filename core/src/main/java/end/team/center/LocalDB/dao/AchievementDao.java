package end.team.center.LocalDB.dao;

import end.team.center.LocalDB.models.Achievement;

public interface AchievementDao {
    void insertAchievement(Achievement achievement);
    Achievement getAchievementByCode(String code);
}
