package uoc.tfm.vmejia.speedrun;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.speedrun.listener.ConnectListener;
import uoc.tfm.vmejia.speedrun.listener.GameListener;
import uoc.tfm.vmejia.speedrun.manager.ArenaManager;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

public final class SpeedRun extends JavaPlugin {

    private ArenaManager arenaManager;

    public ArenaManager getArenaManager(){return arenaManager;}

    @Override
    public void onEnable() {
        // Prepara la configuración del juego
        ConfigManager.setupConfig(this);
        // Prepara el getor de arenas
        arenaManager = new ArenaManager(this);

        // Registra los eventos
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this),this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

    }

}
