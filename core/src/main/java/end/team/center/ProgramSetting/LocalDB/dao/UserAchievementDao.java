package end.team.center.ProgramSetting.LocalDB.dao;

public interface UserAchievementDao {
    void unlockAchievement(int achievementId);
    boolean isAchievementUnlocked(int achievementId);
}
