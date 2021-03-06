package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.cbrplugin.Recomendar;
import uoc.tfm.vmejia.speedrun.cbr.UtilModelo;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlAgente;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlBase;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlCofre;
import uoc.tfm.vmejia.speedrun.event.MarcadorEvent;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.instance.GUI;
import uoc.tfm.vmejia.speedrun.util.UtilAgente;
import uoc.tfm.vmejia.speedrun.util.UtilAlmacen;
import uoc.tfm.vmejia.speedrun.util.UtilBase;
import uoc.tfm.vmejia.speedrun.util.UtilEscena;
import uoc.tfm.vmejia.speedrun.var.Escena;

import java.util.UUID;

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
        if (distancia <= 3 ){ // lleg?? al destino
            trigerbeginmove = true;
            estaenbase = posactual == 0;   // Si est?? en la base es true
            if (estaenbase){  // est?? en la base
                System.out.print("Estoy en la base!");
                // Deja el material que lleva en la bolsa
                switch (escena.agente.producto){
                    case PAN:
                        CtrlAgente.Deja(escena.agente,escena.baseNPC.cofrepan);
                        break;
                    case GALLETA:
                        CtrlAgente.Deja(escena.agente,escena.baseNPC.cofregalleta);
                        break;
                    case PASTEL:
                        CtrlAgente.Deja(escena.agente,escena.baseNPC.cofrepastel);
                        break;
                }
                escena.baseNPC.objetivo = ""+escena.agente.producto;
                // Procesa los productos de los cofres de la base
                System.out.println("Calcula que haya al menos un producto producido >>>>");
                boolean resProceso = CtrlBase.procesa(escena.baseNPC);
                // Si se puede producir un producto, se reporta al marcador
                if (resProceso){
                    MarcadorEvent marcadorEvent = new MarcadorEvent(npc.getUniqueId(), " Se marc?? un: ");
                    Bukkit.getPluginManager().callEvent(marcadorEvent);
                }
                // Publica el estado de la base
                System.out.println(UtilBase.publicar(escena.baseNPC));
                CtrlCofre.PublicaContenido(escena.baseNPC.cofrepan);
                CtrlCofre.PublicaContenido(escena.baseNPC.cofregalleta);
                CtrlCofre.PublicaContenido(escena.baseNPC.cofrepastel);
                // Publica el marcador
                Arena arena = plugin.getArenaManager().getArena(npc.getUniqueId());
                if(arena != null){
                    UUID playerUUID = null;
                    for (UUID uuid : arena.getPlayers()) {
                        if(uuid.equals(npc.getUniqueId())){

                        } else {
                            playerUUID = uuid;
                        }
                    }
                    Player player = Bukkit.getPlayer(playerUUID);
                    GUI.PublicarMarcador(player,escena);
                }
                // Elije la estrategia
                UtilModelo.estrategia(reco, escena);
            } else {  // est?? en el almacen
                System.out.print("Estoy en el almac??n!"+escena.agente.destino);
                // Toma el material
                switch (escena.agente.destino){
                    case ALALMACEN1:
                        CtrlAgente.Toma(escena.agente,escena.almacenIzq);
                        System.out.println(UtilAlmacen.publica(escena.almacenIzq));
                        CtrlCofre.PublicaContenido(escena.almacenIzq.cofre);
                        break;
                    case ALALMACEN2:
                        CtrlAgente.Toma(escena.agente,escena.almacenDer);
                        System.out.println(UtilAlmacen.publica(escena.almacenDer));
                        CtrlCofre.PublicaContenido(escena.almacenDer.cofre);
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
        System.out.println("  -- (X) Evento de navegaci??n CANCEL");
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
    // Bandera para inicializar los contenidos de los cofres
    boolean configCofresIni = true;

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
            configCofresIni = true;
            // Colocar al agente en el inicio
            world = npc.getStoredLocation().getWorld();
            System.out.println(" -- -- Coloca al agente en la base inicial");
            Location locBaseAgente =
                    new Location(world,escena.vecBaseNPC.getX(), escena.vecBaseNPC.getY(),escena.vecBaseNPC.getZ());
            if(npc.getEntity() != null) {
                npc.getEntity().teleport(locBaseAgente);
                posactual = 1;
                movetonextpos();
            }
        }

        // Si la arena ya empez?? el juego
        if(plugin.IsJuegoEnMarcha()) {
            if(configCofresIni){
                // configura la escena con la arena del player
                System.out.println("************* Configuraci??n de la Escena con la arena elejida");
                //
                Arena arena = plugin.getArenaManager().getArena(npc.getUniqueId());
                configCofresIni = false;
                // Coloca los datos de la arena en la escena
                UtilEscena.inicializarAlamcenesYCofres(escena, arena, plugin.getEscenaManager());
                UtilEscena.iniciaLocaciones(escena, arena);
                UtilEscena.PublicaContenidoCofres(escena);

                // Borra la memoria del NPC
                System.out.println("Se cancela la navegaci??n del NPC");
                npc.getNavigator().cancelNavigation();
                System.out.println("La posici??n actual es: "+posactual);
                posactual = 1;
                movetonextpos();
            }
            n_tick++; // El tick tack del reloj
            if (n_tick > n_tick_max) {
                //System.out.println(" -- Est?? navegando?: "+npc.getNavigator().isNavigating() );
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
        plugin.setNPC(npc);
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