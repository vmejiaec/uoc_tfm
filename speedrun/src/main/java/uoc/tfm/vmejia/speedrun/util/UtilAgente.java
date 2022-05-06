package uoc.tfm.vmejia.speedrun.util;

import vmejiaec.com.citnpc.var.Agente;

public class UtilAgente {
    public static String publicar(Agente agente){
        String res ="\nAgente \n";
        res += "  - destino: "  + agente.destino + "\n";
        res += "  - producto: " + agente.producto + "\n";
        res += "  - material: " + agente.cantidad + " - " + agente.material + "\n";
        res += "  - BOLSA: " + UtilCofre.publicar(agente.bolsa);
        return res;
    }


}
