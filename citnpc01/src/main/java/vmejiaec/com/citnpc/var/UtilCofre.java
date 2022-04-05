package vmejiaec.com.citnpc.var;

public class UtilCofre {
    public static String publicar(Cofre cofre){
        String res = "inv";
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
