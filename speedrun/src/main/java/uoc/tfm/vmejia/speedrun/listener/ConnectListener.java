package uoc.tfm.vmejia.speedrun.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

public class ConnectListener implements Listener {
    private SpeedRun minigame;

    public ConnectListener(SpeedRun minigame){
        this.minigame = minigame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(ConfigManager.getLobbySpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());
        if(arena != null){
            arena.removePlayer(e.getPlayer());
        }
    }
}
