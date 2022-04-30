package vmejiaec.com.citnpc.util;

import vmejiaec.com.citnpc.var.Cofre;

public class UtilCofre {
    public static String publicar(Cofre cofre){
        String res = "";
        res += "Inv: " + cofre.inv;
        res += " (";
        res += " c: " + cofre.inv_cacao ;
        res += " h: " + cofre.inv_huevo ;
        res += " l: " + cofre.inv_leche ;
        res += " t: " + cofre.inv_trigo ;
        res += " )";
        return  res;
    }
}
