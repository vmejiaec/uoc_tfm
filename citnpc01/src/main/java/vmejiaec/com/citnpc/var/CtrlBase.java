package vmejiaec.com.citnpc.var;

public class CtrlBase {
    // Procesa el inventario del cofre para obtener los productos
    public static void procesa(Base base){
        // cofre del pan
        int pan_ingr_c = base.cofrepan.receta.ingredientes.inv_cacao;
        int pan_ingr_h = base.cofrepan.receta.ingredientes.inv_huevo;
        int pan_ingr_l = base.cofrepan.receta.ingredientes.inv_leche;
        int pan_ingr_t = base.cofrepan.receta.ingredientes.inv_trigo;
        int pan_inv_c = base.cofrepan.inv_cacao;
        int pan_inv_h = base.cofrepan.inv_huevo;
        int pan_inv_l = base.cofrepan.inv_leche;
        int pan_inv_t = base.cofrepan.inv_trigo;
        if(
            pan_inv_c >= pan_ingr_c &&
            pan_inv_h >= pan_ingr_h &&
            pan_inv_l >= pan_ingr_l &&
            pan_inv_t >= pan_ingr_t
        ){
            // Si hay suficientes ingredientes entonces creamos el producto
            // - Retiro los ingredientes
            CtrlCofre.Retiro(base.cofrepan, Material.tipo.CACAO,pan_ingr_c);
            CtrlCofre.Retiro(base.cofrepan, Material.tipo.HUEVO,pan_ingr_h);
            CtrlCofre.Retiro(base.cofrepan, Material.tipo.LECHE,pan_ingr_l);
            CtrlCofre.Retiro(base.cofrepan, Material.tipo.TRIGO,pan_ingr_t);
            // - Añador los productos
            ++base.cofrepan.inv;
        }
    }
}
