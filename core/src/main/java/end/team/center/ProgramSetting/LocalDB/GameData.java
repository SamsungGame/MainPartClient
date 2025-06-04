package end.team.center.ProgramSetting.LocalDB;

import java.util.*;

public class GameData {
    // ID ачивок и скинов — фиксированный список
    public static final int[] ACHIEVEMENT_IDS = {1, 2, 3, 4, 5};
    public static final int[] SKIN_IDS = {1, 2};

    // Информация об ачивках
    public static final List<AchievementData> ACHIEVEMENTS = Arrays.asList(
        new AchievementData(1, "Прошёл сквозь завесу", "Заверши забег и выберись"),
        new AchievementData(2, "Ничего, кроме ножа", "Заверши забег, не подняв ни одного предмета"),
        new AchievementData(3, "Тишина длиной в вечность", "Выжить 10 минут"),
        new AchievementData(3, "Дверь была открыта...", "Умри у самого выхода"),
        new AchievementData(3, "Руки остались чистыми", " выберись, никого не убив")
    );

    // Информация о скинах
    public static final List<SkinData> SKINS = Arrays.asList(
        new SkinData(1, "Обычный"),
        new SkinData(2, "Рыцарь")
    );

    public static AchievementData getAchievementById(int id) {
        for (AchievementData a : ACHIEVEMENTS) {
            if (a.id == id) return a;
        }
        return null;
    }

    public static SkinData getSkinById(int id) {
        for (SkinData s : SKINS) {
            if (s.id == id) return s;
        }
        return null;
    }
}
