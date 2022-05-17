package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.speedrun.command.ArenaCommand;
import uoc.tfm.vmejia.speedrun.listener.ConnectListener;
import uoc.tfm.vmejia.speedrun.listener.GameListener;
import uoc.tfm.vmejia.speedrun.manager.ArenaManager;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;
import uoc.tfm.vmejia.speedrun.manager.EscenaManager;

import java.util.logging.Level;

public final class SpeedRun extends JavaPlugin {

    private ArenaManager arenaManager;
    private EscenaManager escenaManager;

    private boolean JuegoEnMarcha;
    private boolean JuegoReinicio;
    private NPC npc;

    public ArenaManager getArenaManager(){return arenaManager;}
    public EscenaManager getEscenaManager(){return escenaManager;}

    public boolean IsJuegoEnMarcha(){return JuegoEnMarcha;}
    public void setJuegoEnMarcha(boolean estado){this.JuegoEnMarcha = estado;}
    public Boolean getJuegoReinicio(){return JuegoReinicio;}
    public void setJuegoReinicio(boolean estado){this.JuegoReinicio = estado;}

    public void setNPC (NPC npc){this.npc = npc;}
    public NPC getNPC (){return this.npc;}

    @Override
    public void onEnable() {
        // Registra la clase con los eventos
        getServer().getPluginManager().registerEvents(new Events(), this);

        // Verifica que el plugin de Citizens se encuentre activo.
        if( getServer().getPluginManager().getPlugin("Citizens") == null
            ||
            !getServer().getPluginManager().getPlugin("Citizens").isEnabled())
        {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        //Register your trait with Citizens.
        CitizensAPI.getTraitFactory()
                .registerTrait(TraitInfo.create(MyTrait.class).withName("mytraitname"));
        Trait trait = CitizensAPI.getTraitFactory().getTrait("mytraitname");
        System.out.println("CitizensAPI el trait es: "+ trait.getName());

        // Prepara la configuración del juego
        ConfigManager.setupConfig(this);
        // Prepara el gestor de arenas
        System.out.println("Crear ArenaManager");
        arenaManager = new ArenaManager(this);
        escenaManager = new EscenaManager(this);

        // Registra los eventos
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this),this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        // Registra los comandos
        getCommand("arena").setExecutor(new ArenaCommand(this));

        // Control de inicio del juego en la arena para el Agente NPC
        JuegoEnMarcha = false;
        JuegoReinicio = true;


    }

}
