package end.team.center.android.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {
    // Получить монеты
    @Query("SELECT * FROM coins WHERE id = 0")
    CoinsEntity getCoins();

    // Вставить или обновить монеты
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoins(CoinsEntity coins);

    // Получить все unlock записи для типа (achievement или skin)
    @Query("SELECT * FROM unlock_state WHERE type = :type")
    List<UnlockStateEntity> getUnlocksByType(String type);

    // Получить конкретную запись unlock
    @Query("SELECT * FROM unlock_state WHERE type = :type AND itemId = :itemId")
    UnlockStateEntity getUnlock(String type, int itemId);

    // Вставить или обновить запись unlock
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnlock(UnlockStateEntity entity);
}
