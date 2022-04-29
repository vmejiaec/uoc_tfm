package uoc.tfm.vmejia.speedrun;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.cbrplugin.CBR;
import uoc.tfm.vmejia.cbrplugin.Recomendar;

public final class SpeedRun extends JavaPlugin implements Listener {

    CBR cbr;

    @Override
    public void onEnable() {
        // Plugin startup logic
        cbr = (CBR) Bukkit.getPluginManager().getPlugin("CbrPlugin");
        System.out.println(cbr.getWord());

        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        cbr.sendMessage(e.getPlayer());
        System.out.println(cbr.getWord());

        Recomendar rec = new Recomendar();

        rec.loadengine();
    }
}
