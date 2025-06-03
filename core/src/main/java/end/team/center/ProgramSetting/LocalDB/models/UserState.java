package end.team.center.ProgramSetting.LocalDB.models;

public class UserState {
    private int coins;  // убрали "final"

    public UserState(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
