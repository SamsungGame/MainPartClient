package end.team.center.LocalDB.dao;


import java.util.List;

import end.team.center.LocalDB.models.Skin;

public interface SkinDao {
    void insertSkin(Skin skin);
    List<Skin> getAllSkins();
    Skin getSkinById(int id);
}
