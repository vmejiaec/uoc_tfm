package uoc.tfm.vmejia.speedrun;

import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.speedrun.manager.ArenaManager;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

public final class SpeedRun extends JavaPlugin {

    private ArenaManager arenaManager;

    public ArenaManager getArenaManager(){return arenaManager;}

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);
    }

}
