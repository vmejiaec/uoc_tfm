package vmejiaec.com.citnpc;

import de.dfki.mycbr.util.Pair;
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
import org.bukkit.util.Vector;

import uoc.tfm.vmejia.cbrplugin.Recomendar;

import vmejiaec.com.citnpc.ctrl.CtrlAgente;
import vmejiaec.com.citnpc.ctrl.CtrlBase;
import vmejiaec.com.citnpc.util.UtilAgente;
import vmejiaec.com.citnpc.util.UtilAlmacen;
import vmejiaec.com.citnpc.util.UtilBase;
import vmejiaec.com.citnpc.var.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static vmejiaec.com.citnpc.var.Agente.destinotipo;
import static vmejiaec.com.citnpc.var.Material.tipo;

//This is your trait that will be applied to a npc using the /trait mytraitname command.
// Each NPC gets its own instance of this class.
//the Trait class has a reference to the attached NPC class
// through the protected field 'npc' or getNPC().
//The Trait class also implements Listener so you can add EventHandlers directly to your trait.
public class MyTrait extends Trait {

    // El mundo
    World world = null;
    // La position de las bases

    Vector vecbase1 = new Vector(152, 4, -105);

    // La posición de los almacenes

    Vector vecAlmacen1 = new Vector(172, 4, -103);
    Vector vecAlmacen2 = new Vector(172, 4, -90);

    // los caminos de la base a los almacenes
    List<Vector> caminoAlm1 = new ArrayList<Vector>(){{add(vecbase1);add(vecAlmacen1);}};
    List<Vector> caminoAlm2 = new ArrayList<Vector>(){{add(vecbase1);add(vecAlmacen2);}};
    List<Vector> camino = new ArrayList<>();

    public MyTrait() {
        super("mytraitname");
        plugin = JavaPlugin.getPlugin(Citnpc.class);
    }

    Citnpc plugin = null;

    boolean SomeSetting = false;

    // see the 'Persistence API' section
    @Persist("mysettingname") boolean automaticallyPersistedSetting = false;

    // Here you should load up any values you have previously saved (optional).
    // This does NOT get called when applying the trait for the first time, only loading onto an existing npc at server start.
    // This is called AFTER onAttach so you can load defaults in onAttach and they will be overridden here.
    // This is called BEFORE onSpawn, npc.getEntity() will return null.
    public void load(DataKey key) {
        SomeSetting = key.getBoolean("SomeSetting", false);
    }

    // Save settings for this NPC (optional). These values will be persisted to the Citizens saves file
    public void save(DataKey key) {
        key.setBoolean("SomeSetting",SomeSetting);
    }

    @EventHandler
    public void click(net.citizensnpcs.api.event.NPCRightClickEvent event){
        System.out.println("click event ------------------------------- ");
        System.out.println( "  -- Nombre del Evento: "+ event.getEventName());
        Block bloque = event.getClicker().getTargetBlockExact(1);
        Location bloqueLocation = bloque.getLocation();
        System.out.println(ToString(bloqueLocation));
    }

    public String ToString(Location loc){
        String res="";
        res += " x: " + loc.getX();
        res += " y: " + loc.getY();
        res += " z: " + loc.getZ();
        return res;
    }

    @EventHandler
    public void navigationbegin( NavigationBeginEvent event){
        System.out.print("  -- <[ Evento mov BEGIN");
    }

    // Almacenes y cofres
    Almacen almacen1 = null;
    Almacen almacen2 = null;
    Base base1 = null;
    Agente agente = null;

    // Función para inicializar los cofres y los almacenes
    public void inicializarAlamcenesYCofres(){
        base1 = new Base("pan");
        almacen1 = new Almacen("Almacen 1",25,25,35,30);
        almacen2 = new Almacen("Almacen 2",15,15,20,10);
        base1.cofrepan = new Cofre(0,0,0,0,0);
        base1.cofrepan.receta = new Receta("pan",0,1,0,3);
        base1.cofregalleta = new Cofre(0,0,0,0,0);
        base1.cofregalleta.receta = new Receta("galleta",1,1,1,2);
        base1.cofrepastel = new Cofre(0,0,0,0,0);
        base1.cofrepastel.receta = new Receta("pastel",3,2,1,0);
        // Inicializa el agente
        agente = new Agente("npc");
        agente.destino = destinotipo.ALALMACEN1;
        agente.producto = tipo.PAN;
        agente.material = tipo.CACAO;
        agente.cantidad = 10;
        // Publica la condición inicial
        System.out.println(UtilAlmacen.publica(almacen1));
        System.out.println(UtilAlmacen.publica(almacen2));
    }

    // Inicializa el cbr para realizar las consultas
    Recomendar reco =null;

    public  void inicializarCBR(){
        System.out.println(" -- -- Inicializa el CBR con los casos");
        reco = new Recomendar();
        reco.loadengine();
    }

    // Consultar al cbr sobre el mejor caso
    public String ConsultaCBR(){
        Caso caso = new Caso(base1.objetivo.toLowerCase(Locale.ROOT));
        caso.alm1 = almacen1;
        caso.alm2 = almacen2;
        caso.cofre_pan = base1.cofrepan;
        caso.cofre_galleta = base1.cofregalleta;
        caso.cofre_pastel = base1.cofrepastel;

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
        // --------------------------------------------------------------------------------------

        Pair res = reco.solveOuery(caso.a_objetivo, nombres, valores, 1);

        System.out.println("Mejor caso: "+res.getFirst() + " " +res.getSecond());

        return ""+ res.getFirst();
    }

    // Elije cuál es la mejor estrategia
    public void estrategia(){
        Random r = new Random();


        // Consulta al CBR
        String resultado = ConsultaCBR();
        CtrlAgente.Configurar(agente,resultado);

        // Factor aleatorio sobre el resultado CBR
        int sorteo = r.nextInt(2);
        System.out.println("Estrategia :" + sorteo);
        switch (sorteo){
            case 0:
                camino = caminoAlm1;
                agente.destino = destinotipo.ALALMACEN1;
                break;
            case 1:
                camino = caminoAlm2;
                agente.destino = destinotipo.ALALMACEN2;
                break;
        }
        sorteo = r.nextInt(4);
        switch (sorteo){
            case 0:
                agente.material = tipo.CACAO;
                break;
            case 1:
                agente.material = tipo.HUEVO;
                break;
            case 2:
                agente.material = tipo.LECHE;
                break;
            case 3:
                agente.material = tipo.TRIGO;
                break;
        }
        sorteo = r.nextInt(3);
        switch (sorteo){
            case 0:
                agente.producto = tipo.PAN;
                break;
            case 1:
                agente.producto = tipo.GALLETA;
                break;
            case 2:
                agente.producto = tipo.PASTEL;
                break;
        }
    }

    // Coloca el destino al NPC que le corresponde
    int posactual = 0;

    public void movetonextpos(){
        //System.out.print(" -- movetonextpos: "+posactual);
        if (posactual == 0 ) posactual = 1 ; else posactual = 0;
        //System.out.print(" -- movetonextpos: "+posactual);
        npc.getNavigator().setTarget(new Location(
                world,
                camino.get(posactual).getX(),
                camino.get(posactual).getY(),
                camino.get(posactual).getZ()
        ));
    }

    // El estado significa si se encuentra en la base
    boolean estaenbase = true;

    @EventHandler
    public void navigationcomplete( NavigationCompleteEvent event){
        System.out.print("  -- ]> Evento mov COMPLETE");
        // Confirma que ha llegado al destino
        Location locnpc = npc.getStoredLocation();
        double distancia =  camino.get(posactual).distance(npc.getStoredLocation().toVector())  ;
        //System.out.println(" -- Distancia del Npc al destino: "+distancia);
        if (distancia <= 3 ){ // llegó al destino
            trigerbeginmove = true;
            estaenbase = posactual == 0;   // Si está en la base es true
            if (estaenbase){  // está en la base
                System.out.print("Estoy en la base!");
                // Deja el material que lleva en la bolsa
                switch (agente.producto){
                    case PAN:
                        CtrlAgente.Deja(agente,base1.cofrepan);
                        break;
                    case GALLETA:
                        CtrlAgente.Deja(agente,base1.cofregalleta);
                        break;
                    case PASTEL:
                        CtrlAgente.Deja(agente,base1.cofrepastel);
                        break;
                }
                base1.objetivo = ""+agente.producto;
                // Procesa los productos de los cofres de la base
                CtrlBase.procesa(base1);
                // Publica el estado de la base
                System.out.println(UtilBase.publicar(base1));
                // Elije la estrategia
                estrategia();
            } else {  // está en el almacen
                System.out.print("Estoy en el almacén!"+agente.destino);
                // Toma el material
                switch (agente.destino){
                    case ALALMACEN1:
                        CtrlAgente.Toma(agente,almacen1);
                        System.out.println(UtilAlmacen.publica(almacen1));
                        break;
                    case ALALMACEN2:
                        CtrlAgente.Toma(agente,almacen2);
                        System.out.println(UtilAlmacen.publica(almacen2));
                        break;
                }
                // Se publica al agente
                System.out.println(UtilAgente.publicar(agente));
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
            camino = caminoAlm2;
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