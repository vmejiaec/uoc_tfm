package vmejiaec.com.citnpc.var;

public class UtilAlmacen {
    public static String publica(Almacen almacen){
        String res="";
        res += "\n" + almacen.nombre + "\n";
        res += UtilCofre.publicar( almacen.cofre);
        return res;
    }
}
