package vmejiaec.com.citnpc.var;

public class UtilAlmacen {
    public static String publica(Almacen almacen){
        String res="";
        res += ""+almacen.nombre ;
        res += " cacao: " + almacen.cofre.inv_cacao + "\n";
        res += " huego: " + almacen.cofre.inv_huevo + "\n";
        res += " leche: " + almacen.cofre.inv_leche + "\n";
        res += " trigo: " + almacen.cofre.inv_trigo + "\n";
        return res;
    }
}
