package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.cbrplugin.Recomendar;
import uoc.tfm.vmejia.speedrun.cbr.UtilModelo;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlAgente;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlBase;
import uoc.tfm.vmejia.speedrun.util.UtilAgente;
import uoc.tfm.vmejia.speedrun.util.UtilAlmacen;
import uoc.tfm.vmejia.speedrun.util.UtilBase;
import uoc.tfm.vmejia.speedrun.var.Escena;

public class MyTrait extends Trait {

    SpeedRun plugin;
    World world ;
    Recomendar reco;
    Escena escena;

    public MyTrait() {
        super("mytraitname");
        plugin = JavaPlugin.getPlugin(SpeedRun.class);
        escena = new Escena();
        reco = new Recomendar();
    }

    // Coloca el destino al NPC que le corresponde
    int posactual = 0;
    // El estado significa si se encuentra en la base
    boolean estaenbase = true;

    public void movetonextpos(){
        if (posactual == 0 ) posactual = 1 ; else posactual = 0;
        npc.getNavigator().setTarget(new Location(
                world,
                escena.camino.get(posactual).getX(),
                escena.camino.get(posactual).getY(),
                escena.camino.get(posactual).getZ()
        ));
    }

    @EventHandler
    public void navigationcomplete( NavigationCompleteEvent event){
        // Confirma que ha llegado al destino
        double distancia =  escena.camino.get(posactual).distance(npc.getStoredLocation().toVector())  ;
        //System.out.println(" -- Distancia del Npc al destino: "+distancia);
        if (distancia <= 3 ){ // llegó al destino
            trigerbeginmove = true;
            estaenbase = posactual == 0;   // Si está en la base es true
            if (estaenbase){  // está en la base
                System.out.print("Estoy en la base!");
                // Deja el material que lleva en la bolsa
                switch (escena.agente.producto){
                    case PAN:
                        CtrlAgente.Deja(escena.agente,escena.baseAgente.cofrepan);
                        break;
                    case GALLETA:
                        CtrlAgente.Deja(escena.agente,escena.baseAgente.cofregalleta);
                        break;
                    case PASTEL:
                        CtrlAgente.Deja(escena.agente,escena.baseAgente.cofrepastel);
                        break;
                }
                escena.baseAgente.objetivo = ""+escena.agente.producto;
                // Procesa los productos de los cofres de la base
                CtrlBase.procesa(escena.baseAgente);
                // Publica el estado de la base
                System.out.println(UtilBase.publicar(escena.baseAgente));
                // Elije la estrategia
                UtilModelo.estrategia(reco, escena);
            } else {  // está en el almacen
                System.out.print("Estoy en el almacén!"+escena.agente.destino);
                // Toma el material
                switch (escena.agente.destino){
                    case ALALMACEN1:
                        CtrlAgente.Toma(escena.agente,escena.almacenIzq);
                        System.out.println(UtilAlmacen.publica(escena.almacenIzq));
                        break;
                    case ALALMACEN2:
                        CtrlAgente.Toma(escena.agente,escena.almacenDer);
                        System.out.println(UtilAlmacen.publica(escena.almacenDer));
                        break;
                }
                // Se publica al agente
                System.out.println(UtilAgente.publicar(escena.agente));
            }
        } else {              // aun no llega al destino
            if (posactual == 0 ) posactual = 1 ; else posactual = 0;
            trigerbeginmove = true;
        }
    }

    @EventHandler
    public void navigationcancel( NavigationCancelEvent event){
        System.out.println("  -- (X) Evento de navegación CANCEL");
        System.out.println("  -- (X) -- Reason: "+event.getCancelReason().toString());
        // Si se cancela el destino, se vuelve a relanzar el movimiento
        trigerbeginmove = true;
    }

    // para control del reloj
    int n_tick = 0;
    int n_tick_max = 50;
    // Para volver a relanzar el movimiento
    boolean trigerbeginmove = true;
    // Bandera para la iniciar el relog
    boolean start = true;

    @Override
    public void run() {
        // Una sola vez al empezar
        if (plugin.getJuegoReinicio()){
            start = false;
            plugin.setJuegoReinicio(false);
            //
            n_tick = 0;
            trigerbeginmove = true;
            escena.camino = escena.caminoAlm2;
            // Iniciar el motor del cbr
            System.out.println(" -- -- Inicializa el CBR con los casos");
            reco = new Recomendar();
            reco.loadengine();
            //
            escena.inicializarAlamcenesYCofres();
            // Colocar al agente en el inicio
            world = npc.getStoredLocation().getWorld();
            System.out.println(" -- -- Coloca al agente en la base inicial");
            System.out.println(" -- -- World: "+world);
            System.out.println(" -- -- Escena: "+escena);
            Location locBaseAgente =
                    new Location(world,escena.vecbase1.getX(), escena.vecbase1.getY(),escena.vecbase1.getZ());
            System.out.println(" -- -- NPC: "+npc);
            System.out.println(" -- -- Location: "+locBaseAgente);
            System.out.println(" -- -- NPC Entity: "+npc.getEntity());
            if(npc.getEntity() != null) {
                npc.getEntity().teleport(locBaseAgente);
            }
        }

        // Si la arena ya empezó el juego
        if(plugin.IsJuegoEnMarcha()) {
            n_tick++; // El tick tack del reloj
            if (n_tick > n_tick_max) {
                //System.out.println(" -- Está navegando?: "+npc.getNavigator().isNavigating() );
                n_tick = 0; // Reinicio del reloj

                // Si no ha empezado el viaje, lo inicia
                if (trigerbeginmove) {
                    trigerbeginmove = false;
                    movetonextpos();
                }
            }
        }
    }

    @Override
    public void onAttach() {
        plugin.getServer().getLogger().info(npc.getName() + " ha sido asignado a MyTrait!");
    }

    @Override
    public void onDespawn() {
    }

    @Override
    public void onSpawn() {
        System.out.print(" El agente NPC es colocado en escena --------(*)");
        String npcname = npc.getName();
        world = npc.getStoredLocation().getWorld();
        System.out.print("   --- El npc se llama: " + npcname);
    }

    @Override
    public void onRemove() {
    }

}