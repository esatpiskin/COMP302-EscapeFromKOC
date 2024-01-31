package model.services.save.object;

import java.io.Serializable;
import java.util.List;

public class Save implements Serializable {

    String playerName;
    String gameState;
    int remainingTime;

    PlayerSave player;

    List<BuildingSave> buildings;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public PlayerSave getPlayer() {
        return player;
    }

    public void setPlayer(PlayerSave player) {
        this.player = player;
    }

    public List<BuildingSave> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingSave> buildings) {
        this.buildings = buildings;
    }

    @Override
    public String toString() {
        return "Save{" +
                "playerName='" + playerName + '\'' +
                ", gameState='" + gameState + '\'' +
                ", player=" + player +
                ", buildings=" + buildings +
                '}';
    }
}
