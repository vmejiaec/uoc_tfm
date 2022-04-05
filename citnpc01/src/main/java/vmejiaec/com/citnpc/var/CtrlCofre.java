package vmejiaec.com.citnpc.var;

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
                if(cofre.inv_cacao >= cantidad)
                    cofre.inv_cacao += cantidad;
                break;
            case HUEVO:
                if(cofre.inv_huevo >= cantidad)
                    cofre.inv_huevo += cantidad;
                break;
            case LECHE:
                if (cofre.inv_leche >= cantidad)
                    cofre.inv_leche += cantidad;
                break;
            case TRIGO:
                if (cofre.inv_trigo >= cantidad)
                    cofre.inv_trigo += cantidad;
                break;
            default:
                break;
        }
    }

    public static void Procesar(Cofre cofre){
        int pan_ingr_c = cofre.receta.ingredientes.inv_cacao;
        int pan_ingr_h = cofre.receta.ingredientes.inv_huevo;
        int pan_ingr_l = cofre.receta.ingredientes.inv_leche;
        int pan_ingr_t = cofre.receta.ingredientes.inv_trigo;
        int pan_inv_c = cofre.inv_cacao;
        int pan_inv_h = cofre.inv_huevo;
        int pan_inv_l = cofre.inv_leche;
        int pan_inv_t = cofre.inv_trigo;
        // Verifica si existen los ingredientes para fabricar el producto
        if(
                pan_inv_c >= pan_ingr_c &&
                pan_inv_h >= pan_ingr_h &&
                pan_inv_l >= pan_ingr_l &&
                pan_inv_t >= pan_ingr_t
        ){
            // Si hay suficientes ingredientes entonces creamos el producto
            // - Retiro los ingredientes
            CtrlCofre.Retiro(cofre, Material.tipo.CACAO,pan_ingr_c);
            CtrlCofre.Retiro(cofre, Material.tipo.HUEVO,pan_ingr_h);
            CtrlCofre.Retiro(cofre, Material.tipo.LECHE,pan_ingr_l);
            CtrlCofre.Retiro(cofre, Material.tipo.TRIGO,pan_ingr_t);
            // - Añador los productos
            ++cofre.inv;
        }
    }
}
