package uoc.tfm.vmejia.speedrun;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import uoc.tfm.vmejia.speedrun.util.UtilChest;
import uoc.tfm.vmejia.speedrun.util.UtilLocation;

import java.util.HashMap;

public class Events implements Listener {

    @EventHandler
    public void PlayerRightClick(org.bukkit.event.player.PlayerInteractEvent event){
        Block bloque = event.getClickedBlock();

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) return;

        System.out.println("Bloque tipo: "+bloque.getType());

        if(bloque.getType().equals(Material.CHEST)){
            System.out.print("Si es un cofre!!");
            //BlockData bloquedata = bloque.getBlockData();

            Location bloqueLoc = bloque.getLocation();
            System.out.print(UtilLocation.ToString(bloqueLoc));

            Chest chest = (Chest) bloque.getState();
            System.out.print(UtilChest.ToString(chest));

            // Tomo un item
            System.out.println("- Quito un item");
            int cantRetiro = 4;
            Material materialBuscado = Material.WHEAT;
            int n = chest.getInventory().getContents().length;
            for (int i=0; i < n ; i++){
                ItemStack content = chest.getInventory().getContents()[i];
                if (content == null) continue;
                if (content.getType() ==  materialBuscado){
                    System.out.print(" Encontrado - i: "+i + "/" + n);
                    System.out.print(": "+content.getType() + " " + content.getAmount());
                }
            }

            System.out.println("Ejecuto retiro");
            HashMap<Integer ,ItemStack> z =
                    chest.getInventory().removeItem(
                            new ItemStack(materialBuscado,cantRetiro));
            System.out.println("Luego de ejecutar el retiro");
            if (z == null) System.out.println("z es nulo!!");
            for(Integer i : z.keySet()){
                System.out.println(z.get(i).getType().name() + ": " + z.get(i).getAmount());
            }

            //ItemStack[] x = chest.getInventory().getStorageContents();
            //chest.getInventory().setStorageContents(x);
            //System.out.println(utilChest.ToString(chest));

        }

        // Cuando es un letrero
        if(bloque.getType().equals(Material.BIRCH_WALL_SIGN)){
            System.out.println("Es un LETRERO!!");
            BlockData bloquedata = bloque.getBlockData();
            Location bloqueLoc = bloque.getLocation();
            System.out.println(UtilLocation.ToString(bloqueLoc));
            Sign letrero = (Sign) bloque.getState();
            letrero.setLine(1,"EXITO!!");
            letrero.update();
            //letrero.notify();
        }
    }
}
