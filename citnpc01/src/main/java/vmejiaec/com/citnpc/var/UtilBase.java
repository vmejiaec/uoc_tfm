package vmejiaec.com.citnpc.var;

public class UtilBase {
    public static String publicar(Base base){
        String res = "Base \n";
        res += "  -- Objetivo: "+ base.objetivo +"-- \n";
        res += "  Cofre Pan: " + UtilCofre.publicar(base.cofrepan) + "\n";
        res += "  Cofre Galleta: " + UtilCofre.publicar(base.cofregalleta) + "\n";
        res += "  Cofre Pastel: " + UtilCofre.publicar(base.cofrepastel) + "\n";
        return res;
    }
}
