package uoc.tfm.vmejia.speedrun.cbr;

import uoc.tfm.vmejia.cbrplugin.Recomendar;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlAgente;
import uoc.tfm.vmejia.speedrun.var.Agente;
import uoc.tfm.vmejia.speedrun.var.Escena;
import uoc.tfm.vmejia.speedrun.var.Material;

import java.util.Random;

public class UtilModelo {

    // Inicializa el cbr para realizar las consultas
    public static void inicializarCBR(Recomendar reco){
        System.out.println(" -- -- Inicializa el CBR con los casos");
        reco = new Recomendar();
        reco.loadengine();
    }

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
                escena.agente.material = Material.tipo.CACAO;
                break;
            case 1:
                escena.agente.material = Material.tipo.HUEVO;
                break;
            case 2:
                escena.agente.material = Material.tipo.LECHE;
                break;
            case 3:
                escena.agente.material = Material.tipo.TRIGO;
                break;
        }
        sorteo = r.nextInt(3);
        switch (sorteo){
            case 0:
                escena.agente.producto = Material.tipo.PAN;
                break;
            case 1:
                escena.agente.producto = Material.tipo.GALLETA;
                break;
            case 2:
                escena.agente.producto = Material.tipo.PASTEL;
                break;
        }
    }
}
