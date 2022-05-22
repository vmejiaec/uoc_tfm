package uoc.tfm.vmejia.speedrun.ctrl;

import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import uoc.tfm.vmejia.speedrun.event.MarcadorEvent;
import uoc.tfm.vmejia.speedrun.var.Cofre;
import uoc.tfm.vmejia.speedrun.var.Material;

import java.util.UUID;

public class CtrlCofre {
    public static  boolean Retiro(Cofre cofre, Material.tipo material, int cantidad){
        boolean exito = false;
        switch (material){
            case CACAO:
                if(cofre.inv_cacao >= cantidad){
                    cofre.inv_cacao -= cantidad;
                    exito = true;
                }
                break;
            case HUEVO:
                if(cofre.inv_huevo >= cantidad){
                    cofre.inv_huevo -= cantidad;
                    exito = true;
                }
                break;
            case LECHE:
                if (cofre.inv_leche >= cantidad){
                    cofre.inv_leche -= cantidad;
                    exito = true;
                }
                break;
            case TRIGO:
                if (cofre.inv_trigo >= cantidad){
                    cofre.inv_trigo -= cantidad;
                    exito = true;
                }
                break;
            default:
                break;
        }
        return exito;
    }

    public static void Deposito(Cofre cofre, Material.tipo material, int cantidad){
        // Se deposita en el cofre seleccionado
        switch (material){
            case CACAO:
                cofre.inv_cacao += cantidad;
                break;
            case HUEVO:
                cofre.inv_huevo += cantidad;
                break;
            case LECHE:
                cofre.inv_leche += cantidad;
                break;
            case TRIGO:
                cofre.inv_trigo += cantidad;
                break;
            default:
                break;
        }
    }

    public static boolean Procesar( Cofre cofre){
        int ingr_c = cofre.receta.ingr_cacao;
        int ingr_h = cofre.receta.ingr_huevo;
        int ingr_l = cofre.receta.ingr_leche;
        int ingr_t = cofre.receta.ingr_trigo;
        int inv_c = cofre.inv_cacao;
        int inv_h = cofre.inv_huevo;
        int inv_l = cofre.inv_leche;
        int inv_t = cofre.inv_trigo;
        // Verifica si existen los ingredientes para fabricar el producto
        if(
                inv_c >= ingr_c &&
                inv_h >= ingr_h &&
                inv_l >= ingr_l &&
                inv_t >= ingr_t
        ){
            // Si hay suficientes ingredientes entonces creamos el producto
            // - Retiro los ingredientes
            CtrlCofre.Retiro(cofre, Material.tipo.CACAO,ingr_c);
            CtrlCofre.Retiro(cofre, Material.tipo.HUEVO,ingr_h);
            CtrlCofre.Retiro(cofre, Material.tipo.LECHE,ingr_l);
            CtrlCofre.Retiro(cofre, Material.tipo.TRIGO,ingr_t);
            // - Añadir los productos
            ++cofre.inv;
            return true;
        }
        return  false;
    }

    public static void BorrarContenido(Chest chest){
        int n = chest.getInventory().getContents().length;
        for (int i=0; i < n ; i++){
            ItemStack content = chest.getInventory().getContents()[i];
            if (content == null) continue;
            chest.getInventory().removeItem (content);
        }
    }

    public static void ColocarContenido(Chest chest, Cofre cofre){
        ItemStack itemStackCocoa = new ItemStack(org.bukkit.Material.COCOA_BEANS,cofre.inv_cacao);
        ItemStack itemStackHuevo = new ItemStack(org.bukkit.Material.EGG,cofre.inv_huevo);
        ItemStack itemStackLeche = new ItemStack(org.bukkit.Material.MILK_BUCKET,cofre.inv_leche);
        ItemStack itemStackTrigo = new ItemStack(org.bukkit.Material.WHEAT,cofre.inv_trigo);
        chest.getInventory().addItem(itemStackCocoa,itemStackHuevo,itemStackLeche,itemStackTrigo);
    }

    public static void PublicaContenido(Cofre cofre){
        System.out.print("Publica el contenido del cofre: "+cofre.nombre);
        Chest chest = (Chest) cofre.pos.getBlock().getState();
        CtrlCofre.BorrarContenido(chest);
        CtrlCofre.ColocarContenido(chest,cofre);
    }
}
