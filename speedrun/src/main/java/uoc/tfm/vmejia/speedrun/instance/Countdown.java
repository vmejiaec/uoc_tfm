package uoc.tfm.vmejia.speedrun.instance;

import org.bukkit.scheduler.BukkitRunnable;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

public class Countdown extends BukkitRunnable {

    private SpeedRun minigame;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(SpeedRun minigame, Arena arena){
        this.minigame = minigame;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start(){
        
    }

    @Override
    public void run() {

    }

}
