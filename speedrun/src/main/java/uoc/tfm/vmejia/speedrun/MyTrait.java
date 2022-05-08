package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.ai.event.NavigationBeginEvent;
import net.citizensnpcs.api.ai.event.NavigationCancelEvent;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import uoc.tfm.vmejia.cbrplugin.Recomendar;
import uoc.tfm.vmejia.speedrun.cbr.Modelo;
import uoc.tfm.vmejia.speedrun.cbr.UtilCaso;
import uoc.tfm.vmejia.speedrun.cbr.UtilModelo;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlAgente;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlBase;
import uoc.tfm.vmejia.speedrun.util.UtilAgente;
import uoc.tfm.vmejia.speedrun.util.UtilAlmacen;
import uoc.tfm.vmejia.speedrun.util.UtilBase;
import uoc.tfm.vmejia.speedrun.util.UtilLocation;
import uoc.tfm.vmejia.speedrun.var.Escena;

public class MyTrait extends Trait {

    SpeedRun plugin;
    World world = null;
    Recomendar reco =null;
    Escena escena = new Escena();

    public MyTrait() {
        super("mytraitname");
        plugin = JavaPlugin.getPlugin(SpeedRun.class);
    }

    @EventHandler
    public void click(net.citizensnpcs.api.event.NPCRightClickEvent event){
        System.out.println("click event ------------------------------- ");
        System.out.println( "  -- Nombre del Evento: "+ event.getEventName());
        Block bloque = event.getClicker().getTargetBlockExact(1);
        if (bloque == null) throw new AssertionError();
        Location bloqueLocation = bloque.getLocation();
        System.out.println(UtilLocation.ToString(bloqueLocation));
    }

    @EventHandler
    public void navigationbegin( NavigationBeginEvent event){
        System.out.print("  -- <[ Evento mov BEGIN");
    }



    // Función para inicializar los cofres, los almacenes y el agente
    public void inicializarAlamcenesYCofres(){
        escena.inicializarAlamcenesYCofres();
    }

    // Inicializa el cbr para realizar las consultas
    public  void inicializarCBR(){
        System.out.println(" -- -- Inicializa el CBR con los casos");
        reco = new Recomendar();
        reco.loadengine();
    }

    // Consultar al cbr sobre el mejor caso
    public String ConsultaCBR(){
        return  UtilCaso.ConsultaCBR(reco, escena);
    }

    // Elije cuál es la mejor estrategia
    public void estrategia(Escena escena){
        UtilModelo.estrategia(reco, escena);
    }

    // Coloca el destino al NPC que le corresponde
    int posactual = 0;

    public void movetonextpos(){
        if (posactual == 0 ) posactual = 1 ; else posactual = 0;
        npc.getNavigator().setTarget(new Location(
                world,
                escena.camino.get(posactual).getX(),
                escena.camino.get(posactual).getY(),
                escena.camino.get(posactual).getZ()
        ));
    }

    // El estado significa si se encuentra en la base
    boolean estaenbase = true;

    @EventHandler
    public void navigationcomplete( NavigationCompleteEvent event){
        System.out.print("  -- ]> Evento mov COMPLETE");
        // Confirma que ha llegado al destino
        Location locnpc = npc.getStoredLocation();
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
                estrategia(escena);
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
    // Called every tick
    @Override
    public void run() {

        // Una sola vez al empezar
        if (start){
            start = false;
            // Inicializa el camino por defecto al almacen 1
            escena.camino = escena.caminoAlm2;
            // Iniciar el motor del cbr
            inicializarCBR();
            // Iniciar los almacenes y los cofres de la base
            inicializarAlamcenesYCofres();
            // Encuentra las posiciones de referencia

        }

        n_tick++; // El tick tack del reloj
        if (n_tick > n_tick_max){
            //System.out.println(" -- Está navegando?: "+npc.getNavigator().isNavigating() );
            n_tick = 0; // Reinicio del reloj

            // Si no ha empezado el viaje, lo inicia
            if(trigerbeginmove) {
                trigerbeginmove = false;
                movetonextpos();
            }
        }
    }

    //Run code when your trait is attached to a NPC.
    //This is called BEFORE onSpawn, so npc.getEntity() will return null
    //This would be a good place to load configurable defaults for new NPCs.
    @Override
    public void onAttach() {
        plugin.getServer().getLogger().info(npc.getName() + "has been assigned MyTrait!");
    }

    // Run code when the NPC is despawned. This is called before the entity actually despawns so npc.getEntity() is still valid.
    @Override
    public void onDespawn() {
    }

    //Run code when the NPC is spawned. Note that npc.getEntity() will be null until this method is called.
    //This is called AFTER onAttach and AFTER Load when the server is started.
    @Override
    public void onSpawn() {
        System.out.println("Evento OnSpawn llamado --------(*)");
        String npcname = npc.getName();
        System.out.println();
        world = npc.getStoredLocation().getWorld();
        System.out.println("   --- El npc se llama: " + npcname);
    }

    //run code when the NPC is removed. Use this to tear down any repeating tasks.
    @Override
    public void onRemove() {
    }

}