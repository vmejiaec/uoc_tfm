package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.speedrun.command.ArenaCommand;
import uoc.tfm.vmejia.speedrun.listener.ConnectListener;
import uoc.tfm.vmejia.speedrun.listener.GameListener;
import uoc.tfm.vmejia.speedrun.manager.ArenaManager;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

import java.util.logging.Level;

public final class SpeedRun extends JavaPlugin {

    private ArenaManager arenaManager;

    public ArenaManager getArenaManager(){return arenaManager;}

    @Override
    public void onEnable() {

        // Registra la clase con los eventos
        getServer().getPluginManager().registerEvents(new Events(), this);

        // Plugin startup logic
        System.out.println("++++ Registro del comando para crear citizen");
        getCommand("crearnpc").setExecutor(new CrearNPC(this));

        System.out.println("++++ Registro del comando para mover citizen");
        getCommand("movernpc").setExecutor(new MoverNPC(this));


        //This is your bukkit plugin class. Use it to hook your trait into Citizens and handle any commands.
        if(getServer().getPluginManager().getPlugin("Citizens") == null
                || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Register your trait with Citizens.
        CitizensAPI.getTraitFactory()
                .registerTrait(TraitInfo.create(MyTrait.class).withName("mytraitname"));

        // Prepara la configuración del juego
        ConfigManager.setupConfig(this);
        // Prepara el gestor de arenas
        System.out.println("Crear ArenaManager");
        arenaManager = new ArenaManager(this);

        // Registra los eventos
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this),this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        // Registra los comandos
        getCommand("arena").setExecutor(new ArenaCommand(this));

    }

}
