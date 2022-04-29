package uoc.tfm.vmejia.cbrplugin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CBR extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getWord(){ return "banana";}

    public  void sendMessage(Player player) {
        player.sendMessage("Saludos desde cbr plugin");
    }
}
