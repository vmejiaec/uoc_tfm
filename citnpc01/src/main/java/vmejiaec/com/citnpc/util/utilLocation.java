package vmejiaec.com.citnpc.util;

import org.bukkit.Location;

public class utilLocation {
    public static String ToString(Location loc)
    {
        String res="";
        res += " x: " + loc.getX();
        res += " y: " + loc.getY();
        res += " z: " + loc.getZ();
        return res;
    }
}
