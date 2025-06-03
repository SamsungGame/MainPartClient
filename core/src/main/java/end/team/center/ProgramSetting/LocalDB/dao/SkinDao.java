package end.team.center.ProgramSetting.LocalDB.dao;


import java.util.List;

import end.team.center.ProgramSetting.LocalDB.models.Skin;

public interface SkinDao {
    void insertSkin(Skin skin);
    List<Skin> getAllSkins();
    Skin getSkinById(int id);
}
