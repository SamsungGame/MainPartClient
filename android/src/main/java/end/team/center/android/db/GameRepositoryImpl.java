
package end.team.center.android.db; // Указываем, что класс находится в этом пакете

import android.content.Context; // Импорт контекста Android, нужен для создания базы данных
import androidx.room.Room; // Импорт для работы с Room — ORM-библиотекой от Google
import java.util.HashMap; // Используется для создания Map, где будут храниться состояния (скины/ачивки)
import java.util.List; // Интерфейс списка — для получения записей из базы
import java.util.Map; // Интерфейс отображения — ключ-значение

import end.team.center.ProgramSetting.LocalDB.GameData; // Класс с ID достижений и скинов
import end.team.center.ProgramSetting.LocalDB.GameRepository; // Интерфейс, который реализует этот класс
import end.team.center.android.db.UnlockStateEntity;

public class GameRepositoryImpl implements GameRepository { // Реализация интерфейса работы с БД

    private final GameDao dao; // Data Access Object — интерфейс для запросов к базе

    public GameRepositoryImpl(Context context) { // Конструктор, принимает Android-контекст
        GameDatabase db = Room.databaseBuilder(context, GameDatabase.class, "game-db") // Строим базу данных
            .allowMainThreadQueries() // Разрешаем работу с БД в главном потоке (в демо можно, но не в продакшене)
            .build(); // Завершаем создание
        this.dao = db.gameDao(); // Получаем реализацию DAO из базы
        initializeDefaults(); // Инициализируем стандартные значения, если данных ещё нет
    }

    private void initializeDefaults() { // Метод, который устанавливает значения по умолчанию

        if (dao.getCoins() == null) { // Если монеты ещё не были сохранены
            CoinsEntity coins = new CoinsEntity(); // Создаём новую сущность
            coins.amount = 0; // Устанавливаем количество монет = 0
            dao.insertCoins(coins); // Сохраняем в базу
        }

        for (int id : GameData.ACHIEVEMENT_IDS) { // Проходим по всем ID достижений
            if (dao.getUnlock("achievement", id) == null) { // Если достижение ещё не сохранено
                UnlockStateEntity e = new UnlockStateEntity(); // Создаём новую запись
                e.type = "achievement"; // Тип = достижение
                e.itemId = id; // Устанавливаем ID достижения
                e.unlocked = false; // По умолчанию — закрыто
                dao.insertUnlock(e); // Сохраняем в базу
            }
        }

        for (int id : GameData.SKIN_IDS) { // Аналогично — для скинов
            if (dao.getUnlock("skin", id) == null) {
                UnlockStateEntity e = new UnlockStateEntity();
                e.type = "skin"; // Тип = скин
                e.itemId = id;
                e.unlocked = false; // Заблокирован по умолчанию
                dao.insertUnlock(e);
            }
        }
    }

    @Override
    public int getCoins() { // Получить текущее количество монет
        CoinsEntity coins = dao.getCoins(); // Чтение из базы
        return coins != null ? coins.amount : 0; // Если запись найдена — возвращаем amount, иначе 0
    }

    @Override
    public void addCoins(int amount) { // Добавить монеты
        CoinsEntity coins = dao.getCoins(); // Получаем текущие монеты
        if (coins == null) coins = new CoinsEntity(); // Если нет — создаём новую запись
        coins.amount += amount; // Прибавляем количество
        dao.insertCoins(coins); // Обновляем запись в базе
    }

    @Override
    public void spendCoins(int amount) { // Потратить монеты
        CoinsEntity coins = dao.getCoins(); // Получаем текущую запись
        if (coins != null && coins.amount >= amount) { // Если монет достаточно
            coins.amount -= amount; // Вычитаем нужное количество
            dao.insertCoins(coins); // Сохраняем обратно
        }
    }

    @Override
    public Map<Integer, Boolean> getAchievements() { // Получить все достижения
        return getUnlockMap("achievement"); // Возвращаем map достижений
    }

    @Override
    public void unlockAchievement(int id) { // Разблокировать достижение
        setUnlock("achievement", id); // Помечаем как разблокированное
    }


    @Override
    public Map<Integer, Boolean> getSkins() { // Получить все скины
        return getUnlockMap("skin"); // Возвращаем map скинов
    }

    @Override
    public void unlockSkin(int id) { // Разблокировать скин
        setUnlock("skin", id); // Обновляем состояние
    }

    private Map<Integer, Boolean> getUnlockMap(String type) { // Получить map для типа (скины/ачивки)
        List<UnlockStateEntity> list = dao.getUnlocksByType(type); // Получаем все записи по типу
        Map<Integer, Boolean> map = new HashMap<>(); // Создаём map
        for (UnlockStateEntity e : list) { // Проходим по каждой записи
            map.put(e.itemId, e.unlocked); // Кладем ID и статус (разблокировано или нет)
        }
        return map; // Возвращаем итоговый map
    }

    private void setUnlock(String type, int id) { // Установить разблокировку
        UnlockStateEntity e = dao.getUnlock(type, id); // Получаем запись
        if (e == null) { // Если ещё не существует
            e = new UnlockStateEntity(); // Создаем
            e.type = type; // Устанавливаем тип
            e.itemId = id; // Устанавливаем ID
        }
        e.unlocked = true; // Разблокируем
        dao.insertUnlock(e); // Сохраняем
    }
}
