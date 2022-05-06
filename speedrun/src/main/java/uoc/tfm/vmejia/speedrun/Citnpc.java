package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Citnpc extends JavaPlugin {

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

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
