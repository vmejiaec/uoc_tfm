package uoc.tfm.vmejia.speedrun.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class UtilLocation {
    public static String ToString(Location loc)
    {
        String res="";
        res += " x: " + loc.getX();
        res += " y: " + loc.getY();
        res += " z: " + loc.getZ();
        return res;
    }

    public static Location getLoc(World world, Vector vec){
        return new Location(world, vec.getX(), vec.getY(), vec.getZ());
    }
}
