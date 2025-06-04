package end.team.center.android.db;

import android.content.Context;
import androidx.room.Room;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import end.team.center.ProgramSetting.LocalDB.GameData;
import end.team.center.ProgramSetting.LocalDB.GameRepository;

public class GameRepositoryImpl implements GameRepository {

    private final GameDao dao;

    public GameRepositoryImpl(Context context) {
        GameDatabase db = Room.databaseBuilder(context, GameDatabase.class, "game-db").allowMainThreadQueries().build();
        this.dao = db.gameDao();
        initializeDefaults();
    }

    private void initializeDefaults() {
        if (dao.getCoins() == null) {
            CoinsEntity coins = new CoinsEntity();
            coins.amount = 0;
            dao.insertCoins(coins);
        }
        for (int id : GameData.ACHIEVEMENT_IDS) {
            if (dao.getUnlock("achievement", id) == null) {
                UnlockStateEntity e = new UnlockStateEntity();
                e.type = "achievement";
                e.itemId = id;
                e.unlocked = false;
                dao.insertUnlock(e);
            }
        }
        for (int id : GameData.SKIN_IDS) {
            if (dao.getUnlock("skin", id) == null) {
                UnlockStateEntity e = new UnlockStateEntity();
                e.type = "skin";
                e.itemId = id;
                e.unlocked = false;
                dao.insertUnlock(e);
            }
        }
    }

    @Override
    public int getCoins() {
        CoinsEntity coins = dao.getCoins();
        return coins != null ? coins.amount : 0;
    }

    @Override
    public void addCoins(int amount) {
        CoinsEntity coins = dao.getCoins();
        if (coins == null) coins = new CoinsEntity();
        coins.amount += amount;
        dao.insertCoins(coins);
    }

    @Override
    public void spendCoins(int amount) {
        CoinsEntity coins = dao.getCoins();
        if (coins != null && coins.amount >= amount) {
            coins.amount -= amount;
            dao.insertCoins(coins);
        }
    }

    @Override
    public Map<Integer, Boolean> getAchievements() {
        return getUnlockMap("achievement");
    }

    @Override
    public void unlockAchievement(int id) {
        setUnlock("achievement", id);
    }

    @Override
    public Map<Integer, Boolean> getSkins() {
        return getUnlockMap("skin");
    }

    @Override
    public void unlockSkin(int id) {
        setUnlock("skin", id);
    }

    private Map<Integer, Boolean> getUnlockMap(String type) {
        List<UnlockStateEntity> list = dao.getUnlocksByType(type);
        Map<Integer, Boolean> map = new HashMap<>();
        for (UnlockStateEntity e : list) {
            map.put(e.itemId, e.unlocked);
        }
        return map;
    }

    private void setUnlock(String type, int id) {
        UnlockStateEntity e = dao.getUnlock(type, id);
        if (e == null) {
            e = new UnlockStateEntity();
            e.type = type;
            e.itemId = id;
        }
        e.unlocked = true;
        dao.insertUnlock(e);
    }
}
