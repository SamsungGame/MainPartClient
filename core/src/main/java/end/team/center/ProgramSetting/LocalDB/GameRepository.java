package end.team.center.ProgramSetting.LocalDB;

import java.util.Map;

public interface GameRepository {
    int getCoins();
    void addCoins(int amount);
    void spendCoins(int amount);

    Map<Integer, Boolean> getAchievements();
    void unlockAchievement(int id);

    Map<Integer, Boolean> getSkins();
    void unlockSkin(int id);
    int getCurrentSelectedSkinId(); // Получить ID текущего выбранного скина
    void setCurrentSelectedSkinId(int skinId); // Сохранить ID текущего выбранного скина

}
