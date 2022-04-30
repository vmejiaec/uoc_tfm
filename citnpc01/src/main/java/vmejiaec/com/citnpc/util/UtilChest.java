package vmejiaec.com.citnpc.util;

import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class UtilChest {
    // Imprime el contenido del chest
    public static String ToString(Chest chest){
        String res= "";
        int n = chest.getInventory().getSize();
        for (int i=0; i < n ; i++){
            ItemStack content = chest.getInventory().getContents()[i];
            if (content !=  null){
               res += " "+ i + "/" + n
                    + ": "+ content.getType()
                    + " " + content.getAmount() + ";";
            }
        }
        return res;
    }
}
