package uoc.tfm.vmejia.speedrun.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.var.Almacen;
import uoc.tfm.vmejia.speedrun.var.Cofre;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public List<Arena> getArenas (){return arenas;}

    public ArenaManager(SpeedRun minigame){
        FileConfiguration config = minigame.getConfig();
        for(String str: config.getConfigurationSection("arenas.").getKeys(false)){
            System.out.println( " - World: "+ str + " - " + config.getString("arenas."+str+".world"));
            World world = Bukkit.createWorld(new WorldCreator(config.getString("arenas."+str+".world")));
            world.setAutoSave(false);

            /*
            System.out.println( "Datos de la arena "+ str + " " +
                    config.getString("arenas."+str+".sign.world")+ " " +
                    config.getDouble("arenas."+str+".sign.x")+ " " +
                    config.getDouble("arenas."+str+".sign.y")+ " " +
                    config.getDouble("arenas."+str+".sign.z"));
             */

            arenas.add(new Arena(
                    minigame,
                    Integer.parseInt(str),
                    new Location(
                            world,
                            config.getDouble("arenas."+str+".player-spawn.x"),
                            config.getDouble("arenas."+str+".player-spawn.y"),
                            config.getDouble("arenas."+str+".player-spawn.z"),
                            (float) config.getDouble("arenas."+str+".player-spawn.yaw"),
                            (float) config.getDouble("arenas."+str+".player-spawn.pitch")
                    ),
                    new Location(
                            world,
                            config.getDouble("arenas."+str+".sign.x"),
                            config.getDouble("arenas."+str+".sign.y"),
                            config.getDouble("arenas."+str+".sign.z")
                    ),
                    new Location(
                            world,
                            config.getDouble("arenas."+str+".sign-exit.x"),
                            config.getDouble("arenas."+str+".sign-exit.y"),
                            config.getDouble("arenas."+str+".sign-exit.z")
                    ),
                    new Location(
                            world,
                            config.getDouble("arenas."+str+".npc-spawn.x"),
                            config.getDouble("arenas."+str+".npc-spawn.y"),
                            config.getDouble("arenas."+str+".npc-spawn.z")
                    ),
                    new Location(
                            world,
                            config.getDouble("arenas."+str+".almacen1.x"),
                            config.getDouble("arenas."+str+".almacen1.y"),
                            config.getDouble("arenas."+str+".almacen1.z")
                    ),
                    new Location(
                            world,
                            config.getDouble("arenas."+str+".almacen2.x"),
                            config.getDouble("arenas."+str+".almacen2.y"),
                            config.getDouble("arenas."+str+".almacen2.z")
                    ),
                    new Almacen(
                            "Almacen1",
                            config.getInt("arenas."+str+".almacen1.inv_cacao"),
                            config.getInt("arenas."+str+".almacen1.inv_huevo"),
                            config.getInt("arenas."+str+".almacen1.inv_leche"),
                            config.getInt("arenas."+str+".almacen1.inv_trigo")
                    ),
                    new Almacen(
                            "Almacen2",
                            config.getInt("arenas."+str+".almacen2.inv_cacao"),
                            config.getInt("arenas."+str+".almacen2.inv_huevo"),
                            config.getInt("arenas."+str+".almacen2.inv_leche"),
                            config.getInt("arenas."+str+".almacen2.inv_trigo")
                    )
            ));

        }
    }

    public Arena getArena(Player player){
        for(Arena arena: arenas){
            if(arena.getPlayers().contains(player.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(UUID uuid){
        for(Arena arena: arenas){
            if(arena.getPlayers().contains(uuid)){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for (Arena arena: arenas){
            if(arena.getId() == id){
                return arena;
            }
        }
        return  null;
    }

    public Arena getArena(World world){
        for(Arena arena : arenas){
            if(arena.getWorld().getName().equals(world.getName())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(Location sign){
        for(Arena arena : arenas){
            if(arena.getSignLocation().equals(sign)){
                return arena;
            }
        }
        return null;
    }

    public Arena getArenaByLocSignExit(Location signExit){
        for(Arena arena : arenas){
            if(arena.getSignExitLocation().equals(signExit)){
                return arena;
            }
        }
        return null;
    }
}
