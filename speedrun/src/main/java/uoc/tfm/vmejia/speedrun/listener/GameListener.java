package uoc.tfm.vmejia.speedrun.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.WorldLoadEvent;
import uoc.tfm.vmejia.speedrun.GameState;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.instance.Arena;

public class GameListener implements Listener {

    private SpeedRun minigame;

    public GameListener(SpeedRun minigame){
        this.minigame = minigame;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Arena arena = minigame.getArenaManager().getArena(e.getPlayer());
        if(arena != null && arena.getState().equals(GameState.LIVE)){
            arena.getGame().addPoint(e.getPlayer());
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){
        Arena arena = minigame.getArenaManager().getArena(e.getWorld());
        if(arena != null){
            arena.toggleCanJoin();
        }
    }
}
