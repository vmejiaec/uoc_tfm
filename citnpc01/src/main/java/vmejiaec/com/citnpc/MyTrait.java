package vmejiaec.com.citnpc;

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
    // La localización de las bases
    Location locbase1 = null;
    Vector vecbase1 = new Vector(153, 4, -105);
    // La localización de los almacenes
    Location locAlmacen1 = null;
    Vector vecAlmacen1 = new Vector(170, 4, -103);
    Location locAlmacen2 = null;
    // La localización de los puntos de los caminos
    //  -- Camino 1 al Almacen 1
    Vector C1v1 = new Vector(153, 4, -105);
    Vector C1v2 = new Vector(156, 4, -103);
    Vector C1v3 = new Vector(161, 4, -103);
    Vector C1v4 = new Vector(161, 4, -107);
    Vector C1v5 = new Vector(169, 4, -107);
    Vector C1v6 = new Vector(170, 4, -103);

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

    int pathid =0;
    int pathsize = 5;
    Vector v1 = new Vector(-395, 67, 270);
    Vector v2 = new Vector(-400, 67, 270);
    Vector v3 = new Vector(-407, 67, 270);
    Vector v4 = new Vector(-410, 70, 264);
    Vector v5 = new Vector(-407, 67, 251);
    List<Vector> listvector = new ArrayList<Vector>(){{add(v1);add(v2);add(v3);add(v4);add(v5);}};

    public Location LocationNext(World world){
        pathid++;
        if (pathid == pathsize) { pathid=0; }
        return new Location(world,
                listvector.get(pathid).getX(),listvector.get(pathid).getY(),listvector.get(pathid).getZ());
    }

    @EventHandler
    public void click(net.citizensnpcs.api.event.NPCRightClickEvent event){
        System.out.println("click event ------------------------------- ");
        System.out.println( "  -- Nombre del Evento: "+ event.getEventName());
    }

    int n_tick = 0;
    int n_tick_max = 100;
    int posactual = 0;

    // Called every tick
    @Override
    public void run() {

        n_tick++;
        if (n_tick > n_tick_max){
            System.out.println("TICK + "+n_tick);
            n_tick = 0;
            // Se pone a mover al NPC
            if (posactual == 0 ) posactual = 5 ; else posactual = 0;

            npc.getNavigator().setTarget(new Location(
                    world,
                    caminoAlm1.get(posactual).getX(),
                    caminoAlm1.get(posactual).getY(),
                    caminoAlm1.get(posactual).getZ()
            ));

            Location locnpc = npc.getStoredLocation();

            // Cálculo de la distancia

            double d2 =  vecbase1.distance(npc.getStoredLocation().toVector())  ;

            System.out.println("Distancia del Npc a la base1: "+d2);

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