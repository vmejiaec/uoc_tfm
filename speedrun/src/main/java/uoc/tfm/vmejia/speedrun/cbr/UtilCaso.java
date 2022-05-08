package uoc.tfm.vmejia.speedrun.cbr;

import de.dfki.mycbr.util.Pair;
import uoc.tfm.vmejia.cbrplugin.Recomendar;
import uoc.tfm.vmejia.speedrun.var.Escena;

import java.util.Locale;

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

    public static String ConsultaCBR(Recomendar reco, Escena escena){
        Caso caso = new Caso( escena.baseAgente.objetivo.toLowerCase(Locale.ROOT));
        caso.alm1 = escena.almacenIzq;
        caso.alm2 = escena.almacenDer;
        caso.cofre_pan = escena.baseAgente.cofrepan;
        caso.cofre_galleta = escena.baseAgente.cofregalleta;
        caso.cofre_pastel = escena.baseAgente.cofrepastel;

        // --------------------------------------------------------------------------------------
        String[] nombres = new String[]{
                "alm1-inv-cacao","alm1-inv-huevo","alm1-inv-leche","alm1-inv-trigo",
                "alm2-inv-cacao","alm2-inv-huevo","alm2-inv-leche","alm2-inv-trigo",
                "cofre-galleta",
                "cofre-galleta-ingr-cacao","cofre-galleta-ingr-huevo","cofre-galleta-ingr-leche","cofre-galleta-ingr-trigo",
                "cofre-pan",
                "cofre-pan-ingr-cacao","cofre-pan-ingr-huevo","cofre-pan-ingr-leche","cofre-pan-ingr-trigo",
                "cofre-pastel",
                "cofre-pastel-ingr-cacao","cofre-pastel-ingr-huevo","cofre-pastel-ingr-leche","cofre-pastel-ingr-trigo"
        };
        int[] valores = UtilCaso.getValores(caso);
        // --------------------------------------------------------------------------------------

        Pair res = reco.solveOuery(caso.a_objetivo, nombres, valores, 1);

        System.out.println("Mejor caso: "+res.getFirst() + " " +res.getSecond());

        return ""+ res.getFirst();
    }
}
