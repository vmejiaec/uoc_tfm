package uoc.tfm.vmejia.cbrplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class CBR extends JavaPlugin {

    @Override
    public void onEnable() {
        // Prepara la configuración del plugin CBR para acceder al archivo de configuración
        ConfigManager.setupConfig(this);
    }

    @Override
    public void onDisable() {    }
}
