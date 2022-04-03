package vmejiaec.com.citnpc;

import net.citizensnpcs.api.ai.event.*;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

//This is your trait that will be applied to a npc using the /trait mytraitname command.
// Each NPC gets its own instance of this class.
//the Trait class has a reference to the attached NPC class
// through the protected field 'npc' or getNPC().
//The Trait class also implements Listener so you can add EventHandlers directly to your trait.
public class MyTrait extends Trait {

    // El mundo
    World world = null;
    // La position de las bases

    Vector vecbase1 = new Vector(151, 4, -105);
    Vector vecbase2 = new Vector(156, 4, -86);

    // La posición de los almacenes

    Vector vecAlmacen1 = new Vector(172, 4, -103);
    Vector vecAlmacen2 = new Vector(172, 4, -90);

    // La localización de los puntos de los caminos
    //  -- Camino 1 al Almacen 1
    Vector C1v1 = new Vector(152, 4, -105);
    Vector C1v2 = new Vector(156, 4, -103);
    Vector C1v3 = new Vector(161, 4, -103);
    Vector C1v4 = new Vector(161, 4, -107);
    Vector C1v5 = new Vector(169, 4, -107);
    Vector C1v6 = new Vector(172, 4, -90);
    List<Vector> caminoAlm1 = new ArrayList<Vector>(){
        {add(C1v1);add(C1v2);add(C1v3);add(C1v4);add(C1v5);add(C1v6);}};


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
    }

    @EventHandler
    public void navigationbegin( NavigationBeginEvent event){
        System.out.println("  -- <[ Evento de navegación BEGIN");
    }

    int posactual = 0;

    public  void movetonextpos(){
        System.out.print(" -- movetonextpos: "+posactual);
        if (posactual == 0 ) posactual = 5 ; else posactual = 0;
        System.out.print(" -- movetonextpos: "+posactual);
        npc.getNavigator().setTarget(new Location(
                world,
                caminoAlm1.get(posactual).getX(),
                caminoAlm1.get(posactual).getY(),
                caminoAlm1.get(posactual).getZ()
        ));
        
    }

    @EventHandler
    public void navigationcomplete( NavigationCompleteEvent event){
        System.out.println("  -- ]> Evento de navegación COMPLETE");
        // Confirma que ha llegado al destino
        Location locnpc = npc.getStoredLocation();
        double distancia =  caminoAlm1.get(posactual).distance(npc.getStoredLocation().toVector())  ;
        System.out.println(" -- Distancia del Npc al destino: "+distancia);
        if (distancia <= 3 ){ // llegó al destino
            trigerbeginmove = true;
        } else {              // aun no llega al destino
            if (posactual == 0 ) posactual = 5 ; else posactual = 0;
            trigerbeginmove = true;
        }
    }

    @EventHandler
    public void navigationcancel( NavigationCancelEvent event){
        System.out.println("  -- (X) Evento de navegación CANCEL");
        System.out.println("  -- (X) -- Reason: "+event.getCancelReason().toString());
        System.out.println("  -- (X) -- Reason name: "+event.getCancelReason().name());
        trigerbeginmove = true;
    }



    int n_tick = 0;
    int n_tick_max = 50;

    boolean trigerbeginmove = true;

    // Called every tick
    @Override
    public void run() {

        n_tick++;
        if (n_tick > n_tick_max){
            System.out.println("TICK + "+n_tick);
            System.out.println(" -- Está navegando?: "+npc.getNavigator().isNavigating() );
            n_tick = 0;

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