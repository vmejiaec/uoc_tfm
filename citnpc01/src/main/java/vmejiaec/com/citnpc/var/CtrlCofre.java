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
}
