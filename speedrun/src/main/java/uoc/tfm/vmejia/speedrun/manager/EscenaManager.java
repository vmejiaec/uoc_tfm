package uoc.tfm.vmejia.speedrun.manager;

import org.bukkit.configuration.file.FileConfiguration;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.var.Receta;

public class EscenaManager {
    private FileConfiguration config;

    public EscenaManager(SpeedRun plugin){
        config = plugin.getConfig();
    }

    public Receta getRecetaPan(){
        return new Receta(
            config.getString("receta-pan.nombre"),
            config.getInt("receta-pan.inv_cacao"),
            config.getInt("receta-pan.inv_huevo"),
            config.getInt("receta-pan.inv_leche"),
            config.getInt("receta-pan.inv_trigo")
        );
    }

    public Receta getRecetaGalleta(){
        return new Receta(
                config.getString("receta-galleta.nombre"),
                config.getInt("receta-galleta.inv_cacao"),
                config.getInt("receta-galleta.inv_huevo"),
                config.getInt("receta-galleta.inv_leche"),
                config.getInt("receta-galleta.inv_trigo")
        );
    }

    public Receta getRecetaPastel(){
        return new Receta(
                config.getString("receta-pastel.nombre"),
                config.getInt("receta-pastel.inv_cacao"),
                config.getInt("receta-pastel.inv_huevo"),
                config.getInt("receta-pastel.inv_leche"),
                config.getInt("receta-pastel.inv_trigo")
        );
    }
}
