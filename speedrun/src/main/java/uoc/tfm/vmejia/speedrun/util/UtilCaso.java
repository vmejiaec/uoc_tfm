package uoc.tfm.vmejia.speedrun.util;

import uoc.tfm.vmejia.speedrun.var.Caso;

public class UtilCaso {
    public static int[] getValores(Caso caso){
        int[] valores = new int[]{
            caso.alm1.cofre.inv_cacao, caso.alm1.cofre.inv_huevo, caso.alm1.cofre.inv_leche, caso.alm1.cofre.inv_trigo,
            caso.alm2.cofre.inv_cacao, caso.alm2.cofre.inv_huevo, caso.alm2.cofre.inv_leche, caso.alm2.cofre.inv_trigo,
            caso.cofre_galleta.inv,
            caso.cofre_galleta.inv_cacao,caso.cofre_galleta.inv_huevo, caso.cofre_galleta.inv_leche, caso.cofre_galleta.inv_trigo,
            caso.cofre_pan.inv,
            caso.cofre_pan.inv_cacao, caso.cofre_pan.inv_huevo, caso.cofre_pan.inv_leche,caso.cofre_pan.inv_trigo,
            caso.cofre_pastel.inv,
            caso.cofre_pastel.inv_cacao, caso.cofre_pastel.inv_huevo,caso.cofre_pastel.inv_leche,caso.cofre_pastel.inv_trigo
        };
        return valores;
    }

}
