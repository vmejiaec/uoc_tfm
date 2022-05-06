package uoc.tfm.vmejia.speedrun.util;

import uoc.tfm.vmejia.speedrun.var.Base;

public class UtilBase {
    public static String publicar(Base base){
        String res = "\nBase ";
        res += "  -- Objetivo: "+ base.objetivo +"-- \n";
        res += "    Cofre Pan: " + UtilCofre.publicar(base.cofrepan) + "\n";
        res += "    Cofre Galleta: " + UtilCofre.publicar(base.cofregalleta) + "\n";
        res += "    Cofre Pastel: " + UtilCofre.publicar(base.cofrepastel) + "\n";
        return res;
    }
}
