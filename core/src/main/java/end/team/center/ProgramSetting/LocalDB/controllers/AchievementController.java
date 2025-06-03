package end.team.center.ProgramSetting.LocalDB.controllers;

import end.team.center.ProgramSetting.LocalDB.dao.AchievementDao;
import end.team.center.ProgramSetting.LocalDB.dao.UserAchievementDao;
import end.team.center.ProgramSetting.LocalDB.models.Achievement;

public class AchievementController {

    private final AchievementDao achievementDao;
    private final UserAchievementDao userAchievementDao;

    public AchievementController(AchievementDao achievementDao, UserAchievementDao userAchievementDao) {
        this.achievementDao = achievementDao;
        this.userAchievementDao = userAchievementDao;
    }

    public void unlockAchievement(String code) {
        Achievement achievement = achievementDao.getAchievementByCode(code);
        if (achievement != null) {
            userAchievementDao.unlockAchievement(achievement.id);
        }
    }

    public boolean isAchievementUnlocked(String code) {
        Achievement achievement = achievementDao.getAchievementByCode(code);
        if (achievement == null) return false;
        return userAchievementDao.isAchievementUnlocked(achievement.id);
    }
}
