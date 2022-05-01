package uoc.tfm.vmejia.speedrun.instance;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import uoc.tfm.vmejia.speedrun.GameState;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private int id;
    private Location spawn;
    private List<UUID> players;
    private GameState state;
    private Countdown countdown;

    public Arena(SpeedRun minigame, int id, Location spawn){
        this.id = id;
        this.spawn = spawn;
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        // El conteo atrás para iniciar el juego
        this.countdown = new Countdown(minigame, this);
    }

    public int getId() {
        return id;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public Location getSpawn() {
        return spawn;
    }

    public GameState getState(){
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    /* GAME */

    public void start(){

    }

    /* PLAYERS */

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
    }

    /* TOOLS */

    public void sendMessage(String message){
        for(UUID uuid:players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendMessage(String title, String subtitle){
        for(UUID uuid:players){
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    /* INFO */
}
