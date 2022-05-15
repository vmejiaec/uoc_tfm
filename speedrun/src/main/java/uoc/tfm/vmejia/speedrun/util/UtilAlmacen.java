package uoc.tfm.vmejia.speedrun.util;

import uoc.tfm.vmejia.speedrun.var.Almacen;

public class UtilAlmacen {
    public static String publica(Almacen almacen){
        String res="";
        if(almacen != null){
            res += "\n" + almacen.nombre + "\n";
            res += UtilCofre.publicar( almacen.cofre);
        }
        return res;
    }
}
