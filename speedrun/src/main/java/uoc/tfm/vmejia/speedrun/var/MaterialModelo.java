package uoc.tfm.vmejia.speedrun.var;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialModelo {

    public static enum tipo  {CACAO, HUEVO, LECHE, TRIGO,PAN, GALLETA, PASTEL }

    public static MaterialModelo.tipo equivale(Material material){
        switch (material){
            case EGG:
                return MaterialModelo.tipo.HUEVO;
            case COCOA_BEANS:
                return MaterialModelo.tipo.CACAO;
            case MILK_BUCKET:
                return MaterialModelo.tipo.LECHE;
            case WHEAT:
                return MaterialModelo.tipo.TRIGO;
        }
        return null;
    }
}
