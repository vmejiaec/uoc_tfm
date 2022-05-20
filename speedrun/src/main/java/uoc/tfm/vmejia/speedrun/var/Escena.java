package uoc.tfm.vmejia.speedrun.var;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.util.Vector;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.manager.EscenaManager;
import uoc.tfm.vmejia.speedrun.util.UtilAlmacen;

import java.util.ArrayList;
import java.util.List;

public class Escena {
    // Elementos de la escena
    public Base baseNPC; // base del NPC agente
    public Base basePlayer; // base del player
    public Almacen almacenIzq;
    public Almacen almacenDer;
    public Agente agente;

    // Posiciones y caminos de la escena
    // La position de las bases
    public Vector vecBaseNPC = new Vector();
    public Vector vecBasePlayer = new Vector();
    // La posición de los almacenes
    public Vector vecAlmacen1 = new Vector();
    public Vector vecAlmacen2 = new Vector();

    // los caminos de la base a los almacenes
    public List<Vector> caminoAlm1 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen1);}};
    public List<Vector> caminoAlm2 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen2);}};
    public List<Vector> camino = new ArrayList<>();

    public void iniciaLocaciones(Arena arena){
        System.out.println("Inicializa las Posiciones de la arena: "+arena.getId());
        vecBaseNPC = arena.getNPCSpawn().toVector();
        vecBasePlayer = arena.getPlayerSpawn().toVector();
        vecAlmacen1 = arena.getAlmacen1().toVector();
        vecAlmacen2 = arena.getAlmacen2().toVector();

        caminoAlm1 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen1);}};
        caminoAlm2 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen2);}};

        camino = caminoAlm2;

        // Actualiza las posiciones de los cofres
        World world = arena.getWorld();
        // Decide la orientación de la base respecto a los cofres
        Vector posrelBaseCofrePan = new Vector(1,0,-2);
        Vector posrelBaseCofreGalleta = new Vector(1,0,0);
        Vector posrelBaseCofrePastel = new Vector(1,0,2);
        Vector posrelAlmacen = new Vector(-1,1,0);
        boolean signo = vecAlmacen1.getX() > vecBaseNPC.getX()  ;
        if (signo){
            posrelBaseCofrePan = new Vector(-1,0,2);
            posrelBaseCofreGalleta = new Vector(-1,0,0);
            posrelBaseCofrePastel = new Vector(-1,0,-2);
            posrelAlmacen = new Vector(1,1,0);
        }
        // Calcula dónde estan los cofres de bases y almacenes
        Vector posBaseNPCCofrePan = vecBaseNPC.clone().add(posrelBaseCofrePan) ;
        Vector posBaseNPCCofreGalleta = vecBaseNPC.clone().add(posrelBaseCofreGalleta) ;
        Vector posBaseNPCCofrePastel = vecBaseNPC.clone().add(posrelBaseCofrePastel) ;
        Vector posBasePlayerCofrePan = vecBasePlayer.clone().add(posrelBaseCofrePan) ;
        Vector posBasePlayerCofreGalleta = vecBasePlayer.clone().add(posrelBaseCofreGalleta) ;
        Vector posBasePlayerCofrePastel = vecBasePlayer.clone().add(posrelBaseCofrePastel) ;
        Vector posAlmacen1 = vecAlmacen1.clone().add(posrelAlmacen);
        Vector posAlmacen2 = vecAlmacen2.clone().add(posrelAlmacen);

        // Localiza los cofres
        System.out.println("World: "+world);
        if(world != null){
            baseNPC.cofrepan.pos = getLoc(world,posBaseNPCCofrePan);
            baseNPC.cofregalleta.pos = getLoc(world,posBaseNPCCofreGalleta);
            baseNPC.cofrepastel.pos = getLoc(world,posBaseNPCCofrePastel);

            basePlayer.cofrepan.pos = getLoc(world,posBasePlayerCofrePan);
            basePlayer.cofregalleta.pos = getLoc(world,posBasePlayerCofreGalleta);
            basePlayer.cofrepastel.pos = getLoc(world,posBasePlayerCofrePastel);

            almacenIzq.cofre.pos = getLoc(world,posAlmacen1);
            almacenDer.cofre.pos = getLoc(world,posAlmacen2);


            // Publicamos resultados de posiciones para control
            System.out.println("baseNPC.cofrepan.pos "+ baseNPC.cofrepan.pos);
            System.out.println("baseNPC.cofregalleta.pos "+ baseNPC.cofregalleta.pos);
            System.out.println("baseNPC.cofrepastel.pos "+ baseNPC.cofrepastel.pos);

            System.out.println("basePlayer.cofrepan.pos "+ basePlayer.cofrepan.pos);
            System.out.println("basePlayer.cofregalleta.pos "+ basePlayer.cofregalleta.pos);
            System.out.println("basePlayer.cofrepastel.pos "+ basePlayer.cofrepastel.pos);

            System.out.println("almacenIzq.cofre.pos "+ almacenIzq.cofre.pos);
            System.out.println("almacenDer.cofre.pos "+ almacenDer.cofre.pos);
        }
    }

    public Location getLoc(World world, Vector vec){
        return new Location(world, vec.getX(), vec.getY(), vec.getZ());
    }

    public void inicializarAlamcenesYCofres(Arena arena, EscenaManager escenaManager){
        // Inicializa los almacenes
        if (arena != null) {
            almacenIzq = arena.getalmAlmacen1();
            almacenDer = arena.getalmAlmacen2();
        }
        // Inicializa la base del NPC
        baseNPC = new Base("pan");
        baseNPC.cofrepan = new Cofre();
        baseNPC.cofrepan.receta = escenaManager.getRecetaPan();
        baseNPC.cofregalleta = new Cofre();
        baseNPC.cofregalleta.receta = escenaManager.getRecetaGalleta();
        baseNPC.cofrepastel = new Cofre();
        baseNPC.cofrepastel.receta = escenaManager.getRecetaPastel();
        // Inicializa la base del player
        basePlayer = new Base("pan");
        basePlayer.cofrepan = new Cofre();
        basePlayer.cofrepan.receta = escenaManager.getRecetaPan();
        basePlayer.cofregalleta = new Cofre();
        basePlayer.cofregalleta.receta = escenaManager.getRecetaGalleta();
        basePlayer.cofrepastel = new Cofre();
        basePlayer.cofrepastel.receta = escenaManager.getRecetaPastel();
        // Inicializa el agente
        agente = new Agente("npc");
        agente.destino = Agente.destinotipo.ALALMACEN1;
        agente.producto = Material.tipo.PAN;
        agente.material = Material.tipo.CACAO;
        agente.cantidad = 10;
        // Publica la condición inicial
        System.out.println(UtilAlmacen.publica(almacenIzq));
        System.out.println(UtilAlmacen.publica(almacenDer));
    }


    public void iniciaContenidoCofres() {
        Chest cofre = (Chest) baseNPC.cofrepan.pos.getBlock().getState();

    }
}
