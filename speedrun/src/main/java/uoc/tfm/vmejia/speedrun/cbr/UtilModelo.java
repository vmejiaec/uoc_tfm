package uoc.tfm.vmejia.speedrun.cbr;

import uoc.tfm.vmejia.cbrplugin.Recomendar;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlAgente;
import uoc.tfm.vmejia.speedrun.var.Agente;
import uoc.tfm.vmejia.speedrun.var.Escena;
import uoc.tfm.vmejia.speedrun.var.MaterialModelo;

import java.util.Random;

public class UtilModelo {


    public static void estrategia(Recomendar reco, Escena escena){
        Random r = new Random();

        // Consulta al CBR
        String resultado = UtilCaso.ConsultaCBR(reco, escena);
        CtrlAgente.Configurar(escena.agente,resultado);

        // Factor aleatorio sobre el resultado CBR
        int sorteo = r.nextInt(2);
        System.out.println("Estrategia :" + sorteo);
        switch (sorteo){
            case 0:
                escena.camino = escena.caminoAlm1;
                escena.agente.destino = Agente.destinotipo.ALALMACEN1;
                break;
            case 1:
                escena.camino = escena.caminoAlm2;
                escena.agente.destino = Agente.destinotipo.ALALMACEN2;
                break;
        }
        sorteo = r.nextInt(4);
        switch (sorteo){
            case 0:
                escena.agente.material = MaterialModelo.tipo.CACAO;
                break;
            case 1:
                escena.agente.material = MaterialModelo.tipo.HUEVO;
                break;
            case 2:
                escena.agente.material = MaterialModelo.tipo.LECHE;
                break;
            case 3:
                escena.agente.material = MaterialModelo.tipo.TRIGO;
                break;
        }
        sorteo = r.nextInt(3);
        switch (sorteo){
            case 0:
                escena.agente.producto = MaterialModelo.tipo.PAN;
                break;
            case 1:
                escena.agente.producto = MaterialModelo.tipo.GALLETA;
                break;
            case 2:
                escena.agente.producto = MaterialModelo.tipo.PASTEL;
                break;
        }
    }
}
